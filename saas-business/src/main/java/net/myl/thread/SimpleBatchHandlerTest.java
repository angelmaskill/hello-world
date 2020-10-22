package net.myl.thread;

/**
 * @ClassName SimpleBatchHandlerTest
 * @Description TODO
 * @Author yanlu.myl
 * @Date 2020-10-22 11:41
 */
public class SimpleBatchHandlerTest {
    public static void main(String[] args) {
// 总数
        long total = 100000;
// 休眠时间
        long sleepTime = 100;
// 每次拉取的数量
        int batch = 100;
// 线程个数
        int threadNum = 16;
        MockService mockService = new MockService(total, sleepTime);
        SimpleBatchHandler handler = new SimpleBatchHandler(mockService, batch, threadNum);
        handler.startHandle();
    }
}
