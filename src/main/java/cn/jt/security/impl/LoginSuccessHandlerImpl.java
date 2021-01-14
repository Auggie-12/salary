package cn.jt.security.impl;

import cn.jt.pojo.User;
import cn.jt.security.pub.ResultJSON;
import cn.jt.security.pub.SecurityHandlerUtil;
import cn.jt.service.UserService;
import cn.jt.utils.Log4jUtils;
import cn.jt.utils.TokenUtil;
import cn.jt.utils.SpringUtil;
import cn.jt.utils.TokenUtil1;
import com.mysql.jdbc.log.LogUtils;
import org.apache.log4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static cn.jt.security.pub.ResultArgsUtil.USER_LOGIN_SUCCESS_CODE;
import static cn.jt.security.pub.ResultArgsUtil.USER_LOGIN_SUCCESS_MSG;
import static cn.jt.utils.TokenUtil.TOKEN_PREFIX;

/* 登录认证成功 */
public class LoginSuccessHandlerImpl implements AuthenticationSuccessHandler {

    // 手动注入 bean
    UserService userService = (UserService) SpringUtil.getBean("userServiceImpl");

    // 重写登录认证成功逻辑，不再返回页面，而是向浏览器（前端）返回 json 对象
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws UsernameNotFoundException,IOException, ServletException{

        // 获取用户名，用于加密
        String username = request.getParameter("username");

        // 用于存储 token 和 权限集合
        HashMap<String,Object> result = new HashMap<>();

        // 根据用户名生成 token
        String token = TokenUtil1.encodeToken(username);

        // 通过职工号查询用户信息
        User user = userService.findById(Integer.parseInt(username));

        // 将当前用户存入 session
        request.getSession().setAttribute("user",user);

        // 将 token 和权限集合保存到 result
        result.put("token",token);
        result.put("user",userService.findById(Integer.parseInt(username)));

        //获取登录用户的id
        MDC.put("userId",user.getId());
        System.out.println(MDC.get("userId"));

        //获取登录用户的名字
        MDC.put("userName",user.getRealname());
        System.out.println(MDC.get("username"));

        // 登录成功，写入日志
        Log4jUtils.getLogger().info("登录成功");

        // 响应前端
        SecurityHandlerUtil.responseHandler(response,new ResultJSON(USER_LOGIN_SUCCESS_CODE,USER_LOGIN_SUCCESS_MSG, result));

    }
}
