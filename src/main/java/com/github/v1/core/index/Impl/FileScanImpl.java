package com.github.v1.core.index.Impl;

import com.github.v1.config.EverythingConfig;
import com.github.v1.core.index.FileScan;
import com.github.v1.core.interceptor.FileInterceptor;

import java.io.File;
import java.util.LinkedList;
import java.util.Set;

/**
 * FileScanImpl：建立索引
 * Author: zsm
 * Created: 2019/4/3
 */

public class FileScanImpl implements FileScan {

    private final LinkedList<FileInterceptor> interceptors = new LinkedList<>();

    private EverythingConfig config = EverythingConfig.getInstance();

    /**
     * 将指定path路径下的所有文件和目录以及子目录和文件递归扫描索引到数据库
     * @param path 文件路径
     */
    @Override
    public void index(String path) {
        Set<String> excludePaths = config.getHandlerpath().getExcludePath();

        for(String excludePath : excludePaths) {
            if(path.startsWith(excludePath)) {
                return;
            }
        }

        // 判断路径是否是文件夹
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

    /**
     * 文件扫描接口增加拦截器对象
     * @param interceptor
     */
    @Override
    public void interceptor(FileInterceptor interceptor) {
        this.interceptors.add(interceptor);
    }
}

