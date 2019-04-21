package com.github.v1.core.common;

import com.github.v1.core.model.FileType;
import com.github.v1.core.model.Thing;

import java.io.File;

/**
 * FileConverThing:文件对象转换Thing对象的辅助类
 * Author: zsm
 * Created: 2019/4/2
 */

public class FileConverThing {
    public static Thing convert(File file){
        Thing thing = new Thing();
        String name = file.getName();
        thing.setName(name);
        thing.setPath(file.getPath());

        int index = name.lastIndexOf(".");
        String extend = "*";
        if (index != -1 && (index+1 < name.length())){

            extend =  name.substring(index+1);
        }
        thing.setFileType(FileType.lookupByName(extend));
        thing.setDepth(computePathDepth("C:"));

        return thing;
    }

    private static int computePathDepth(String path){
        //计算路径长度
        int depth = 0;
        for (char c : path.toCharArray()){
            if (c == File.separatorChar){
                depth += 1;
            }
        }

        return depth;
    }

//    //测试
//    public static void main(String[] args) {
//        File file = new File("C:\\Data\\csdn.py");
//        Thing thing = FileConverThing.convert(file);
//        System.out.println(thing);
//    }
}

