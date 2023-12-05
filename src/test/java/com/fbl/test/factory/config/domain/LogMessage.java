package com.fbl.test.factory.config.domain;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * Log Message
 * 
 * @author Sam Butler
 * @since Dec 05, 2023
 */
public class LogMessage {
    private Level level;
    private String message;
    private String loggerName;
    private Class<?> clazz;

    public LogMessage(ILoggingEvent log) {
        this.level = log.getLevel();
        this.message = log.getFormattedMessage();
        this.loggerName = log.getLoggerName();
        this.clazz = log.getClass();
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

}
