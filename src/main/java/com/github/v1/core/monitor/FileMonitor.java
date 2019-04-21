package com.github.v1.core.monitor;

import com.github.v1.config.Handlerpath;

public interface FileMonitor {

    void start();

    /**
     * 监控
     */
    void monitor(Handlerpath handlerpath);

    void stop();
}
