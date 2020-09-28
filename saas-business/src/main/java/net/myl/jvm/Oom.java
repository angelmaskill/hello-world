package net.myl.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author myl
 * @Described：栈层级不足探究
 * @VM args:-Xss128k
 */

public class Oom {
    /**
     * 内存占位符对象：一个 OOMObject 对象大约占 10KB
     */
    static class OOMObject {
        byte[] placeholder = new byte[64 * 1024];
    }


    private static void fillHeapTest() throws InterruptedException {
        Thread.sleep(5000);
        fillHeap(1000);//  模拟oom
        System.gc();
        Thread.sleep(5000);
    }

    /**
     * 4-8 jConsole 监控代码
     * <p>
     * 虚拟机参数：
     * -Xmx10m -Xms10m -XX:+UseSerialGC -XX:+PrintGCDetails   -XX:+DisableExplicitGC -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\oom\oom.hprof            模拟内存溢出
     * </p>
     *
     * @param num
     * @throws InterruptedException
     */
    private static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            // 稍作延迟，令监控曲线变化更加明显
            Thread.sleep(50);
            list.add(new OOMObject());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        fillHeapTest();
    }
}
