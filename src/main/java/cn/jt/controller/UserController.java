package cn.jt.controller;

import cn.jt.pojo.User;
import cn.jt.security.pub.ResultJSON;
import cn.jt.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/user/")
public class UserController {

    @Resource
    UserService userService;

    // 获取验证码
    @RequestMapping("getCaptcha")
    public ResultJSON getCaptcha(HttpServletResponse response, HttpServletRequest request) {
        return userService.getCaptcha(response, request);
    }

    // 根据职工号获取用户信息
    @RequestMapping("findById")
    public User findById(@RequestParam("id") int id) {
        return userService.findById(id);
    }

    // 获取所有用户信息
    @RequestMapping("findAll")
    public ResultJSON findAll() {
        return userService.findAll();
    }

    // 退出登录
    @RequestMapping("logout")
    public ResultJSON logout() {
        return userService.logout();
    }

    // 更新用户信息（通过 id）
    @PostMapping("updateUserByUserId")
    public ResultJSON updateUserByUserId(@RequestBody Map userMap) {
        return userService.updateUserByUserId(userMap);
    }

    // 新增用户
    @PostMapping("insertUser")
    public ResultJSON insertUser(@RequestBody Map userMap) {
        return userService.insertUser(userMap);
    }

    // 删除用户
    @RequestMapping("deleteUserByUserId")
    public ResultJSON deleteUserByUserId(@RequestParam("uid") int uid) {
        return userService.deleteUserByUserId(uid);
    }

    // 根据部门代号获取职工列表
    @RequestMapping("selectUsersByDepartId")
    public ResultJSON selectUsersByDepartId(@RequestParam("departId") String departId) {
        return userService.selectUsersByDepartId(departId);
    }


}
