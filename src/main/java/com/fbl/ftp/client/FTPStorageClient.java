package com.fbl.ftp.client;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.fbl.common.annotations.interfaces.Client;
import com.fbl.exception.types.BaseException;

@Client
public class FTPStorageClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(FTPStorageClient.class);

    @Value("${ftp.server:#{null}}")
    private String server;

    @Value("${ftp.port:#{null}}")
    private int port;

    @Value("${ftp.environment:#{null}}")
    private String environment;

    @Value("${ftp.username:#{null}}")
    private String username;

    @Value("${ftp.password:#{null}}")
    private String password;

    private FTPClient ftp;

    private String BASE_PATH;

    /**
     * Open Connection to FTP Storage bucket.
     */
    public void open() {
        ftp = new FTPClient();
        try {
            ftp.connect(server, port);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                throw new BaseException("Connection Reply failed to FTP Storage");
            }
            ftp.login(username, password);
            LOGGER.info("Successfully connected to FTP Storage! {}:{} -> {}-{}", server, port, username, password);
        } catch (Exception e) {
            throw new BaseException("Unable to connect to FTP Storage");
        }
        this.BASE_PATH = String.format("disk1/fbl-church-storage-%s", this.environment);
    }

    public void upload(InputStream is, String fileName) {
        upload(is, "", fileName);
    }

    public void upload(InputStream is, String path, String fileName) {
        String overallPath = String.format("%s/%s", this.BASE_PATH, fileName);
        try {
            ftp.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
            if (StringUtils.hasText(path)) {
                overallPath = String.format("%s/%s/%s", this.BASE_PATH, path, fileName);
            }

            ftp.storeFile(overallPath, is);
            LOGGER.info("Successfully uploaded file: {}", overallPath);
        } catch (Exception e) {
            LOGGER.error("Unable to save file to FTP Storage: {}", overallPath);
        }
    }

    public List<FTPFile> getFiles() {
        try {
            return Arrays.asList(ftp.listFiles(this.BASE_PATH));
        } catch (Exception e) {
            LOGGER.error("Unable to list files from FTP Storage!", e);
            return List.of();
        }
    }

    public List<FTPFile> getDirectories() {
        try {
            return Arrays.asList(ftp.listDirectories(this.BASE_PATH));
        } catch (Exception e) {
            LOGGER.error("Unable to list directories from FTP Storage!", e);
            return List.of();
        }
    }

    /**
     * Close the Connection to FTP Storage bucket.
     */
    public void close() {
        try {
            ftp.disconnect();
            LOGGER.info("Successfully closed connection to FTP Storage!");
        } catch (Exception e) {
            throw new BaseException("Unable to close connection to FTP Storage!");
        }
    }
}