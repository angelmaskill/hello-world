package net.myl.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.8之后,常量池移出方法区.
 * @Described：常量池内存溢出探究
 * @VM args : -XX:PermSize=10M -XX:MaxPermSize=10M
 */

public class ConstantOutOfMemory {
    public static void main(String[] args) {
        try {
            List<String> strings = new ArrayList<>();
            int i = 0;
            while (true) {
                strings.add(String.valueOf(i++).intern());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}