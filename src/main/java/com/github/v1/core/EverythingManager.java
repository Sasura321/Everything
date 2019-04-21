package com.github.v1.core;

import com.github.v1.config.EverythingConfig;
import com.github.v1.config.Handlerpath;
import com.github.v1.core.dao.DataSourceFactory;
import com.github.v1.core.dao.FileIndexDao;
import com.github.v1.core.dao.impl.FileIndexDaoImpl;
import com.github.v1.core.index.FileScan;
import com.github.v1.core.index.Impl.FileScanImpl;
import com.github.v1.core.interceptor.impl.FileIndexInterceptor;
import com.github.v1.core.interceptor.impl.FilePrintInterceptor;
import com.github.v1.core.model.Condition;
import com.github.v1.core.model.Thing;
import com.github.v1.core.monitor.FileMonitor;
import com.github.v1.core.monitor.Impl.FileMonitorImpl;
import com.github.v1.core.search.ThingSearch;
import com.github.v1.core.search.impl.ThingSearchImpl;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

 /* EverythingManager：核心统一调度器
 * Author: zsm
 * Created: 2019/4/3
 */

public class EverythingManager {
    private static volatile EverythingManager manager;

    /**
     * 业务层
     */
    private FileScan fileScan;
    private ThingSearch thingSearch;

    /**
     * 数据库访问层
     */
    private FileIndexDao fileIndexDao;

    /**
     * 线程池的执行器
     */
    private final ExecutorService executorService = Executors.
            newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);

    private EverythingConfig config = EverythingConfig.getInstance();

    private FileMonitor fileMonitor;

    /**
     * 数据库访问层
     */
    public EverythingManager() {
        FileIndexDao fileIndexDao = new FileIndexDaoImpl(DataSourceFactory.getInstance());

        this.fileScan = new FileScanImpl();

        // 打印索引信息的拦截器
        //this.fileScan.interceptor(new FilePrintInterceptor());

        //索引信息写数据库的拦截器
        this.fileScan.interceptor(new FileIndexInterceptor(fileIndexDao));

        this.thingSearch = new ThingSearchImpl(fileIndexDao);

        this.fileMonitor = new FileMonitorImpl(fileIndexDao);
    }

    public static EverythingManager getInstance() {
        if(manager == null) {
            synchronized (EverythingManager.class) {
                if(manager == null) {
                    manager = new EverythingManager();
                }
            }
        }
        return manager;
    }

    /**
     * 构建索引
     */
    public void buildIndex() {
        DataSourceFactory.databaseInit(true);
        Handlerpath handlerpath = config.getHandlerpath();
        //handlerpath.getIncludePath();

        Set<String> includePaths = handlerpath.getIncludePath();
        new Thread(() -> {
            System.out.println("Buid Index Started ...");
            final CountDownLatch countDownLatch = new CountDownLatch(includePaths.size());
            for (String path: includePaths) {
                executorService.submit(() -> {
                    fileScan.index(path);
                    countDownLatch.countDown();
                });
            }

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Build Index Complete ...");
        }).start();
    }

    /**
     * 索引功能
     * @param condition
     * @return condition
     */
    public List<Thing> search(Condition condition) {
        //Condition 用户提供：name.file_type
        condition.setLimit(config.getMaxReturn());
        condition.setOrderByDepthAsc(!config.getOrderByDesc());
        return this.thingSearch.search(condition);
    }

    /**
     * 帮助
     */
    public void help() {
        /**
         * 命令列表：
         * 退出：quit
         * 帮助：help
         * 索引：index
         * 搜索：search <name> [/...(其他)]
         */
        System.out.println("命令列表：");
        System.out.println("退出：quit");
        System.out.println("帮助：help");
        System.out.println("索引：index");
        System.out.println("搜索：search <name> [/...(其他)] ");
    }

    /**
     * 退出
     */
    public void quit() {
        System.out.println("欢迎使用，再见！");
        System.exit(0);
    }

    /**
     * 启动文件监控
     */
    public void monitor(){
        new Thread(() -> {
            fileMonitor.monitor(config.getHandlerpath());
            fileMonitor.start();
        }).start();
    }

}

