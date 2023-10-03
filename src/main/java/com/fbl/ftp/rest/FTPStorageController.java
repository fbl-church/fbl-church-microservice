package com.fbl.ftp.rest;

import static org.springframework.http.MediaType.*;

import java.util.List;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fbl.app.user.openapi.TagUser;
import com.fbl.ftp.service.FTPStorageService;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/api/ftp-storage")
@RestController
@TagUser
public class FTPStorageController {

    @Autowired
    private FTPStorageService service;

    /**
     * Gets a list of files in the base directory
     * 
     * @return list of files
     */
    @Operation(summary = "Upload a file to FTP Storage", description = "Given a a file and name. It will upload the file to the FTP Storage.")
    @GetMapping("/files")
    public List<FTPFile> getFiles() {
        return service.getFiles();
    }

    /**
     * Gets a list of users based of the request filter
     * 
     * @param request to filter on
     * @return list of user objects
     */
    @Operation(summary = "Upload a file to FTP Storage", description = "Given a a file and name. It will upload the file to the FTP Storage.")
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public void upload(@RequestParam("file") MultipartFile file) {
        service.upload(file);
    }

}
