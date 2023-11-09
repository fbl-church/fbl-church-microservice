package com.fbl.ftp.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.fbl.common.annotations.interfaces.ControllerJwt;
import com.fbl.common.page.Page;
import com.fbl.exception.types.ServiceException;
import com.fbl.ftp.client.domain.request.FileGetRequest;
import com.fbl.ftp.service.FTPStorageService;
import com.fbl.test.factory.abstracts.BaseControllerTest;
import com.fbl.test.factory.annotations.InsiteRestTest;
import com.google.common.collect.Sets;

@InsiteRestTest
@ControllerJwt
public class FTPStorageControllerTest extends BaseControllerTest {
    private static final String BASE_PATH = "/api/ftp-storage";

    @MockBean
    private FTPStorageService service;

    @Captor
    private ArgumentCaptor<FileGetRequest> getFilesCaptor;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Captor
    private ArgumentCaptor<MultipartFile> multipartFileCaptor;

    @Test
    public void testGetFiles() throws Exception {
        when(service.getFiles(any(FileGetRequest.class))).thenReturn(Page.of(1, List.of()));
        this.mockMvc
                .perform(get(BASE_PATH).param("path", "base/path").param("search",
                        "fileSearch"))
                .andExpect(status().isOk());

        verify(service).getFiles(getFilesCaptor.capture());

        assertEquals("base/path", getFilesCaptor.getValue().getPath(), "Path Value");
        assertEquals(Sets.newHashSet("fileSearch"),
                getFilesCaptor.getValue().getSearch(), "Search Value");
    }

    @Test
    public void testDownloadFile() throws Exception {
        when(service.downloadFile(anyString())).thenReturn(null);
        this.mockMvc
                .perform(get(BASE_PATH + "/download").param("file", "base/path/fileDownload.txt"))
                .andExpect(status().isOk());
                
        verify(service).downloadFile(stringCaptor.capture());
        assertEquals("base/path/fileDownload.txt", stringCaptor.getValue(), "Path Value");
    }

    @Test
    public void testUploadFile() throws Exception {
        doNothing().when(service).upload(anyString(), any(MultipartFile.class));

        this.mockMvc.perform(multipart(BASE_PATH, getTestMultipartFile()).param("path", "upload/path"))
                .andExpect(status().isOk());

        verify(service).upload(stringCaptor.capture(),
                multipartFileCaptor.capture());
        assertEquals("upload/path", stringCaptor.getValue(), "Path Value");
        assertEquals("hello.txt",
                multipartFileCaptor.getValue().getOriginalFilename(), "Path Value");
    }

    @Test
    public void testUploadFileFailed() throws Exception {
        doThrow(new ServiceException("Upload Error")).when(service).upload(anyString(), any(MultipartFile.class));

        this.mockMvc.perform(multipart(BASE_PATH, getTestMultipartFile()).param("path", "upload/path"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testDeleteFile() throws Exception {
        doNothing().when(service).deleteFile(anyString());
        this.mockMvc.perform(delete(BASE_PATH).param("file", "file/path/delete.txt"))
                .andExpect(status().isOk());

        verify(service).deleteFile(stringCaptor.capture());
        assertEquals("file/path/delete.txt", stringCaptor.getValue(), "Path Value");
    }

    private MockMultipartFile getTestMultipartFile() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes());
        return file;
    }
}
