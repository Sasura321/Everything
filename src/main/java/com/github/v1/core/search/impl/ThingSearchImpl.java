package com.github.v1.core.search.impl;

import com.github.v1.core.dao.FileIndexDao;
import com.github.v1.core.interceptor.impl.ThingClearInterceptor;
import com.github.v1.core.model.Condition;
import com.github.v1.core.model.Thing;
import com.github.v1.core.search.ThingSearch;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * ThingSearchImpl:
 * Author: zsm
 * Created: 2019/4/2
 */

public class ThingSearchImpl implements ThingSearch {

    private final FileIndexDao fileIndexDao;

    private final ThingClearInterceptor interceptor;

    private final Queue<Thing> thingQueue = new ArrayBlockingQueue<Thing>(1024);

    public ThingSearchImpl(FileIndexDao fileIndexDao) {
        this.fileIndexDao = fileIndexDao;
        this.interceptor = new ThingClearInterceptor(this.fileIndexDao, thingQueue);
        this.backgroundClearThread();
    }

    @Override
    public List<Thing> search(Condition condition) {
        //BUG
        List<Thing> things = this.fileIndexDao.query(condition);
        Iterator<Thing> iterator = things.iterator();
        while(iterator.hasNext()){
            Thing thing = iterator.next();
            File file = new File(thing.getPath());
            //如果文件不存在
            if(!file.exists()) {
                iterator.remove(); // 删除
                this.thingQueue.add(thing);
            }
        }

        return things;
    }

    private void backgroundClearThread() {
        // 进行一个后台清理操作
        Thread thread = new Thread(this.interceptor);
        thread.setName("Thread-Clear");
        thread.setDaemon(true);
        thread.start();
    }
}
