/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.ftp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.fbl.common.page.Page;
import com.fbl.exception.types.ServiceException;
import com.fbl.ftp.client.FTPStorageClient;
import com.fbl.ftp.client.domain.request.FileGetRequest;
import com.fbl.test.factory.annotations.InsiteServiceTest;

@InsiteServiceTest
public class FTPStorageServiceTest {

    @InjectMocks
    private FTPStorageService ftpStorageService;

    @Mock
    private FTPStorageClient ftpStorageClient;

    @Test
    void testGetFiles() {
        FTPFile f = new FTPFile();
        f.setName("FILE_NAME.txt");

        when(ftpStorageClient.get(anyString(), any(FTPFileFilter.class))).thenReturn(List.of(f));

        FileGetRequest request = new FileGetRequest();
        request.setPath("path/to/file");
        Page<FTPFile> files = ftpStorageService.getFiles(request);

        assertTrue(files.getList().size() == 1, "Size should be 1");
    }

    @Test
    void testDownloadFile() {
        InputStream testStream = new ByteArrayInputStream("test data".getBytes());
        when(ftpStorageClient.download(anyString())).thenReturn(testStream);

        InputStreamResource isr = ftpStorageService.downloadFile("path");

        verify(ftpStorageClient).download("path");
        assertNotNull(isr, "Input Stream Resource is not null");
    }

    @Test
    void testDownloadFileNullStream() {
        when(ftpStorageClient.download(anyString())).thenReturn(null);
        InputStreamResource isr = ftpStorageService.downloadFile("path");

        verify(ftpStorageClient).download("path");
        assertNull(isr, "Input Stream Resource is null");
    }

    @Test
    void testUpload() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes());
        doNothing().when(ftpStorageClient).upload(anyString(), anyString(), any(InputStream.class));

        ftpStorageService.upload("path", file);
        verify(ftpStorageClient).upload(eq("path"), eq("hello.txt"), any(InputStream.class));
    }

    @Test
    void testUploadFailed() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes());
        doThrow(new RuntimeException()).when(ftpStorageClient).upload(anyString(), anyString(), any(InputStream.class));

        ServiceException ex = assertThrows(ServiceException.class, () -> ftpStorageService.upload("path", file));
        verify(ftpStorageClient).upload(eq("path"), eq("hello.txt"), any(InputStream.class));
        assertEquals("Unable to upload mulipart file: hello.txt", ex.getMessage(), "Exception Message");
    }

    @Test
    void testDeleteFile() {
        doNothing().when(ftpStorageClient).deleteFile(anyString());

        ftpStorageService.deleteFile("path/to/delete");
        verify(ftpStorageClient).deleteFile("path/to/delete");
    }
}
