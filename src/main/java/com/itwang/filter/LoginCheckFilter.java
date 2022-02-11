package com.itwang.filter;/**
 * @author Whx
 * @company XXX
 * @create 2022-01-28 21:56
 */

import com.alibaba.fastjson.JSON;
import com.itwang.common.BaseContext;
import com.itwang.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Whx
 * @packageName: com.itwang.filter
 * @ClassName: LoginCheckFilter
 * @Description:
 * @data 2022/1/28 TIME:21:56
 */
/*检查用户是否已经完成登陆*/
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //    路径匹配器支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//获取本次请求的URI
        String requestURI = request.getRequestURI();
//定义不需要处理的请求路径
        log.info("拦截到请求：{}", requestURI);

        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
//        判断是否需要处理
        boolean check = check(urls, requestURI);

//如果不需要处理，则直接放行
        if (check) {
            log.info("本次请求{}不需要处理：", requestURI);

            filterChain.doFilter(request, response);
            return;
        }
//      4.1  判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已登录，用户id为：{}", request.getSession().getAttribute("empployee"));
            Long empId = (Long) request.getSession().getAttribute("employee");
//设置id
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request, response);
            return;
        }

        //      4.2 移动端 判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("user") != null) {
            log.info("用户已登录，用户id为：{}", request.getSession().getAttribute("user"));
            Long userId = (Long) request.getSession().getAttribute("user");
//设置id
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request, response);
            return;
        }

        log.info("用户未登录");
//        如果未登录则返回未登录结果,通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
//        log.info("拦截到请求: {}", request.getRequestURI());
//        filterChain.doFilter(request, response);

    }

    /*路径匹配，检查本次请求是否需要放行*/
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }

        }
        return false;
    }
}
