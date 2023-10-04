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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.fbl.common.annotations.interfaces.Client;

import io.jsonwebtoken.lang.Collections;

@Client
public class FTPStorageClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(FTPStorageClient.class);

    @Value("${ftp.environment:#{null}}")
    private String environment;

    @Autowired
    private FTPClient ftp;

    /**
     * Upload a file to the base storage path with the given file name.
     * 
     * @param is       The file to be stored.
     * @param fileName The name to store the file under.
     */
    public void upload(InputStream is, String fileName) {
        upload(is, "", fileName);
    }

    /**
     * Upload a file to given storage path with the given file name.
     * 
     * @param is       The file to be stored.
     * @param path     The location to store the file
     * @param fileName The name to store the file under.
     */
    public void upload(InputStream is, String path, String fileName) {
        String directoryPath = this.getBasePath();
        try {
            ftp.changeToParentDirectory();
            ftp.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
            if (StringUtils.hasText(path)) {
                directoryPath = String.format("%s/%s", this.getBasePath(), path);
            }

            if (!ftp.changeWorkingDirectory(directoryPath)) {
                createDirectory(directoryPath);
            }
            ftp.changeWorkingDirectory(directoryPath);

            ftp.storeFile(fileName, is);
            is.close();
            LOGGER.info("Successfully uploaded file: {}", String.format("%s/%s", directoryPath, fileName));
        } catch (Exception e) {
            LOGGER.error("Unable to save file to FTP Storage: {}", String.format("%s/%s", directoryPath, fileName), e);
        }
    }

    /**
     * Create a new directory for the given path.
     * 
     * @param path The directory path to be created.
     */
    public void createDirectory(String path) {
        if (ftp.isConnected()) {
            try {
                ftp.changeWorkingDirectory("/");
                this.makeDirectories(path);
                LOGGER.info("Successfully created directory: {}", path);
            } catch (IOException e) {
                LOGGER.error("Unable to create directory '{}'", path, e);
            }
        } else {
            LOGGER.warn("Could not create directory '{}'. Connection is not open.", path);
        }
    }

    /**
     * Get a list of files in the base path directory.
     * 
     * @return A list of {@link FTPFile}
     */
    public List<FTPFile> getFiles(String path, FTPFileFilter filter) {
        try {
            ftp.changeWorkingDirectory(String.format("%s/%s", this.getBasePath(), path));
            return Arrays.asList(ftp.listFiles("", filter));
        } catch (Exception e) {
            LOGGER.error("Unable to list files from FTP Storage!", e);
            return List.of();
        }
    }

    /**
     * Get a list of directories in the base path directory.
     * 
     * @return A list of {@link FTPFile}
     */
    public List<FTPFile> getDirectories() {
        try {
            changeToBaseDirectory();
            return Arrays.asList(ftp.listDirectories(this.getBasePath()));
        } catch (Exception e) {
            LOGGER.error("Unable to list directories from FTP Storage!", e);
            return List.of();
        }
    }

    private void changeToBaseDirectory() {
        try {
            ftp.changeWorkingDirectory(this.getBasePath());
        } catch (Exception e) {
            LOGGER.error("Unable to chagne to base directory", e);
        }
    }

    private String getBasePath() {
        return String.format("/disk1/fbl-cloud-%s", this.environment);
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
                boolean existed = ftp.changeWorkingDirectory(singleDir);
                if (!existed) {
                    boolean created = ftp.makeDirectory(singleDir);
                    if (created) {
                        ftp.changeWorkingDirectory(singleDir);
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}