package com.github.v1.core.search;

import com.github.v1.core.model.Condition;
import com.github.v1.core.model.Thing;

import java.util.List;

/**
 * ThingSearch接口：文件检索业务
 * <p>
 * Author: zsm
 * Created: 2019/4/2
 */

public interface ThingSearch {

    /**
     * 根据condition条件检索数据
     * @param  condition
     * @return
     */

    List<Thing> search(Condition condition);
}
