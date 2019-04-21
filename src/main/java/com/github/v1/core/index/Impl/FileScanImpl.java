package com.github.v1.core.index.Impl;

import com.github.v1.config.EverythingConfig;
import com.github.v1.core.index.FileScan;
import com.github.v1.core.interceptor.FileInterceptor;

import java.io.File;
import java.util.LinkedList;
import java.util.Set;

/**
 * FileScanImpl：
 * Author: zsm
 * Created: 2019/4/3
 */

public class FileScanImpl implements FileScan {

    private final LinkedList<FileInterceptor> interceptors = new LinkedList<>();

    private EverythingConfig config = EverythingConfig.getInstance();

    @Override
    public void index(String path) {
        Set<String> excludePaths = config.getHandlerpath().getExcludePath();

        for(String excludePath : excludePaths) {
            if(path.startsWith(excludePath)) {
                return;
            }
        }

        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null){
                for (File f : files){
                    index(f.getAbsolutePath());
                }
            }
        }

        for (FileInterceptor interceptor : this.interceptors){
            interceptor.apply(file);
        }
    }

    @Override
    public void interceptor(FileInterceptor interceptor) {
        this.interceptors.add(interceptor);
    }
}

