/**
 * 大内存直接进入老年区
 *
 * @author L
 * @date 2018/3/31
 */
public class TestPretenureSizeThreshold {

    private static final int _1MB = 1024 * 1024;

    /**
     * vm参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC -XX:PretenureSizeThreshold=3145728
     * -XX:PretenureSizeThreshold=3145728 大于这个的对象直接进入老年区
     * 该参数只对Serial和ParNew收集器有效，如遇到必须使用些参数的场合，可考虑ParNew加CMS的收集器组合
     * 目的：避免在Eden及两个Survivor区之间发生大量的内存复制（新生代采用复制算法收集内存）
     * @param args
     */
    public static void main(String[] args) {
        byte[] allcation = new byte[4 * _1MB];// 4M大于PretenureSizeThreshold设定的3M 直接进入老年区

    }
}
