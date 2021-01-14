package cn.jt.controller;

import cn.jt.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserControllerTest {
    @Resource
    UserService userService;
    @Test
    void selectUsersByDepartId() {
        System.out.println(userService.selectUsersByDepartId("A"));
    }
}