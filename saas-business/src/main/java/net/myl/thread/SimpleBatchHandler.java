package net.myl.thread;

/**
 * @ClassName SimpleBatchHandler
 * @Description TODO
 * @Author yanlu.myl
 * @Date 2020-10-22 11:40
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 基于线程池的多线程批量写入处理器
 *
 * @author RJH
 * create at 2019-04-01
 */
public class SimpleBatchHandler {
    private ExecutorService executorService;
    private MockService service;
    /**
     * 每次批量读取的数据量
     */
    private int batch;
    /**
     * 线程个数
     */
    private int threadNum;

    public SimpleBatchHandler(MockService service, int batch, int threadNum) {
        this.service = service;
        this.batch = batch;
//使用固定数目的线程池
        this.executorService = Executors.newFixedThreadPool(threadNum);
    }

    /**
     * 开始处理
     */
    public void startHandle() {
// 开始处理的时间
        long startTime = System.currentTimeMillis();
        System.out.println("start handle time:" + startTime);
        long readData;
        while ((readData = service.readData(batch)) != 0) {// 批量读取数据，知道读取不到数据才停止
            for (long i = 0; i < readData; i++) {
                executorService.execute(() -> service.writeData());
            }
        }
// 关闭线程池
        executorService.shutdown();
        while (!executorService.isTerminated()) {//等待线程池中的线程执行完
        }
// 结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("end handle time:" + endTime);
// 总耗时
        System.out.println("total handle time:" + (endTime - startTime) + "ms");
// 写入总数
        System.out.println("total write num:" + service.getWriteTotal());
    }
}