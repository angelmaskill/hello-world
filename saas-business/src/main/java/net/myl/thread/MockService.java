package net.myl.thread;

/**
 * @ClassName MockService
 * @Description TODO
 * @Author yanlu.myl
 * @Date 2020-10-22 11:40
 */

import java.util.concurrent.atomic.AtomicLong;

/**
 * 数据批量写入用的模拟服务
 *
 * @author RJH
 * create at 2019-04-01
 */
public class MockService {
    /**
     * 可读取总数
     */
    private long canReadTotal;
    /**
     * 写入总数
     */
    private AtomicLong writeTotal = new AtomicLong(0);
    /**
     * 写入休眠时间（单位：毫秒）
     */
    private final long sleepTime;

    /**
     * 构造方法
     *
     * @param canReadTotal
     * @param sleepTime
     */
    public MockService(long canReadTotal, long sleepTime) {
        this.canReadTotal = canReadTotal;
        this.sleepTime = sleepTime;
    }

    /**
     * 批量读取数据接口
     *
     * @param num
     * @return
     */
    public synchronized long readData(int num) {
        long readNum;
        if (canReadTotal >= num) {
            canReadTotal -= num;
            readNum = num;
        } else {
            readNum = canReadTotal;
            canReadTotal = 0;
        }
//System.out.println("read data size:" + readNum);
        return readNum;
    }

    /**
     * 写入数据接口
     */
    public void writeData() {
        try {
// 休眠一定时间模拟写入速度慢
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
// 写入总数自增
        System.out.println("thread:" + Thread.currentThread() + " write data:" + writeTotal.incrementAndGet());
    }

    /**
     * 获取写入的总数
     *
     * @return
     */
    public long getWriteTotal() {
        return writeTotal.get();
    }
}
