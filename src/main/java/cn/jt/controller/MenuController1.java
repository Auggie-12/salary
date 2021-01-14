package cn.jt.controller;

import cn.jt.pojo.MenuRole;
import cn.jt.security.pub.ResultJSON;
import cn.jt.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/menu/")
public class MenuController1 {

    @Resource
    MenuService menuService;

    // 查询所有权限
    @RequestMapping("findAllMenus")
    public ResultJSON findAllMenus() {
        return menuService.findAllMenus();
    }

    // 查询所有一级权限
    @RequestMapping("findAllParentMenus")
    public ResultJSON findAllParentMenus() {
        return menuService.findAllParentMenus();
    }

    // 查询指定角色的权限
    @RequestMapping("findMenusByRoleId")
    public ResultJSON findMenusByRoleId(@RequestParam("rid") int rid) {
        return menuService.findMenusByRoleId(rid);
    }

    // 查询指定用户的权限
    @RequestMapping("findMenusByUserId")
    public ResultJSON findMenusByUserId(@RequestParam("uid") int uid) {
        return menuService.findMenusByRoleId(uid);
    }

    // 为指定角色分配权限
    @PostMapping("saveMenus")
    public ResultJSON saveMenus(@RequestBody MenuRole[] menuRole) { //@RequestBody MenuRole[] menuRole
        return menuService.saveMenus(menuRole);
    }

    // 删除角色的所有权限
    @RequestMapping("deleteAllMenusByRoleId")
    public ResultJSON deleteAllMenusByRoleId(@RequestParam("rid") int rid) {
        return menuService.deleteAllMenusByRoleId(rid);
    }

    // 查询角色权限数量
    @RequestMapping("countMenusByRoleId")
    public ResultJSON countMenusByRoleId(@RequestParam("rid") int rid){
        return menuService.countMenusByRoleId(rid);
    }

    // 查询所有角色权限数量
    @RequestMapping("countMenus")
    public ResultJSON countMenus() {
        return menuService.countMenus();
    }
}
