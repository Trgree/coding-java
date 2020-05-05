import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 内存占位符对象，一个OOMObject大约占 64k
 *
 * VM:-Xms100m -Xmx100m -XX:+UseSerialGC -verbose:gc
 *
 * @author L
 * @date 2018/4/1
 */
public class TestGC {
    static class OOMOjbect {
        public byte[] placeholder = new byte[64 * 1024];
    }

    public static void fillHeap(int num) throws InterruptedException {
        List<OOMOjbect> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Thread.sleep(50);
            list.add(new OOMOjbect());
        }
         System.gc();// 年经代被清空，老年代还在，因为list对象还存活，若放在方法返回后，即老年代也会清

    }

    public static void main(String[] args) throws InterruptedException {
        fillHeap(1000);
        fillHeap(1000);// 若前面没有 System.gc();到这空间不够，也会触发Full GC

        TimeUnit.SECONDS.sleep(10);
    }

}

