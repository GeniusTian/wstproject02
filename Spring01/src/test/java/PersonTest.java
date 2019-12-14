import com.atguigu.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wststart
 * @create 2019-06-29 12:46
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})//加载spring配置文件
public class PersonTest {
    @Autowired
    Person person;
    @Test
    public void test1() {
        System.out.println(person);
    }
}
