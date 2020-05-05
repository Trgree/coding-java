public class MyTest {
    public static void main(String[] args) {
        String key="chunk1: \"Google Bye GoodBye Hadopp lintcode\"";
        if(key!=null && key.length() > 0) {
            String data = key.substring(key.indexOf("\"") + 1, key.lastIndexOf("\""));
            System.out.println(data);
        }
    }
}
