package com.github.v1.cmd;

import com.github.v1.config.EverythingConfig;
import com.github.v1.core.EverythingManager;
import com.github.v1.core.model.Condition;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

/**
 * Everything主程序
 * 基于字符界面进行交互式访问
 */

public class EverythingCmdApplication {
    public static void main(String[] args) {

        //0、everything Config
        if (args.length >= 1){
            String configFile = args[0];
            Properties p = new Properties();
            try {

                p.load(new FileInputStream(configFile));
                //p 的值赋值给everythingconfig对象
                everythingConfigInit(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //1、EverythingManager
        EverythingManager manager = new EverythingManager().getInstance();
        // 监控
        manager.monitor();

        //2、命令行交互
        Scanner scanner = new Scanner(System.in);

        //3、用户交互输入
        System.out.println("欢迎使用everything！");
        while(true) {
            System.out.print(">>");
            String line = scanner.nextLine();

            switch (line){
                case "help":
                    manager.help();
                    break;
                case "quit":{
                    manager.quit();
                    break;
                }
                case "index":{
                    manager.buildIndex();
                    break;
                }
                default:{
                    if (line.startsWith("search")){
                        //解析参数
                        String[] segments = line.split(" ");

                        if (segments.length >= 2){
                            Condition condition = new Condition();
                            String name = segments[1];
                            condition.setName(name);
                            if (segments.length >= 3){
                                String type = segments[2];
                                condition.setFileType(type.toUpperCase());
                            }
                            manager.search(condition).forEach(thing -> {
                                System.out.println(thing.getPath());
                            });
                        }else {
                            manager.help();
                        }
                    }else {
                        manager.help();
                    }
                }
            }
        }

    }

    private static void everythingConfigInit(Properties p) {
        EverythingConfig config = EverythingConfig.getInstance();
        String maxreturn = p.getProperty("everything.max_return");
        String interval = p.getProperty("everything.interval");
        try {
            if (interval != null){
                Integer.parseInt(interval);
            }
            if (maxreturn != null){
                config.setMaxReturn(Integer.parseInt(maxreturn));
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        String enableBuildIndex = p.getProperty("everything.enable_build_index");
        config.setEnableBuildIndex(Boolean.parseBoolean(enableBuildIndex));

        String orderByDesc = p.getProperty("everything.order_by_desc");
        config.setOrderByDesc(Boolean.parseBoolean(orderByDesc));

        //处理的目录
        String includePaths = p.getProperty("everything.handle_path.include_path");
        if (includePaths != null){

            String[] paths = includePaths.split(";");
            if (paths.length > 0){
                //清理已经有的默认值
                config.getHandlerpath().getIncludePath().clear();
                for (String path : paths){
                    config.getHandlerpath().addIncludePath(path);
                }
            }
        }

        String excludePaths = p.getProperty("everything.handle_path.exclude_path");
        if (excludePaths != null){

            String[] paths = excludePaths.split(";");
            if (paths.length > 0){
                //清理已经有的默认值
                config.getHandlerpath().getExcludePath().clear();
                for (String path : paths){
                    config.getHandlerpath().addExcludePath(path);
                }
            }
        }

        System.out.println(config);
    }
}
