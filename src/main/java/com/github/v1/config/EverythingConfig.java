package com.github.v1.config;

import lombok.Data;

/**
 * EverythingConfig：配置信息
 * Author: zsm
 * Created: 2019/4/3
 */

@Data
public class EverythingConfig {

    private static volatile EverythingConfig config;

    /**
     * 索引目录
     */
    private Handlerpath handlerpath = Handlerpath.getDefaultHandlerPath();

    /**
     * 最大检索返回的结果数
     */
    private Integer maxReturn = 30;

    /**
     * 是否开启索引
     * 默认：关闭索引
     * 1.运行程序时，指定参数
     * 2.通过功能命令 index
     */
    private Boolean enableBuildIndex = false;

    /**
     * 检索时depth深度的排序规则
     * true：表示降序
     * false：表示升序
     * 默认：降序
     */
    private Boolean orderByDesc = false;

    /**
     * 文件监控时间
     */
    private Integer interval = 6000 * 10;

    // 构造方法
    private EverythingConfig(){}

    // 获取配置信息的方法
    public static EverythingConfig getInstance(){
        if (config == null){
            synchronized (EverythingConfig.class){
                if (config == null){
                    config = new EverythingConfig();
                }
            }
        }
        return config;
    }

}

