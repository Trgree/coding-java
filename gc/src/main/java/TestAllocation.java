/**
 * 内存分配，对象优先在新生代Eden分配
 * 当Eden区没有足够空间进行分配时，虚拟机将发起一次Minor GC
 * @author L
 * @date 2018/3/31
 */
public class TestAllocation {

    private static final int _1MB = 1024 * 1024;
    private static final int _600K = 1024 * 600;

    /**
     * vm参数：-verbose:gc -Xms20M -Xmx20M    -Xmn10M       -XX:+PrintGCDetails    -XX:SurvivorRatio=8      -XX:+UseSerialGC
     *        gc时打印日志  最大最小堆内存为20m，其中新生代10m  程序结束时打印堆详细信息  新生代Eden和Survivor8:1:1 使用Serial收集器，默认为UseParallelGC
     * 新生代共10M,8m eden 1m Survivor 1m Survivor
     *
     * 数据回收流程
     *
     *
     * |eden|survivor|survivor|tenured|
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        byte[] a1,a2,a3,a4,a5,a6;
        a1 = new byte[_600K];// a1 放入eden，|a1||||  到这eden用了2932K,（初始就有2m被用，其它数据 ）
        a2 = new byte[_1MB * 5 ];// a2 放入eden，|a1 a2|||| eden用了8052k
        a3 = new byte[_1MB * 3];// 【gc】 eden空间只有8m,放不下a3,a1移到survivor, a2移到tenured，a3放入eddn |a3|a1||a2|
        a4 = new byte[7 * _1MB]; // 【gc】 |a4|||a1 a2 a3 | eden放不下a4，eden的a3要移到survivor,但survivor也放不下，
                                        // a1要移到老年区，survivor还是容不下a3，a3放到老年区，a4放在eden


    }

}
