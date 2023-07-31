package love.heer.demo.satoken.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    // 查询登录状态，访问： http://localhost:8080/user/isLogin
    @GetMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin(10001);
    }


    @GetMapping("login")
    public String login(String username, String passwd) {

        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if ("heer".equals(username) && "123456".equals(passwd)) {
            StpUtil.login(10001, "WEB");
            log.info("WEB: {}", StpUtil.getTokenValue());
            StpUtil.login(10001, "APP");
            log.info("APP: {}", StpUtil.getTokenValue());
            return StpUtil.getTokenValue();
        }
        return "登录失败！";
    }

}
