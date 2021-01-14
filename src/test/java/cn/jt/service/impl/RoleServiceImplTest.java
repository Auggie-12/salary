package cn.jt.service.impl;

import cn.jt.mapper.RoleMapper;
import cn.jt.pojo.Role;
import cn.jt.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleServiceImplTest {

    @Autowired
    RoleService roleService;

    @Resource
    RoleMapper roleMapper;

    @Test
    void insertRole() {
        Role role = new Role();

        int rid = roleMapper.insertRole(role);
        System.out.println(rid
        );

    }
}