package cn.jt.security.impl;

import cn.jt.security.WebSecurityConfig;
import cn.jt.security.pub.ResultJSON;
import cn.jt.security.pub.SecurityHandlerUtil;
import cn.jt.utils.TokenUtil;
import cn.jt.utils.TokenUtil1;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

// 登录验证过滤器
@Component
public class TokenFilter extends GenericFilter {



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String url = req.getRequestURI();
        String header = req.getHeader("Authorization"); // "Authorization"
        String pass = req.getHeader("Pass");

        // 已经验证过 token 的请求，放行，方便验证权限
        if (pass != null) {
            chain.doFilter(req,resp);
            return;
        }

        System.out.println(header);
        if ("POST".equals(req.getMethod()) && "/login".equals(req.getServletPath())) {
            System.out.println("TokenFilter:"+"如果是登录请求，直接放行");
            chain.doFilter(req, resp);
            return;
        }

        // 不在拦截范围
        if (Arrays.asList(WebSecurityConfig.AUTH_WHITELIST).contains(url)) {
            chain.doFilter(req, resp);
            return;
        }

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDY1Nzk1MDgsInVzZXJJZCI6IjEiLCJpYXQiOjE2MDY1NzU5MDh9.qbPjibk51chZqnmnIkgQYK8gGkbew96Y2qnHmEi5nIE";

        try {
            DecodedJWT decodedJWT = TokenUtil1.decodeToken(header);
            String userId = decodedJWT.getClaims().get("userId").asString();
            SecurityHandlerUtil.responseHandler(resp,new ResultJSON(2000,"验证成功"));
        }
        catch (Exception e) {
            SecurityHandlerUtil.responseHandler(resp,new ResultJSON(4000,"验证失败"));
        }

    }

}
