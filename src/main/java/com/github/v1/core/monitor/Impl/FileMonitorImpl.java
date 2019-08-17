package com.github.v1.core.monitor.Impl;

import com.github.v1.config.EverythingConfig;
import com.github.v1.config.Handlerpath;
import com.github.v1.core.common.FileConverThing;
import com.github.v1.core.dao.FileIndexDao;
import com.github.v1.core.monitor.FileMonitor;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.util.Set;

public class FileMonitorImpl extends FileAlterationListenerAdaptor implements FileMonitor {

    private FileAlterationMonitor monitor;

    private final FileIndexDao fileIndexDao;

    public FileMonitorImpl(FileIndexDao fileIndexDao) {
        this.fileIndexDao = fileIndexDao;
        this.monitor = new FileAlterationMonitor(
                EverythingConfig.getInstance().getInterval()
        );
    }

    @Override
    public void start() {
        //启动
        try {
            this.monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void monitor(Handlerpath handlerpath) {
        //监控的目录
        Set<String> includePath =  handlerpath.getIncludePath();
        for (String path : includePath){
            FileAlterationObserver observer = new FileAlterationObserver(new File(path), pathname -> {
                for (String exclude : handlerpath.getIncludePath()){
                    if (pathname.getAbsolutePath().startsWith(exclude)){
                        return false;
                    }
                }
                return true;
            }); //要监控的目录

            observer.addListener(this);//监听，当前对象
            this.monitor.addObserver(observer);
        }

    }

    //覆写方法
    @Override
    public void onDirectoryCreate(File directory) {
        System.out.println("onDirectoryCreate: "+directory.getAbsolutePath());

        this.fileIndexDao.insert(FileConverThing.convert(directory));
    }

    @Override
    public void onDirectoryDelete(File directory) {
        System.out.println("onDirectoryDelete: "+directory.getAbsolutePath());

        this.fileIndexDao.insert(FileConverThing.convert(directory));
    }

    @Override
    public void onFileCreate(File file) {
        System.out.println("onFileCreate: "+file.getAbsolutePath());

        this.fileIndexDao.insert(FileConverThing.convert(file));
    }

    @Override
    public void onFileDelete(File file) {
        System.out.println("onFileDelete: "+file.getAbsolutePath());

        this.fileIndexDao.insert(FileConverThing.convert(file));
    }

    @Override
    public void stop() {
        //停止
        try {
            this.monitor.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
