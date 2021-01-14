package cn.jt.security.impl;

import cn.jt.security.pub.ResultJSON;
import cn.jt.security.pub.SecurityHandlerUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.jt.security.pub.ResultArgsUtil.LOGOUT_SUCCESS_CODE;
import static cn.jt.security.pub.ResultArgsUtil.LOGOUT_SUCCESS_MSG;

/* 实现注销接口 - 注销只有成功接口 */
public class LogoutHandlerImpl implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        /* 返回注销成功的 json 数据给前端 */
        SecurityHandlerUtil.responseHandler(httpServletResponse,new ResultJSON(LOGOUT_SUCCESS_CODE,LOGOUT_SUCCESS_MSG));
    }

}
