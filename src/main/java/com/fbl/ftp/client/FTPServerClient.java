/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.ftp.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.util.StringUtils;

import com.fbl.common.annotations.interfaces.Client;
import com.fbl.exception.types.ServiceException;

import io.jsonwebtoken.lang.Collections;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * FTP Server Client wrapper for {@link FTPClient}.
 * 
 * @author Sam Butler
 * @since April 27, 2022
 */
@Slf4j
@Client
@AllArgsConstructor
@NoArgsConstructor
public class FTPServerClient extends FTPClient {

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
                throw new ServiceException("Connection Reply failed to FTP Server");
            }

            boolean loginSuccess = super.login(username, password);
            if (!loginSuccess) {
                throw new ServiceException("FTP Authentication Failed!");
            }

            super.enterLocalPassiveMode();
            super.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
            log.info("FTP Connected to Server!");
        } catch (Exception e) {
            log.error("Unable to establish FTP Connection!", e);
        }
    }

    /**
     * Close method to logout and disconnect the active session to the FTP server.
     */
    public void close() {
        if (this.isActive()) {
            try {
                super.logout();
                super.disconnect();
                log.info("FTP Connection Successfully Closed!");
            } catch (Exception e) {
                log.warn("Unable to close connection to FTP Server!", e);
            }
        }
    }

    /**
     * Perform a get on the FTP server. It will return a list of information based
     * on the path name.
     * 
     * @param path The path to pull the information from
     * @return List of {@link FTPFile}
     */
    public List<FTPFile> get(String path) {
        return get(path, null);
    }

    /**
     * Will get the file by the given path and name
     * 
     * @param path The path to pull the information from
     * @return {@link FTPFile}
     */
    public Optional<FTPFile> getFile(String path, String fileName) {
        List<FTPFile> files = get(path, f -> f.isFile());
        return files.stream().filter(f -> f.getName().equals(fileName)).findAny();
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
            FTPFile[] files = filter == null ? super.listFiles("") : super.listFiles("", filter);
            return Arrays.asList(files);
        } catch (Exception e) {
            log.error("Unable to list files from FTP Server!", e);
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
            log.info("File Uploaded: '{}'", String.format("%s/%s", path, fileName));
            return created;
        } catch (IOException e) {
            log.warn("Unable to create file: '{}'", String.format("%s/%s", path, fileName), e);
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
            log.info("File Deleted: '{}'", filePath);
            return deleted;
        } catch (IOException e) {
            log.warn("Unable to Delete File: '{}'", filePath, e);
            return false;
        }
    }

    /**
     * Deletes a directory on the FTP server.
     * 
     * @param path The path to the directory to delete
     * @return Boolean if the directory was deleted or not.
     */
    public boolean deleteDirectory(String path) {
        this.checkConnection();
        try {
            boolean deleted = super.removeDirectory(path);
            log.info("Directory Deleted: '{}'", path);
            return deleted;
        } catch (IOException e) {
            log.warn("Unable to Delete Directory: '{}'", path, e);
            return false;
        }
    }

    /**
     * Returns an InputStream from which a named file from the server can be read.
     * If the current file type is ASCII, the returned InputStream will convert line
     * separators in the file to the local representation. You must close the
     * InputStream when you finish reading from it. The InputStream itself will take
     * care of closing the parent data connection socket upon being closed.
     *
     * @param path The path to the file
     * @return The input stream of file if found, otherwise a null stream
     */
    public InputStream download(String path) {
        this.checkConnection();
        try {
            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
            boolean fileCopied = super.retrieveFile(path, byteOutStream);

            if (fileCopied) {
                log.info("File Downloaded: {}", path);
                return new ByteArrayInputStream(byteOutStream.toByteArray());
            } else {
                log.error("Unable to get file input stream: {}", path);
                return null;
            }

        } catch (Exception e) {
            log.error("Unable to get file input stream: {}", path, e);
            return null;
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
            log.error("Unable to change to base directory", e);
            return false;
        }
    }

    /**
     * Checks the FTP connection that it is still connected. If not it will attempt
     * a reconnection.
     */
    public void checkConnection() {
        if (!this.isActive()) {
            log.info("FTP Server not Connected. Attempting Reconnect...");
            connect();
        }
    }

    /**
     * Checks to see if the current FTP Connection is active on the server. Will
     * first check if the FTP is connected, if is connected then it will send a NOOP
     * command to confirm it is active.
     * 
     * @return True if the server is active, otherwise false.
     */
    public boolean isActive() {
        try {
            return super.isConnected() ? super.sendNoOp() : false;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Create a new directory for the given path.
     * 
     * @param path The directory path to be created.
     */
    public void createDirectory(String path) {
        this.checkConnection();
        try {
            this.changeDirectory("/");
            this.makeDirectories(path);
            log.info("Directory Created: {}", path);
        } catch (IOException e) {
            log.error("Unable to Create Directory '{}'", path, e);
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
