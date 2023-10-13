/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.ftp.client;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.InputStream;

import org.apache.commons.net.ftp.FTPFileFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import com.fbl.test.factory.annotations.InsiteServiceTest;

@InsiteServiceTest
public class FTPStorageClientTest {

    @InjectMocks
    private FTPStorageClient ftpStorageClient;

    @Mock
    private FTPServerClient ftpServerClient;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(ftpStorageClient, "environment", "TEST");
    }

    @Test
    void testUploadWithOnlyFileName() {
        ftpStorageClient.upload("FAKE_FILE.txt", InputStream.nullInputStream());

        verify(ftpServerClient).storeFile(eq("/disk1/fbl-cloud-TEST"), eq("FAKE_FILE.txt"), any(InputStream.class));
    }

    @Test
    void testUploadWithPathAndFileName() {
        ftpStorageClient.upload("path/to/store/at", "FAKE_FILE.txt", InputStream.nullInputStream());

        verify(ftpServerClient).storeFile(eq("/disk1/fbl-cloud-TEST/path/to/store/at"), eq("FAKE_FILE.txt"),
                any(InputStream.class));
    }

    @Test
    void testFileDelete() {
        ftpStorageClient.deleteFile("path/to/store/at/FAKE_FILE.txt");

        verify(ftpServerClient).changeDirectory("/disk1/fbl-cloud-TEST");
        verify(ftpServerClient).deleteFile("/disk1/fbl-cloud-TEST/path/to/store/at/FAKE_FILE.txt");
    }

    @Test
    void testGetFile() {
        ftpStorageClient.get("path/to/store/at/FAKE_FILE.txt", (f) -> f.isFile());

        verify(ftpServerClient).get(eq("/disk1/fbl-cloud-TEST/path/to/store/at/FAKE_FILE.txt"),
                any(FTPFileFilter.class));
    }

    @Test
    void testDownload() {
        ftpStorageClient.download("path/to/store/at/FAKE_FILE.txt");

        verify(ftpServerClient).download("/disk1/fbl-cloud-TEST/path/to/store/at/FAKE_FILE.txt");
    }
}
