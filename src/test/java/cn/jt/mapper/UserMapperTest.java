package cn.jt.mapper;

import cn.jt.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Resource
    UserMapper userMapper;

    @Test
    void insertUser() {
        User user = new User();
        user.setDepartId("F");
        int res = userMapper.insertUser(user);
        System.out.println(res);
    }

    @Test
    void insertUserRole() {
        int res = userMapper.insertUserRole(8,3);
        System.out.println(res);
    }

    @Test
    void deleteUserByUserId() {
        int res = userMapper.deleteUserByUserId(12);
        System.out.println(res);
    }
}