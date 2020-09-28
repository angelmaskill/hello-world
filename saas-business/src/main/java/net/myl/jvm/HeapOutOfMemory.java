package net.myl.jvm;


import java.util.ArrayList;
import java.util.List;

/**
 * @author myl
 * @Described：堆溢出测试
 * @VM args:-verbose:gc -Xms20M -Xmx20M -XX:+PrintGCDetails
 */

public class HeapOutOfMemory {
    public static void main(String[] args) {
        List<TestCase> cases = new ArrayList<TestCase>();
        while (true) {
            cases.add(new TestCase());
        }
    }
}

/**
 * @ClassName TestCase
 * @Description 测试类
 * @Author yanlu.myl
 * @Date 2020-09-28 10:34
 */
class TestCase {
}
