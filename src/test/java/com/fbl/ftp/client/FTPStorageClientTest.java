package com.fbl.ftp.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
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
        Mockito.clearInvocations(ftpServerClient);
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

    @Test
    void testFileDelete() throws IOException {
        FileEntry newFile = new FileEntry();
        newFile.setContents("NEW FILE DATA");
        ftpServerClient.storeFile("/disk1/fbl-cloud-TEST/folder/newFiles", "NEW_FILE.txt", newFile.createInputStream());

        assertEquals(ftpServerClient.printWorkingDirectory(), "/disk1/fbl-cloud-TEST/folder/newFiles",
                "Working directory should be changed");

        boolean fileDeleted = ftpServerClient.deleteFile("/disk1/fbl-cloud-TEST/folder/newFiles/NEW_FILE.txt");
        assertTrue(fileDeleted, "File deleted");
    }

    @Test
    void testDirectoryDelete() {
        FileEntry newFile = new FileEntry();
        newFile.setContents("NEW FILE DATA");
        ftpServerClient.storeFile("/disk1/fbl-cloud-TEST", "NEW_FILE.txt", newFile.createInputStream());
        ftpServerClient.createDirectory("/disk1/fbl-cloud-TEST/DELETE_DIRECTORY");

        // Confirm Test Data was created
        List<FTPFile> files = ftpServerClient.get("/disk1/fbl-cloud-TEST");
        FTPFile directory = files.stream().filter(f -> f.isDirectory()).findAny().get();
        assertTrue(files.size() == 2, "Single Directory and One File");
        assertEquals(directory.getName(), "DELETE_DIRECTORY", "Directory Name");

        // Perform directory delete
        ftpServerClient.deleteDirectory("/disk1/fbl-cloud-TEST/DELETE_DIRECTORY");

        // Confirm directory deletion only
        files = ftpServerClient.get("/disk1/fbl-cloud-TEST");
        assertTrue(files.size() == 1, "Single File");
        assertTrue(files.stream().filter(f -> f.isDirectory()).findAny().isEmpty(), "Directory should be deleted");
    }

    @Test
    void testGetFileByName() throws IOException {
        FileEntry newFile = new FileEntry();
        newFile.setContents("NEW FILE DATA");
        ftpServerClient.storeFile("/disk1/fbl-cloud-TEST", "NEW_FILE.txt", newFile.createInputStream());

        assertTrue(ftpServerClient.getFile("/disk1/fbl-cloud-TEST", "NEW_FILE.txt").isPresent(), "File Found");
        assertTrue(ftpServerClient.getFile("/disk1/fbl-cloud-TEST", "NOT_REAL.png").isEmpty(), "File NOT Found");
    }

    @Test
    void testCheckConnectionServerConnectedAndActive() {
        ftpServerClient.checkConnection();
        verify(ftpServerClient).isActive();
        verify(ftpServerClient, never()).connect();
    }

    @Test
    void testCheckConnectionServerConnectedAndNotActive() throws IOException {
        when(ftpServerClient.sendNoOp()).thenReturn(false);

        ftpServerClient.checkConnection();
        verify(ftpServerClient).isActive();
        verify(ftpServerClient).connect();
    }

    @Test
    void testCheckConnectionServerNotConnected() throws IOException {
        ftpServerClient.close();
        ftpServerClient.checkConnection();
        verify(ftpServerClient, times(2)).isActive();
        verify(ftpServerClient).connect();
        assertTrue(ftpServerClient.isActive(), "Server should be active again");
    }

    @Test
    void testConnectionToFTPServer() throws IOException {
        ftpServerClient.close();
        assertFalse(ftpServerClient.isActive(), "Server should be disconnected");

        ftpServerClient.connect();
        verify(ftpServerClient).getReplyCode();
        verify(ftpServerClient).login("test", "password");
        verify(ftpServerClient).enterLocalPassiveMode();
        verify(ftpServerClient).setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
    }

    @Test
    void testCloseConnection() throws IOException {
        ftpServerClient.close();
        verify(ftpServerClient).logout();
        verify(ftpServerClient).disconnect();
        assertFalse(ftpServerClient.isActive(), "Server should be disconnected");
    }
}
