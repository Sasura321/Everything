package com.github.v1.core.dao.impl;

import com.github.v1.core.dao.FileIndexDao;
import com.github.v1.core.model.FileType;
import com.github.v1.core.model.Thing;
import com.github.v1.core.model.Condition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * FileIndexDaoImpl:
 * Author: zsm
 * Created: 2019/4/2
 */

public class FileIndexDaoImpl  implements FileIndexDao{

    //DataSource.getConnection 通过数据源工厂获取DataSource实例化对象
    private final DataSource dataSource;

    public FileIndexDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 插入
     * @param thing
     */
    @Override
    public void insert(Thing thing) {
        //JDBC操作
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.dataSource.getConnection();
            String sql = "insert into thing(name,path,depth,file_type) values(?,?,?,?)";
            statement = connection.prepareStatement(sql);
            //预编译命令中SQL的占位符赋值
            statement.setString(1,thing.getName());
            statement.setString(2,thing.getPath());
            statement.setInt(3,thing.getDepth());
            statement.setString(4,thing.getFileType().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseResource(null,statement,connection);
        }
    }

    /**
     * 删除
     * @param thing
     */
    @Override
    public void delete(Thing thing) {
        /*
         * = 最多删除一个，绝对匹配
         */
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.dataSource.getConnection();
            String sql = "delete from thing where path = ?";
            statement = connection.prepareStatement(sql);
            //预编译命令中SQL的占位符赋值
            statement.setString(1,thing.getPath());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            releaseResource(null,statement,connection);
        }
    }

    /**
     * 查询
     * @param condition
     * @return
     */
    @Override
    public List<Thing> query(Condition condition) {
        List<Thing> things = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dataSource.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("select name,path,depth,file_type from thing");
            sb.append(" where ");

            //采用模糊匹配
            sb.append(" name like '").append(condition.getName()).append("%'");
            //search<name> [file_type]
            if(condition.getFileType() != null){
                FileType fileType = FileType.lookupByName(condition.getFileType());
                sb.append(" and file_type='"+fileType.name()+"'");
            }
            sb.append(" order by depth").append(condition.getOrderByDepthAsc()?" asc":" desc");
            sb.append(" limit ").append(condition.getLimit());

            statement = connection.prepareStatement(sb.toString());
            resultSet = statement.executeQuery();
            //处理结果
            while(resultSet.next()){
                Thing thing = new Thing();
                thing.setName(resultSet.getString("name"));
                thing.setPath(resultSet.getString("path"));
                thing.setDepth(resultSet.getInt("depth"));
                thing.setFileType(FileType.lookupByName(resultSet.getString("file_type")));

                things.add(thing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            releaseResource(resultSet,statement,connection);
        }
        return things;
    }

    /**
     * 重构
     * 在不改变程序的功能和业务的前提下，对代码进行优化，是的代码更易阅读和扩展
     */
    private void releaseResource(ResultSet resultSet,PreparedStatement statement,Connection connection){
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(statement != null){
            try {
                //???
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection != null){
            try {
                //???
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

////    //测试
//    public static void main(String[] args) {
//        DataSource dataSource = DataSourceFactory.getInstance();
//        FileIndexDao dao = new FileIndexDaoImpl(dataSource);
//        System.out.println(dao);
//
//        //测试
////        Thing thing = new Thing();
////        thing.setName("Java数据结构.pdf");
////        thing.setPath("C:\\a\\Java数据结构.pdf");
////        thing.setDepth(2);
////        thing.setFileType(FileType.DOC);
//    }
}
