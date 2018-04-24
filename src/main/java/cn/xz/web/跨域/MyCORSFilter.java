package cn.xz.web.跨域;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class MyCORSFilter implements Filter {
    // 注册为 spring 的 bean，并且要在 web.xml 中配置；
    /*
     * <filter>
    <filter-name>CORS</filter-name>
      <filter-class>com.web.service.MyCORSFilter</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>CORS</filter-name>
    <url-pattern>/webService/*</url-pattern>
  </filter-mapping>
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTI ONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}