package cn.jt.security.impl;

import cn.jt.security.pub.ResultJSON;
import cn.jt.security.pub.SecurityHandlerUtil;
import cn.jt.utils.Log4jUtils;
import org.apache.log4j.MDC;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.jt.security.pub.ResultArgsUtil.USER_NOT_EXIST_FAILURE_CODE;
import static cn.jt.security.pub.ResultArgsUtil.USER_NOT_EXIST_FAILURE_MSG;


/* 登录认证失败 */
public class LoginFailureHandlerImpl implements AuthenticationFailureHandler {

    /* 重写登录认证失败逻辑，不再返回页面，而是向浏览器（前端）返回 json 对象 */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        //获取登录用户的id
        MDC.put("userId",httpServletRequest.getParameter("username"));
        System.out.println(httpServletRequest.getParameter("username"));

        //获取登录用户的名字
        MDC.put("userName",httpServletRequest.getParameter("password"));
        System.out.println(httpServletRequest.getParameter("password"));

        // 写入日志
        Log4jUtils.getLogger().error("登录失败：用户名或密码错误");

        /* 返回登录成功的 json 数据给前端 */
        SecurityHandlerUtil.responseHandler(httpServletResponse,new ResultJSON(USER_NOT_EXIST_FAILURE_CODE,USER_NOT_EXIST_FAILURE_MSG));
    }
}
