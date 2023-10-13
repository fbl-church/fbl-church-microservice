/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.ftp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fbl.common.page.Page;
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
}
