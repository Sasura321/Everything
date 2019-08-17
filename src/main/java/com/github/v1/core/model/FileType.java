package com.github.v1.core.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * FileType模型类：表示文件的扩展归类
 * <p>
 * Author: zsm
 * Created: 2019/4/2
 */

public enum FileType {

    IMG("jpg","jpeg","bmp","gif"),
    DOC("doc","docx","pdf","ppt","pptx","xls","txt"),
    BIN("exe","jar","sh","msi"),
    ARCHIVE("zip","rar"),
    OTHER("*");

    private Set<String> extend = new HashSet<String>();

    FileType(String... extend) {
        this.extend.addAll(Arrays.asList(extend));
    }

    // 通过扩展名查找文件
    public static FileType lookupByExtend(String extend) {
        for(FileType fileType : FileType.values()) {
            if(fileType.extend.contains(extend)) {
                return fileType;
            }
        }
        return FileType.OTHER;
    }

    // 通过文件名称查找文件
    public static FileType lookupByName(String name) {
        for(FileType fileType : FileType.values()) {
            if(fileType.name().equals((name))) {
                return fileType;
            }
        }
        return FileType.OTHER;
    }

}
