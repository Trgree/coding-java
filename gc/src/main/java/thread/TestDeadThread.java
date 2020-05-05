package thread;

/**
 * @author L
 * @date 2018/4/1
 */
public class TestDeadThread {
    static class SynAndRunnable implements Runnable {

        int a,b;

        public SynAndRunnable(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            synchronized (Integer.valueOf(a)) {// 同一a,对象为同一个
                synchronized (Integer.valueOf(b)) {
                    System.out.println(a + b);

                }

            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new SynAndRunnable(1,2)).start();
            new Thread(new SynAndRunnable(2,1)).start();
        }
    }
}
