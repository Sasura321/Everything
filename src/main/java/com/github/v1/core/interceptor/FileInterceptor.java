package com.github.v1.core.interceptor;

import java.io.File;

public interface FileInterceptor {

    /**
     * 文件拦截器，处理指定文件
     * @param file
     */
    void apply(File file);

}
