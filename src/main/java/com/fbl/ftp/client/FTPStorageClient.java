package com.fbl.ftp.client;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.fbl.common.annotations.interfaces.Client;

@Client
public class FTPStorageClient {

    @Value("${ftp.environment:#{null}}")
    private String environment;

    @Autowired
    private FTPServerClient ftp;

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
        if (StringUtils.hasText(path)) {
            directoryPath = String.format("%s/%s", this.getBasePath(), path);
        }

        ftp.storeFile(directoryPath, fileName, is);
    }

    /**
     * Get a list of files and directories in the base path directory.
     * 
     * @return A list of {@link FTPFile}
     */
    public List<FTPFile> get(String path, FTPFileFilter filter) {
        return ftp.get(String.format("%s/%s", this.getBasePath(), path), filter);
    }

    /**
     * Get the base ftp session directory for the application
     * 
     * @return String of the application ftp path
     */
    private String getBasePath() {
        return String.format("/disk1/fbl-cloud-%s", this.environment);
    }
}