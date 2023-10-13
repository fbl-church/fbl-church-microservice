/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fbl.ftp.client.FTPServerClient;

import jakarta.annotation.PreDestroy;

/**
 * FTP Client config.
 * 
 * @author Sam Butler
 * @since April 27, 2022
 */
@Configuration
public class FTPServerClientConfiguration {

    @Value("${ftp.server:#{null}}")
    private String server;

    @Value("${ftp.port:#{null}}")
    private int port;

    @Value("${ftp.username:#{null}}")
    private String username;

    @Value("${ftp.password:#{null}}")
    private String password;

    FTPServerClient ftp;

    @Bean
    @Primary
    FTPServerClient ftpServerClient() {
        ftp = new FTPServerClient(server, port, username, password);
        ftp.connect();
        return ftp;
    }

    @PreDestroy
    void cleanup() {
        ftp.close();
    }
}
