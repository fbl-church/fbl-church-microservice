package com.fbl.ftp.client;

import static org.junit.jupiter.api.Assertions.*;

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

import com.fbl.test.factory.annotations.InsiteTest;

@InsiteTest
public class FTPStorageClientTest {
    private FakeFtpServer fakeFtpServer;

    private FTPServerClient ftpServerClient;

    @BeforeEach
    public void setup() throws IOException {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount("test", "password", "/disk1"));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/disk1"));
        fileSystem.add(new DirectoryEntry("/fbl-cloud-TEST"));
        fileSystem.add(new FileEntry("/disk1/fbl-cloud-TEST/foobar.txt", "abcdef 1234567890"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(0);

        fakeFtpServer.start();

        ftpServerClient = new FTPServerClient("localhost", fakeFtpServer.getServerControlPort(), "test", "password");
        ftpServerClient.connect();
    }

    @AfterEach
    public void teardown() throws IOException {
        ftpServerClient.close();
        fakeFtpServer.stop();
    }

    @Test
    void testConnection() {
        List<FTPFile> files = ftpServerClient.get("/disk1/fbl-cloud-TEST", null);
        assertTrue(files.size() == 1, "Single file should be created");
    }
}
