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
import org.springframework.test.context.ContextConfiguration;

import com.fbl.FBLChurchApplication;
import com.fbl.common.annotations.interfaces.ControllerJwt;
import com.fbl.common.page.Page;
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
    public void testDownloadFile() throws Exception {
        InputStream testStream = new ByteArrayInputStream("test data".getBytes());
        when(service.downloadFile(anyString())).thenReturn(new InputStreamResource(testStream));
        check(get(BASE_PATH + "/download?file=base/path/fileDownload.txt", InputStreamResource.class),
                serializedNonNull(HttpStatus.OK));

        verify(service).downloadFile(stringCaptor.capture());
        assertEquals("base/path/fileDownload.txt", stringCaptor.getValue(), "Path Value");
    }
}
