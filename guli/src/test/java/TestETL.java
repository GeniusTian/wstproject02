import com.atguigu.ETLUtil;
import org.junit.Test;

/**
 * @author wststart
 * @create 2019-08-06 18:46
 */
public class TestETL {
    @Test
    public void test1(){
        String s = ETLUtil.StringETL("SDNkMu8ZT68\tw00dy911\t630\tPeople & Blogs\t186\t10181\t3.49\t494\t257\trjnbgpPJUks");
        System.out.println(s);
    }
}
