package com.github.v1.core.interceptor.impl;

import com.github.v1.core.common.FileConverThing;
import com.github.v1.core.dao.FileIndexDao;
import com.github.v1.core.interceptor.FileInterceptor;
import com.github.v1.core.model.Thing;

import java.io.File;

/**
 * FileIndexInterceptor：将Filie转换为Thing然后写入数据库（拦截器）
 * Author: zsm
 * Created: 2019/4/2
 */

public class FileIndexInterceptor implements FileInterceptor {
    private final FileIndexDao fileIndexDao;

    public FileIndexInterceptor(FileIndexDao fileIndexDao) {
        this.fileIndexDao = fileIndexDao;
    }

    //打印，转换，写入数据库
    @Override
    public void apply(File file) {

        Thing thing = FileConverThing.convert(file);
        this.fileIndexDao.insert(thing);

    }
}
