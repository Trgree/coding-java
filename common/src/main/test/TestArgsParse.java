import org.ace.coding.common.utils.CommandLineParser;

import java.util.Arrays;

/**
 * Created by LiangShujie
 * Date: 2019/8/27 16:01
 */
public class TestArgsParse {
    public static void main(String[] args) {
        CommandLineParser parser= new CommandLineParser();
        String[] otherArgs = parser.parse(args);
        System.out.println(Arrays.toString(otherArgs));
        System.out.println(parser.getAllPropsString());
        System.out.println(parser.getProp("a"));
    }
}
