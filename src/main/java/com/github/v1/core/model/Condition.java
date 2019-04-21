package com.github.v1.core.model;

import lombok.Data;

/**
 * Condition模型类：检索条件
 * Author: zsm
 * Created: 2019/4/2
 */

@Data
public class Condition {

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 限制的数量
     */
    private Integer limit;

    /**
     * 是否按照dept升序
     */
    public Boolean orderByDepthAsc;


}
