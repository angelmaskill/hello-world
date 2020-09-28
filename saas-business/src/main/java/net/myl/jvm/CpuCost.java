package net.myl.jvm;

/**
 * @author myl
 * @Described：模拟内存飚高
 */
public class CpuCost {
    public static void main(String[] args) {
        for(;;){
            double v = 10d / 3d;
        }
    }
}