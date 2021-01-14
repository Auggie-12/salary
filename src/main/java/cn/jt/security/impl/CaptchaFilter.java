package cn.jt.security.impl;

import cn.jt.security.pub.ResultJSON;
import cn.jt.security.pub.SecurityHandlerUtil;
import cn.jt.utils.Log4jUtils;
import org.apache.catalina.security.SecurityUtil;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 验证码过滤器
@Component
public class CaptchaFilter extends GenericFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        System.out.println("CaptchaFilter:"+request.getParameter("captcha"));

        // 过滤器加入过滤器链之后，所有请求都会经过，如果请求不是登录请求，直接放行
        if ("POST".equals(req.getMethod()) && "/login".equals(req.getServletPath())) {

            // 提用户输入的验证码
            String captcha = req.getParameter("captcha");

            // 提取存入 session 的验证码
            String captchaSession = (String) req.getSession().getAttribute("captchaText");

            try {
                // 判断（不区分大小写）
                if (captcha == null || captcha == "" || !captcha.toLowerCase().equals(captchaSession.toLowerCase())) {

                    // 验证码不正确

                    //获取登录用户的id
                    MDC.put("userId",request.getParameter("username"));
                    System.out.println(request.getParameter("username"));

                    //获取登录用户的名字
                    MDC.put("userName",request.getParameter("password"));
                    System.out.println(request.getParameter("password"));


                    // 写入日志
                    Log4jUtils.getLogger().error("登录失败：验证码错误");

                    SecurityHandlerUtil.responseHandler(resp, new ResultJSON(4023, "验证码不正确"));
                    return;
                } else {

                    // 放行
                    chain.doFilter(req, resp);
                    return;
                }
            }catch (Exception e) {
                //获取登录用户的id
                MDC.put("userId",request.getParameter("username"));
                System.out.println(request.getParameter("username"));

                //获取登录用户的名字
                MDC.put("userName",request.getParameter("password"));
                System.out.println(request.getParameter("password"));


                // 写入日志
                Log4jUtils.getLogger().error("登录失败：系统异常");

                SecurityHandlerUtil.responseHandler(resp, new ResultJSON(4028, "异常"));
            }
        }
        else {

            // 放行
            chain.doFilter(req,resp);
            return;
        }

    }
}
