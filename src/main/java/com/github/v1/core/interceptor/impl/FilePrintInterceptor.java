package com.github.v1.core.interceptor.impl;

import com.github.v1.core.interceptor.FileInterceptor;

import java.io.File;

/**
 * 打印文件拦截器
 * Author: zsm
 * Created: 2019/4/2
 */
public class FilePrintInterceptor implements FileInterceptor {

    @Override
    public void apply(File file) {
        System.out.println(file.getAbsolutePath());
    }
}
