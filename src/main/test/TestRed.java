import com.lxh.red.config.RootConfig;
import com.lxh.red.mapper.RedPacketMapper;
import com.lxh.red.mapper.UserRedPacketMapper;
import com.lxh.red.pojo.UserRedPacket;
import com.lxh.red.service.UserRedPacketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/*
 * @PackageName: PACKAGE_NAME
 * @ClassName: TestRed
 * @Description:
 * @author: 辉
 * @date: 2019/12/29 15:03
 * */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {RootConfig.class})
public class TestRed {

    @Autowired
    UserRedPacketMapper userRedPacketMapper;
    @Autowired
    RedPacketMapper redPacketMapper;

    @Test
    public void test() {
        System.out.println(redPacketMapper.decreaseRedPacketForVersion((long)1, (long)0));
    }

}
