package com.fbl.ftp.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fbl.common.annotations.interfaces.Client;
import com.fbl.exception.types.BaseException;

import io.jsonwebtoken.lang.Collections;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * FTP Server Client wrapper for {@link FTPClient}.
 * 
 * @author Sam Butler
 * @since April 27, 2022
 */
@Client
@AllArgsConstructor
@NoArgsConstructor
public class FTPServerClient extends FTPClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(FTPServerClient.class);

    private String server;
    private int port;
    private String username;
    private String password;

    /**
     * Base Connection and login with the constructed server, port, username and
     * password.
     */
    public void connect() {
        try {
            super.connect(server, port);
            if (!FTPReply.isPositiveCompletion(super.getReplyCode())) {
                super.disconnect();
                throw new BaseException("Connection Reply failed to FTP Server");
            }

            boolean loginSuccess = super.login(username, password);
            if (!loginSuccess) {
                throw new BaseException("FTP Authentication Failed!");
            }

            super.enterLocalPassiveMode();
            super.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
            LOGGER.info("FTP Connected to Server!");
        } catch (Exception e) {
            LOGGER.error("Unable to connect to FTP Server!", e);
            throw new BaseException("Unable to connect to FTP Server!");
        }
    }

    /**
     * Close method to logout and disconnect the active session to the FTP server.
     */
    public void close() {
        if (super.isConnected()) {
            try {
                super.logout();
                super.disconnect();

            } catch (Exception e) {
                LOGGER.warn("Unable to close connection to FTP Server!", e);
            }
            LOGGER.info("FTP Connection Successfully Closed!");
        }
    }

    /**
     * Perform a get on the FTP server. It will return a list of information based
     * on the path name and the {@link FTPFileFilter}
     * 
     * @param path   The path to pull the information from
     * @param filter The filter for the files/directories
     * @return List of {@link FTPFile}
     */
    public List<FTPFile> get(String path, FTPFileFilter filter) {
        this.checkConnection();
        try {
            this.changeDirectory(path);
            return Arrays.asList(super.listFiles("", filter));
        } catch (Exception e) {
            LOGGER.error("Unable to list files from FTP Server!", e);
            return List.of();
        }
    }

    /**
     * Stores a file on the server using the given name and taking input from the
     * given InputStream. This will close the given input stream after the file has
     * been stored.
     * 
     * @param path     The path to store the file at
     * @param fileName The name to store the file under
     * @param is       The input stream to store
     * @return Boolean if the file was created or not.
     */
    public boolean storeFile(String path, String fileName, InputStream is) {
        this.checkConnection();
        try {
            if (!super.changeWorkingDirectory(path)) {
                createDirectory(path);
            }

            boolean created = super.storeFile(fileName, is);
            is.close();
            LOGGER.info("File Uploaded: '{}'", String.format("%s/%s", path, fileName));
            return created;
        } catch (IOException e) {
            LOGGER.warn("Unable to create file: '{}'", String.format("%s/%s", path, fileName), e);
            return false;
        }
    }

    /**
     * Deletes a file on the FTP server.
     * 
     * @param filePath The path to delete the file from
     * @return Boolean if the file was deleteds or not.
     */
    @Override
    public boolean deleteFile(String filePath) {
        this.checkConnection();
        try {
            boolean deleted = super.deleteFile(filePath);
            LOGGER.info("File Deleted: '{}'", filePath);
            return deleted;
        } catch (IOException e) {
            LOGGER.warn("Unable to Delete File: '{}'", filePath, e);
            return false;
        }
    }

    /**
     * Change the current working directory of the FTP session.
     *
     * @param path The new current working directory.
     * @return if the directory change was successful
     */
    public boolean changeDirectory(String path) {
        this.checkConnection();
        try {
            return super.changeWorkingDirectory(path);
        } catch (Exception e) {
            LOGGER.error("Unable to change to base directory", e);
            return false;
        }
    }

    /**
     * Checks the FTP connection that it is still connected. If not it will attempt
     * a reconnection.
     */
    private void checkConnection() {
        if (super.isConnected()) {
            boolean serverActive = true;
            try {
                serverActive = super.sendNoOp();
            } catch (IOException e) {
                serverActive = false;
            }
            if (!serverActive) {
                LOGGER.info("FTP Server not Connected. Attempting Reconnect...");
                connect();
            }
        } else {
            LOGGER.info("FTP not Connected. Attempting Reconnect...");
            connect();
        }
    }

    /**
     * Create a new directory for the given path.
     * 
     * @param path The directory path to be created.
     */
    private void createDirectory(String path) {
        if (super.isConnected()) {
            try {
                this.changeDirectory("/");
                this.makeDirectories(path);
                LOGGER.info("Directory Created: {}", path);
            } catch (IOException e) {
                LOGGER.error("Unable to Create Directory '{}'", path, e);
            }
        } else {
            LOGGER.warn("Unable to Create Directory '{}'. Connection is not open.", path);
        }
    }

    /**
     * Creates a nested directory structure on a FTP server
     * 
     * @param path Path of the directory, i.e /projects/java/ftp/demo
     * @return true if the directory was created successfully, false otherwise
     */
    private boolean makeDirectories(String path) throws IOException {
        List<String> pathElements = Arrays.asList(path.split("/")).stream().filter(p -> StringUtils.hasText(p))
                .collect(Collectors.toList());

        if (!Collections.isEmpty(pathElements)) {
            for (String singleDir : pathElements) {
                boolean exists = super.changeWorkingDirectory(singleDir);
                if (!exists) {
                    boolean created = super.makeDirectory(singleDir);
                    if (created) {
                        super.changeWorkingDirectory(singleDir);
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
