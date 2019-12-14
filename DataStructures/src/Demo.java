import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author wststart
 * @create 2019-06-18 13:56
 */
public class Demo {
    @Test
    public void test1() {

    }
    public static void main(String[] args) {
        String str = fileExtNameFromUrl("http://localhost:8080/testweb/index.html");
        System.out.println(str);
    }

    public static String fileExtNameFromUrl(String url){
        return url.substring(url.lastIndexOf("."));
    }



}
