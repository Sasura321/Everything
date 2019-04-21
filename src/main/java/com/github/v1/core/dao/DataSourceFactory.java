package com.github.v1.core.dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.v1.core.model.Thing;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * DataSourceFactory：数据库的数据源
 * Author: zsm
 * Created: 2019/4/2
 */

public class DataSourceFactory {

    private static volatile DruidDataSource instance;

    private DataSourceFactory() {}

    public static DataSource getInstance() {
        if(instance == null) {
            synchronized (DataSource.class) {
                if(instance == null) {
                    instance = new DruidDataSource();
                    //instance.setUrl("jdbc:mysql://127.0.0.1:3306/MyEverything");

                    //这是连接MySQL的配置
//                    instance.setUrl("jdbc:mysql://localhost:3306/MyEverything?useUnicode=true&characterEncoding=utf-8&useSSL=false");
//                    instance.setUsername("root");
//                    instance.setPassword("123456");
//                    instance.setDriverClassName("com.mysql.jdbc.Driver");

                    //这是连接H2的配置
                    instance.setTestWhileIdle(false);
                    instance.setDriverClassName("org.h2.Driver");
                    String path = System.getProperty("user.dir")+File.separator+"MyEverything";
                    instance.setUrl("jdbc:h2:"+path);

                    //数据库创建表完成之后，初始化表结构
                    databaseInit(false);
                }
            }
        }
        return instance;
    }

    public static void databaseInit(boolean buildIndex) {
        StringBuffer sb = new StringBuffer();

        try (
            InputStream in = DataSourceFactory.class.getClassLoader().getResourceAsStream("database.sql");
        ){
            if(in != null) {
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                    String line = null;
                    while((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                throw new RuntimeException("database.sql script can't load please check it.");
            }

        }catch (IOException e) {
            e.printStackTrace();
        }

        String sql = sb.toString();

        try(Connection connection = getInstance().getConnection();
        ) {
            if(buildIndex) {
                try(PreparedStatement statement = connection.prepareStatement("drop table if exists thing;");) {
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            try(PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //测试
//    public static void main(String[] args) {
//        DataSourceFactory.databaseInit(false);
////
////        Thing thing = new Thing();
////
////        DataSource dataSource = DataSourceFactory.getInstance();
////
////        try (Connection connection = dataSource.getConnection();
////             PreparedStatement statement = connection.prepareStatement
////                     ("insert into thing(name,path,depth,file_type) values(?,?,?,?)")
////        ) {
////            statement.setString(1,"简历.pdf");
////            statement.setString(2,"C:\\abc\\简历.pdf");
////            statement.setInt(3,2);
////            statement.setString(4,"DOC");
////            statement.executeUpdate();
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
//    }

}
