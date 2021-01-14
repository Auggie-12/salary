package cn.jt.security.impl;

import cn.jt.security.pub.ResultJSON;
import cn.jt.security.pub.SecurityHandlerUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.jt.security.pub.ResultArgsUtil.USER_NOT_LOGIN_FAILURE_CODE;
import static cn.jt.security.pub.ResultArgsUtil.USER_NOT_LOGIN_FAILURE_MSG;

/* 没有登录处理逻辑 */
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        /* 向前端返回没有登录的 json 数据 */
        SecurityHandlerUtil.responseHandler(httpServletResponse,new ResultJSON(USER_NOT_LOGIN_FAILURE_CODE,USER_NOT_LOGIN_FAILURE_MSG));
    }
}
