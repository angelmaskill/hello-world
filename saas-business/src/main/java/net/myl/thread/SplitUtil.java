package net.myl.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SplitUtil
 * @Description TODO
 * @Author yanlu.myl
 * @Date 2020-10-22 11:53
 */
public class SplitUtil {
    /**
     * 将一组数据平均分成n组
     *
     * @param source   要分组的数据源
     * @param groupNum 平均分成n组
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int groupNum) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % groupNum;  //(先计算出余数)
        int number = source.size() / groupNum;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < groupNum; i++) {
            List<T> value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    /**
     * 将一组数据固定分组，每组n个元素
     *
     * @param source     要分组的数据源
     * @param elementNum 每组n个元素
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> fixedGrouping(List<T> source, int elementNum) {

        if (null == source || source.size() == 0 || elementNum <= 0)
            return null;
        List<List<T>> result = new ArrayList<List<T>>();

        int sourceSize = source.size();
        int size = (source.size() / elementNum) + 1;
        for (int i = 0; i < size; i++) {
            List<T> subset = new ArrayList<T>();
            for (int j = i * elementNum; j < (i + 1) * elementNum; j++) {
                if (j < sourceSize) {
                    subset.add(source.get(j));
                }
            }
            result.add(subset);
        }
        return result;
    }


    /**
     * 将一组数据固定分组，每组n个元素
     *
     * @param source 要分组的数据源
     * @param n      每组n个元素
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> fixedGrouping2(List<T> source, int n) {

        if (null == source || source.size() == 0 || n <= 0)
            return null;
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;
        int size = (source.size() / n);
        for (int i = 0; i < size; i++) {
            List<T> subset = null;
            subset = source.subList(i * n, (i + 1) * n);
            result.add(subset);
        }
        if (remainder > 0) {
            List<T> subset = null;
            subset = source.subList(size * n, size * n + remainder);
            result.add(subset);
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 13; i++) {
            list.add(i);
        }
        List<List<Integer>> lists = averageAssign(list, 3);
        System.out.println(lists);

        List<List<Integer>> lists1 = fixedGrouping(list, 4);
        System.out.println(lists1);

        List<List<Integer>> lists2 = fixedGrouping2(list, 4);
        System.out.println(lists2);
    }
}
