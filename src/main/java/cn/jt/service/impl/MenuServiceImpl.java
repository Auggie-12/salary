package cn.jt.service.impl;

import cn.jt.mapper.MenuMapper;
import cn.jt.mapper.RoleMapper;
import cn.jt.pojo.Menu;
import cn.jt.pojo.MenuRole;
import cn.jt.pojo.Role;
import cn.jt.pojo.User;
import cn.jt.security.pub.ResultJSON;
import cn.jt.service.MenuService;
import cn.jt.utils.Log4jUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    MenuMapper menuMapper;

    @Resource
    RoleMapper roleMapper;

    // 查询所有权限
    public ResultJSON findAllMenus() {
        HashMap<String,Object> result = new HashMap<>();
        try{
            List<Menu> menus = menuMapper.findAllMenus();
            result.put("menus",menus);
            return new ResultJSON(11,"查询所有权限成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，查询失败",null);
        }
    }

    // 根据权限 id 查询指定权限
    public ResultJSON findMenuByMenuId(int mid){
        HashMap<String,Object> result = new HashMap<>();
        try{
            Menu menu = menuMapper.findMenuByMenuId(mid);
            result.put("menu",menu);
            return new ResultJSON(11,"查询指定权限成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，查询失败",null);
        }
    }

    // 查询所有一级权限
    public ResultJSON findAllParentMenus() {
        HashMap<String,Object> result = new HashMap<>();
        try{
            List<Menu> parentMenus = menuMapper.findAllParentMenus();
            result.put("parentMenus",parentMenus);
            return new ResultJSON(11,"查询所有一级权限成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，查询失败",null);
        }
    }

    // 查询指定角色的权限
    public ResultJSON findMenusByRoleId(int rid) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            List<Menu> roleMenus = menuMapper.findMenusByRoleId(rid);
            result.put("roleMenus",roleMenus);
            return new ResultJSON(11,"查询当前角色的所有权限成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，查询失败",null);
        }
    }

    // 删除角色的所有权限
    public ResultJSON deleteAllMenusByRoleId(int rid) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            int isDelete = menuMapper.deleteAllMenusByRoleId(rid);
            result.put("isDelete",isDelete);
            return new ResultJSON(11,"删除当前角色的所有权限成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，查询失败",null);
        }
    }

    // 查询指定用户的权限
    public ResultJSON findMenusByUserId(int uid) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            List<Menu> userMenus = menuMapper.findMenusByUserId(uid);
            result.put("userMenus",userMenus);
            return new ResultJSON(11,"查询当前用户的所有权限成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，查询失败",null);
        }
    }

    // 为指定角色分配权限
    public ResultJSON saveMenus( MenuRole[] menuRole) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            // 先删除该用户的所有权限
            System.out.println(menuRole[0].getRoleId());
            menuMapper.deleteAllMenusByRoleId(menuRole[0].getRoleId());
            int isInsert = 0;

            // 获取当前分配角色
            Role role = roleMapper.findByRoleId(menuRole[0].getRoleId());

            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

            User user = (User) request.getSession().getAttribute("user");

            //获取登录用户的id
            MDC.put("userId",user.getId());

            //获取登录用户的名字
            MDC.put("userName",user.getRealname());

            // 日志
            String message = "更新角色（"+role.getNameZh()+"）的权限为：\n";

            // 遍历权限集合，一条一条插入
            for (int i = 0; i < menuRole.length; i++) {
                isInsert = menuMapper.saveMenu(menuRole[i].getRoleId(),menuRole[i].getMenuId());
                // 获取当前权限 id
                int mid = menuRole[i].getMenuId();
                // 通过 id 获取当前权限
                Menu menu = menuMapper.findMenuByMenuId(mid);
                message = message + menu.getName() + "\n";

                if (isInsert == 0) break;
            }
            Log4jUtils.getLogger().info(message);
            result.put("isInsert",isInsert);
            System.out.println(message);
            return new ResultJSON(11,"为用户分配权限成功",result);
        }catch (Exception e){
            Log4jUtils.getLogger().error("分配权限失败，系统异常");
            return new ResultJSON(00,"系统错误，分配权限失败",null);
        }
    }

    // 查询指定角色权限数量
    public ResultJSON countMenusByRoleId(int rid) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            int count = menuMapper.countMenusByRoleId(rid);
            result.put("count",count);
            System.out.println(count);
            return new ResultJSON(11,"查询角色权限数量成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，查询失败",null);
        }
    }

    // 查询所有角色权限数量
    public ResultJSON countMenus() {
        HashMap<String,Object> result = new HashMap<>();
        try{
            List<Map> countMap = menuMapper.countMenus();
            Map roleMenuNumber = new HashMap();
            System.out.println(countMap);
            for (int i = 0; i < countMap.size(); i++) {
                Number cnt = (Number) countMap.get(i).values().toArray()[0];
                Number rid = (Number) countMap.get(i).values().toArray()[1];
                roleMenuNumber.put(rid, cnt);
            }
            result.put("roleMenuNumber",roleMenuNumber);
            return new ResultJSON(11,"查询所有角色权限数量成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，查询失败",null);
        }
    }

}
