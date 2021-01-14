package cn.jt.service.impl;

import cn.jt.mapper.RoleMapper;
import cn.jt.pojo.Depart;
import cn.jt.pojo.Role;
import cn.jt.pojo.User;
import cn.jt.security.pub.ResultJSON;
import cn.jt.service.RoleService;
import cn.jt.utils.Log4jUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleMapper roleMapper;

    // 通过用户 id 查询角色
    public ResultJSON findByUserId(int userId) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            Role role = roleMapper.findByUserId(userId);
            result.put("role",role);
            return new ResultJSON(11,"查询角色成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，查询失败",null);
        }
    }

    // 通过角色 id 查询角色
    public ResultJSON findByRoleId(int rid) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            Role role = roleMapper.findByRoleId(rid);
            result.put("role",role);
            return new ResultJSON(11,"查询角色成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，查询失败",null);
        }
    }

    // 查询所有角色
    public ResultJSON findAll() {
        HashMap<String,Object> result = new HashMap<>();
        try{
            List<Role> roles = roleMapper.findAll();

            Map rolesMap = new HashMap();
            for (int i = 0; i < roles.size() ; i++) {
                rolesMap.put(""+roles.get(i).getId(),roles.get(i));
            }

            result.put("rolesMap",rolesMap);

            result.put("roles",roles);
            return new ResultJSON(11,"查询所有角色成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，查询失败",null);
        }
    }

    // 根据用户 id 修改用户角色
    public ResultJSON updateByUserId(int uid, int rid) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            int isUpdate = roleMapper.updateByUserId(uid, rid);
            result.put("isUpdate",isUpdate);
            return new ResultJSON(11,"调整角色成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，更新失败",null);
        }
    }

    // 根据角色 id 删除角色
    public ResultJSON deleteRoleByRoleId(int rid) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            roleMapper.deleteRoleByRoleId(rid);
            roleMapper.deleteRoleMenuByRoleId(rid);
            roleMapper.deleteRoleUserByRoleId(rid);
            result.put("isDelete",1);
            return new ResultJSON(11,"删除角色信息成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，删除失败",null);
        }
    }

    // 根据角色 id 修改信息
    public ResultJSON updateRoleByRoleId(int rid,String name,String nameZh,String desc){
        HashMap<String,Object> result = new HashMap<>();
        try{
            int isUpdate = roleMapper.updateRoleByRoleId(rid, name, nameZh, desc);
            result.put("isUpdate",isUpdate);
            return new ResultJSON(11,"更新角色信息成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，更新失败",null);
        }
    }

    // 添加角色
    public ResultJSON insertRole(String name,String nameZh, String desc) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            // 获取当前时间
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = sdf.format(date);
            // 插入
            Role role = new Role();
            role.setName(name); role.setNameZh(nameZh); role.setCreateTime(createTime);
            role.setDesc(desc); role.setLevel(30); role.setEnable(1);
            int isInsert = roleMapper.insertRole(role);
            System.out.println("isInsert"+isInsert);

            // 写入日志
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            User user = (User) request.getSession().getAttribute("user");
            //获取登录用户的id
            MDC.put("userId",user.getId());
            //获取登录用户的名字
            MDC.put("userName",user.getRealname());
            String message = "添加了角色，中文名："+nameZh+"，编号："+roleMapper.selectMaxRoleId();
            Log4jUtils.getLogger().info(message);

            result.put("isInsert",isInsert);
            return new ResultJSON(11,"添加角色成功",result);
        }catch (Exception e){
            Log4jUtils.getLogger().error("添加角色失败，系统异常");
            return new ResultJSON(00,"系统错误，更新失败",null);
        }
    }

    // 获取最大 id
    public ResultJSON selectMaxRoleId() {
        HashMap<String,Object> result = new HashMap<>();
        try{
            int maxRoleId = roleMapper.selectMaxRoleId();
            result.put("maxRoleId",maxRoleId);
            return new ResultJSON(11,"获取最大角色id成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，查询失败",null);
        }
    }
}














