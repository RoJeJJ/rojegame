package bean;

import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.gate.AppGate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppGate.class)
public class BeanTest {
    @Autowired
    MessageDispatcher dispatcher;
    @Test
    public void bean(){
    }
}
