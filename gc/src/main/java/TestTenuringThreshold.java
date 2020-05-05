/**
 * @author L
 * @date 2018/3/31
 */
public class TestTenuringThreshold {
    private static final int _1MB = 1024 * 1024;

    /**
     * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC -XX:MaxTenuringThreshold=1 -XX:PrintTenuringDistribution
     * -XX:MaxTenuringThreshold=1晋升老年代的年龄阈值（默认15），即挺过1次Minor GC，年龄增加1，当年龄达到15，就会晋升级老年代
     * 但这即使阈值为15，urvivor空间也被清了   ？？？
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        byte[] all1,all2,all3;
        all1 = new byte[_1MB / 4];// 256k,Survivor空间可以容纳
        all2 = new byte[4 * _1MB];
        all3 = new byte[4 * _1MB];//gc all1进入Survivor，all2进入老年代，all3在Eden
        all3 = null;
        all3 = new byte[4 * _1MB];// gc all3回收，新all3进入Eden,all1进入老年代（all2也在）
    }
}
