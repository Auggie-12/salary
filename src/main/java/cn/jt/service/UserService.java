package cn.jt.service;

import cn.jt.pojo.User;
import cn.jt.security.pub.ResultJSON;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public interface UserService extends UserDetailsService {

    // 获取验证码
    public ResultJSON getCaptcha(HttpServletResponse response, HttpServletRequest request);

    // 根据职工号获取用户信息
    public User findById(int id);

    // 获取所有用户信息
    public ResultJSON findAll();

    // 退出登录
    public ResultJSON logout();

    // 更新用户信息（通过 id）
    public ResultJSON updateUserByUserId(Map userMap);

    // 新增用户
    public ResultJSON insertUser(Map userMap);

    // 删除用户
    public ResultJSON deleteUserByUserId(int uid);

    // 根据部门代号获取职工列表
    public ResultJSON selectUsersByDepartId(String departId);

}
