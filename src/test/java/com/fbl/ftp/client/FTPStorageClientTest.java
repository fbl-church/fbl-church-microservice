package com.fbl.ftp.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.mockito.Mockito;

import com.fbl.test.factory.annotations.InsiteTest;

@InsiteTest
public class FTPStorageClientTest {
    private final String FAKE_FTP_HOST = "localhost";
    private final String FAKE_FTP_USERNAME = "test";
    private final String FAKE_FTP_PASSWORD = "password";

    private FakeFtpServer fakeFtpServer;

    private FTPServerClient ftpServerClient;

    @BeforeEach
    public void setup() throws IOException {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount(FAKE_FTP_USERNAME, FAKE_FTP_PASSWORD, "/disk1"));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/disk1/fbl-cloud-TEST"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(0);

        fakeFtpServer.start();

        ftpServerClient = Mockito
                .spy(new FTPServerClient(FAKE_FTP_HOST, fakeFtpServer.getServerControlPort(), FAKE_FTP_USERNAME,
                        FAKE_FTP_PASSWORD));
        ftpServerClient.connect();
    }

    @AfterEach
    public void teardown() throws IOException {
        ftpServerClient.close();
        fakeFtpServer.stop();
    }

    @Test
    void testStoreFile() {
        FileEntry newFile = new FileEntry();
        newFile.setContents("NEW FILE DATA");
        ftpServerClient.storeFile("/disk1/fbl-cloud-TEST", "NEW_FILE.txt", newFile.createInputStream());

        List<FTPFile> directoryFiles = ftpServerClient.get("/disk1/fbl-cloud-TEST", (f) -> f.isFile());
        assertTrue(directoryFiles.size() == 1, "Should be 1 file");
        assertEquals(directoryFiles.get(0).getName(), "NEW_FILE.txt", "New file name");
    }

    @Test
    void testDirectoryCreation() throws IOException {
        FileEntry newFile = new FileEntry();
        newFile.setContents("NEW FILE DATA");
        ftpServerClient.storeFile("/disk1/fbl-cloud-TEST/folder/newFiles", "NEW_FILE.txt", newFile.createInputStream());

        verify(ftpServerClient).makeDirectory("folder");
        verify(ftpServerClient).makeDirectory("newFiles");
        assertEquals(ftpServerClient.printWorkingDirectory(), "/disk1/fbl-cloud-TEST/folder/newFiles",
                "Working directory should be changed");

        List<FTPFile> directoryFiles = ftpServerClient.get("/disk1/fbl-cloud-TEST/folder/newFiles", (f) -> f.isFile());
        assertTrue(directoryFiles.size() == 1, "Should be 1 file");
        assertEquals(directoryFiles.get(0).getName(), "NEW_FILE.txt", "New file name");
    }
}
