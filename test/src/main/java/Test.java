import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author L
 * @date 2018/3/24
 */
public class Test {

    private int a=0;

    public void test(int i) throws InterruptedException {
        int j=0;
        j = i + j;
        Object obj = new Test();
        test2();
    }

    public void test2() throws InterruptedException {
        while(true){
            new Test();
            System.out.println("haha");
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public  native void test3();

    public static void main(String[] args) throws InterruptedException {
       new Test().test(1);


    }
}
