package com.fbl.test.factory.config;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;

import com.fbl.test.factory.config.domain.LogMessage;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

/**
 * Logger Listener
 * 
 * @author Sam Butler
 * @since Dec 05, 2023
 */
public class LoggerListener {

    private ListAppender<ILoggingEvent> watcher;

    public LoggerListener(ListAppender<ILoggingEvent> watcher) {
        this.watcher = watcher;
    }

    public List<LogMessage> getLogs() {
        return watcher.list.stream().map(l -> new LogMessage(l)).collect(Collectors.toList());
    }

    public static <T> LoggerListener start(Class<T> clazz) {
        ListAppender<ILoggingEvent> logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(clazz)).addAppender(logWatcher);
        return new LoggerListener(logWatcher);
    }
}
