package com.github.v1.core.dao;

import com.github.v1.core.model.Condition;
import com.github.v1.core.model.Thing;

import java.util.List;

/**
 * FileIndexDao接口:数据库访问的对象
 * Author: zsm
 * Created: 2019/4/2
 */

public interface FileIndexDao {

    /**
     * 插入
     * @param thing
     */
    void insert(Thing thing);

    /**
     * 删除
     * @param thing
     */
    void delete(Thing thing);

    /**
     * 查询
     * @param condition
     */
    List<Thing> query(Condition condition);

}
