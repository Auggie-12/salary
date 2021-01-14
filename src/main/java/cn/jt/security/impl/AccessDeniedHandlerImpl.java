package cn.jt.security.impl;

import cn.jt.security.pub.ResultJSON;
import cn.jt.security.pub.SecurityHandlerUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.jt.security.pub.ResultArgsUtil.AUTHORIZE_FAILURE_CODE;
import static cn.jt.security.pub.ResultArgsUtil.AUTHORIZE_FAILURE_MSG;

/* 没有权限处理逻辑 */
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        /* 把没有权限的 json 数据传给前端 */
        SecurityHandlerUtil.responseHandler(httpServletResponse,new ResultJSON(AUTHORIZE_FAILURE_CODE,AUTHORIZE_FAILURE_MSG));
    }
}
