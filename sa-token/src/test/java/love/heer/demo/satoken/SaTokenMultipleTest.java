package love.heer.demo.satoken;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import love.heer.demo.satoken.config.StpConsumerUtil;
import love.heer.demo.satoken.config.StpDoctorUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 多账号认证功能测试
 */
@SpringBootTest
@Slf4j
public class SaTokenMultipleTest {


    /**
     * 基础测试
     * <p>
     *     一个后台人员认证 StpUtil
     *     一个医生人员认证 StpDoctorUtil
     * </p>
     */
    @Test
    void baseTest() {
        // ID 相同 1008611
        Long userId = 1008611L;

        // 医途运营人员登录[WEB] - StpUtil type=login
        StpUtil.login(userId, "WEB");

        // SaaS 端医务人员登录[WEB] - StpDoctorUtil type=doctor
        StpDoctorUtil.login(userId, "WEB");

        // SaaS 端医务人员登录[APP] - StpDoctorUtil type=doctor
        StpDoctorUtil.login(userId, "APP");

        // 消费者账号登录[wxMiniApp] - StpConsumerUtil type=consumer
        StpConsumerUtil.login(userId, "WxMiniApp");
    }

}
