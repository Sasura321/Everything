package com.github.v1.core.interceptor.impl;

import com.github.v1.core.dao.FileIndexDao;
import com.github.v1.core.interceptor.ThingInterceptor;
import com.github.v1.core.model.Thing;

import java.util.Queue;

public class ThingClearInterceptor implements Runnable ,ThingInterceptor {

    private final FileIndexDao fileIndexDao;
    private Queue<Thing> thingQueue;

    public ThingClearInterceptor(FileIndexDao fileIndexDao, Queue<Thing> thingQueue) {
        this.fileIndexDao = fileIndexDao;
        this.thingQueue = thingQueue;
    }

    @Override
    public void apply(Thing thing) {
        this.fileIndexDao.delete(thing);
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thing thing = this.thingQueue.poll();
            if (thing != null){
                this.apply(thing);
            }
        }
    }
}
