package com.fbl.configs;

import javax.annotation.PreDestroy;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fbl.exception.types.BaseException;

/**
 * FTP Client config.
 * 
 * @author Sam Butler
 * @since April 27, 2022
 */
@Configuration
public class FTPClientConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FTPClientConfiguration.class);

    @Value("${ftp.server:#{null}}")
    private String server;

    @Value("${ftp.port:#{null}}")
    private int port;

    @Value("${ftp.username:#{null}}")
    private String username;

    @Value("${ftp.password:#{null}}")
    private String password;

    FTPClient ftp;

    @Bean
    @Primary
    FTPClient ftpClient() {
        ftp = new FTPClient();
        try {
            ftp.connect(server, port);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                throw new BaseException("Connection Reply failed to FTP Storage");
            }

            boolean loginSuccess = ftp.login(username, password);
            if (!loginSuccess) {
                throw new BaseException("FTP Authentication Failed!");
            }

            ftp.enterLocalPassiveMode();
            return ftp;
        } catch (Exception e) {
            throw new BaseException("Unable to connect to FTP Storage");
        }
    }

    @PreDestroy
    void cleanup() {
        try {
            ftp.logout();
            ftp.disconnect();
            LOGGER.info("FTP Connection Successfully Closed!");
        } catch (Exception e) {
            throw new BaseException("Unable to close connection to FTP Storage!");
        }

    }
}
