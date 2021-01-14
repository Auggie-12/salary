package cn.jt.controller;

import cn.jt.security.pub.ResultJSON;
import cn.jt.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/role/")
public class RoleController {

    @Resource
    RoleService roleService;

    // 通过用户 id 查询角色
    @RequestMapping("findByUserId")
    public ResultJSON findByUserId(@RequestParam("userId") int userId) {
        return roleService.findByUserId(userId);
    }

    // 查询所有角色
    @RequestMapping("findAll")
    public ResultJSON findAll() {
        return roleService.findAll();
    }

    // 根据用户 id 修改用户角色
    @RequestMapping("updateByUserId")
    public ResultJSON updeteByUserId(@RequestParam("uid") int uid, @RequestParam("rid") int rid){
        return roleService.updateByUserId(uid,rid);
    }

    // 根据角色 id 删除所有相关信息
    @RequestMapping("deleteRoleByRoleId")
    public ResultJSON deleteRoleByRoleId(@RequestParam("rid") int rid) {
        return roleService.deleteRoleByRoleId(rid);
    }

    // 根据角色 id 修改信息
    @PostMapping("updateRoleByRoleId")
    @ResponseBody
    public ResultJSON updateRoleByRoleId(int rid, String name, String nameZh, String desc) {
        return roleService.updateRoleByRoleId(rid, name, nameZh, desc);
    }

    // 添加角色
    @PostMapping("insertRole")
    @ResponseBody
    ResultJSON insertRole(String name,String nameZh, String desc) {
        return roleService.insertRole(name, nameZh, desc);
    }

    // 获取最大 id
    @RequestMapping("selectMaxRoleId")
    ResultJSON selectMaxRoleId() {
        return roleService.selectMaxRoleId();
    }

}
