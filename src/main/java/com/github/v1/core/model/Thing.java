package com.github.v1.core.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Thing模型类：
 * Author: zsm
 * Created: 2019/4/2
 */

@Data
public class Thing {

    /**
     * 文件名称（不包含路径）
     */
    private String name;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件路径深度
     */
    private Integer depth;

    /**
     * 文件类型
     */
    private FileType fileType;
}
