import com.google.gson.Gson;
import com.roje.game.cluster.AppCluster;
import com.roje.game.cluster.netty.TestProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppCluster.class)
public class TestProp {
    @Autowired
    private TestProperties testProp;
    @Test
    public void test(){
        log.info(new Gson().toJson(testProp));
    }
}
