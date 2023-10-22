package com.fbl.ftp.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.fbl.FBLChurchApplication;
import com.fbl.common.annotations.interfaces.ControllerJwt;
import com.fbl.common.page.Page;
import com.fbl.exception.types.ServiceException;
import com.fbl.ftp.client.domain.request.FileGetRequest;
import com.fbl.ftp.service.FTPStorageService;
import com.fbl.test.factory.abstracts.BaseControllerTest;
import com.fbl.test.factory.annotations.InsiteRestTest;
import com.google.common.collect.Sets;

@ContextConfiguration(classes = FBLChurchApplication.class)
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
        FTPFile f = new FTPFile();
        f.setName("FILE_NAME.txt");

        when(service.getFiles(any(FileGetRequest.class))).thenReturn(new Page<>(1, List.of(f)));
        check(get(BASE_PATH + "?path=base/path&search=fileSearch", FTPFile[].class), serializedList(HttpStatus.OK));

        verify(service).getFiles(getFilesCaptor.capture());

        assertEquals("base/path", getFilesCaptor.getValue().getPath(), "Path Value");
        assertEquals(Sets.newHashSet("fileSearch"), getFilesCaptor.getValue().getSearch(), "Search Value");
    }

    @Test
    public void testDownloadFile() {
        InputStream testStream = new ByteArrayInputStream("test data".getBytes());
        when(service.downloadFile(anyString())).thenReturn(new InputStreamResource(testStream));
        check(get(BASE_PATH + "/download?file=base/path/fileDownload.txt", InputStreamResource.class),
                serializedNonNull(HttpStatus.OK));

        verify(service).downloadFile(stringCaptor.capture());
        assertEquals("base/path/fileDownload.txt", stringCaptor.getValue(), "Path Value");
    }

    @Test
    public void testUploadFile() {
        doNothing().when(service).upload(anyString(), any(MultipartFile.class));

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", getTestMultipartFile().getResource());
        body.add("path", "upload/path");

        check(post(BASE_PATH, body, Void.class), httpStatusEquals(HttpStatus.OK));

        verify(service).upload(stringCaptor.capture(), multipartFileCaptor.capture());
        assertEquals("upload/path", stringCaptor.getValue(), "Path Value");
        assertEquals("hello.txt", multipartFileCaptor.getValue().getOriginalFilename(), "Path Value");
    }

    @Test
    public void testUploadFileFailed() {
        doThrow(new ServiceException("Upload Error")).when(service).upload(anyString(), any(MultipartFile.class));

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", getTestMultipartFile().getResource());
        body.add("path", "upload/path");

        check(post(BASE_PATH, body), error(HttpStatus.INTERNAL_SERVER_ERROR, "Upload Error"));
    }

    @Test
    public void testDeleteFile() {
        doNothing().when(service).deleteFile(anyString());
        check(delete(BASE_PATH + "?file=file/path/delete.txt"), httpStatusEquals(HttpStatus.OK));

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
