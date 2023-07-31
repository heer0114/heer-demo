package love.heer.demo.jcasbin.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.casbin.adapter.JDBCAdapter;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

/**
 * absbin abac filter
 *
 * @author heer on 2023/07/14
 */
@Component
@WebFilter(urlPatterns = {"/**"})
@Slf4j
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class AbacFilter implements Filter {

    private String username;

    private String password;

    private String url;

    private String driverClassName;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        log.info("requestURI: {}", requestURI);

        URL modelUrl = this.getClass().getResource("/model/casbin01-model.conf");
        URL policyUrl = this.getClass().getResource("/policy/casbin01-policy.csv");

        JDBCAdapter jdbcAdapter;

        try {
            jdbcAdapter = new JDBCAdapter(driverClassName, url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (modelUrl != null && policyUrl != null) {
            Enforcer enforcer = new Enforcer(modelUrl.getPath(), jdbcAdapter);
            enforcer.loadPolicy();

            boolean bool = enforcer.enforce(new Jeer("heer", 20), requestURI, "read");
            if (bool) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            throw new AssertionError("权限不足！！");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }



}
