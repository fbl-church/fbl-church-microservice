package com.fbl.app.cloudinary.service;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FTPConnector {

    Logger logger = LoggerFactory.getLogger(FTPConnector.class);

    public FTPClient connect(MultipartFile file) throws IOException {
        // Create an instance of FTPClient
        FTPClient ftpClient = new FTPClient();
        try {
            // establish a connection with specific host and
            // port.
            ftpClient.connect("174.104.249.41", 21);

            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                logger.info(
                        "Operation failed. Server reply code: "
                                + replyCode);
                ftpClient.disconnect();
            }

            // login to ftp server with username and
            // password.
            boolean success = ftpClient.login("fbl-user-PROD", "100698Sb!");
            if (!success) {
                ftpClient.disconnect();
            }
            // assign file type according to the server.
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            FTPFile[] other = ftpClient.listDirectories();
            ftpClient.changeWorkingDirectory("/disk1/fbl-cloud-PROD");
            ftpClient.storeFile("/test/dummy.pdf", file.getInputStream());
            int test = 1;

        } catch (UnknownHostException E) {
            logger.info("No such ftp server");
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return ftpClient;
    }
}
