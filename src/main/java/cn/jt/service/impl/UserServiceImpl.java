package cn.jt.service.impl;

import cn.hutool.json.JSONUtil;
import cn.jt.mapper.UserMapper;
import cn.jt.pojo.Change;
import cn.jt.pojo.User;
import cn.jt.pojo.result.UserIdName;
import cn.jt.security.impl.Md5PasswordEncoderImpl;
import cn.jt.security.pub.ResultJSON;
import cn.jt.service.UserService;
import cn.jt.utils.CaptchaUtil;
import cn.jt.utils.Log4jUtils;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.log4j.MDC;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    // 重写 spring security 从数据库读取用户信息的方法
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        // 匹配正整数的正则表达式
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");

        // 判断用户输入的用户名是否为纯数字
        boolean isNumeric =  pattern.matcher(s).matches();

        // 如果用户输入的用户名不合法
        if (isNumeric == false) {
            throw new UsernameNotFoundException("用户名非法");
        }

        // 根据用户名查询用户对象
        User user = userMapper.findById(Integer.parseInt(s));

        // 用户不存在
        if (user == null || s == ""){
            throw new UsernameNotFoundException("用户不存在");
        }

        // 返回用户
        return userMapper.findById(Integer.parseInt(s));

    }

    // 获取验证码
    @Override
    public ResultJSON getCaptcha(HttpServletResponse response, HttpServletRequest request) {
        // 返回数据，包括：验证码
        HashMap<String,Object> result = new HashMap<>();

        // 生成验证码

        // 通知浏览器不要缓存
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "-1");

        // 通过工具类生成验证码，包含：图片和验证文本
        CaptchaUtil captchaUtil = CaptchaUtil.Instance();

        // 将验证码文本存入 session 中，用来验证
        String captchaText = captchaUtil.getString();
        request.getSession().setAttribute("captchaText",captchaText);

        // 将图片转化为 bs64 图片，传给前端
        try {
            // 转化，将字符串 src 传输至前端放入 src 即可显示图片
            ByteArrayOutputStream captchaImage = new ByteArrayOutputStream();
            ImageIO.write(captchaUtil.getImage(), "png", captchaImage);
            String base64bytes = Base64.encode(captchaImage.toByteArray());
            String captchaImageSrc = "data:image/png;base64," + base64bytes;

            // 传给前端页面
            result.put("captchaText",captchaText);
            result.put("captchaImageSrc",captchaImageSrc);

            return new ResultJSON(1100, "获取验证码成功", result);
        } catch (Exception e) {
            return new ResultJSON(0010,"获取验证码失败");
        }
    }

    // 根据职工号获取用户信息
    @Override
    public User findById(int id) {
        return userMapper.findById(id);
    }

    // 获取所有用户信息
    @Override
    public ResultJSON findAll() {
        Map result = new HashMap();
        List<User> users = userMapper.findAll();
        result.put("users",users);
        return new ResultJSON(2200,"请求所有用户信息成功",result);
    }

    // 退出登录
    @Override
    public ResultJSON logout() {
        try {

            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

            User user = (User) request.getSession().getAttribute("user");

            //获取登录用户的id
            MDC.put("userId",user.getId());

            //获取登录用户的名字
            MDC.put("userName",user.getRealname());

            // 写入日志
            Log4jUtils.getLogger().info("退出登录");

            return new ResultJSON(11,"退出登录成功", null);
        }
        catch (Exception e) {

            // 写入日志
            Log4jUtils.getLogger().error("退出登录：系统异常");

            return new ResultJSON(00,"系统异常", null);
        }
    }

    // 更新用户信息（通过 id）
    public ResultJSON updateUserByUserId(Map userMap) {
        Map result = new HashMap();
        User user = new User();
        user.setId((Integer) userMap.get("currentUserId")); // id
        user.setRealname((String) userMap.get("currentUserRealName"));  // 姓名
        user.setAge(Integer.parseInt(userMap.get("currentUserAge").toString()));   // 年龄
        user.setSex((String) userMap.get("currentUserSex"));    // 性别
        user.setDepartId((String) userMap.get("currentUserDepartId"));   // 部门代号
        user.setTell((String) userMap.get("currentUserTell"));  // 电话
        user.setEmail((String) userMap.get("currentUserEmail")); // 邮箱
        user.setWorkAge(Integer.parseInt(userMap.get("currentUserWorkAge").toString()));   // 工龄
        user.setNickname((String) userMap.get("currentUserNickname"));  // 昵称
        user.setIdent((String) userMap.get("currentUserIdent"));    // 身份证号
        String pwd = (String) userMap.get("currentUserPwd");
        String pwdMd5 = new Md5PasswordEncoderImpl().encode(pwd);
        user.setPwd(pwdMd5);    // 密码
        user.setIntroduction((String) userMap.get("currentUserIntroduction"));  // 个人简介

        int isUpdate = userMapper.updateUserByUserId(user);
        result.put("isUpdate",isUpdate);
        return new ResultJSON(11,"更新用户成功",result);
    }

    // 新增用户
    public ResultJSON insertUser(Map userMap) {
        Map result = new HashMap();
        User user = new User();
        user.setRealname((String) userMap.get("currentUserRealName"));  // 姓名
        user.setAge(Integer.parseInt(userMap.get("currentUserAge").toString()));   // 年龄
        user.setSex((String) userMap.get("currentUserSex"));    // 性别
        user.setDepartId((String) userMap.get("currentUserDepartId"));   // 部门代号
        user.setTell((String) userMap.get("currentUserTell"));  // 电话
        user.setEmail((String) userMap.get("currentUserEmail")); // 邮箱
        user.setWorkAge(Integer.parseInt(userMap.get("currentUserWorkAge").toString()));   // 工龄
        user.setNickname((String) userMap.get("currentUserNickname"));  // 昵称
        user.setIdent((String) userMap.get("currentUserIdent"));    // 身份证号
        String pwd = (String) userMap.get("currentUserPwd");
        String pwdMd5 = new Md5PasswordEncoderImpl().encode(pwd);
        user.setPwd(pwdMd5);    // 密码
        user.setIntroduction((String) userMap.get("currentUserIntroduction"));  // 个人简介

        int isInsert = userMapper.insertUser(user);

        // 新增的用户 id
        int uid = userMapper.selectMaxUserId();
        // 用户的角色
        int rid = Integer.parseInt(userMap.get("currentUserRoleId").toString());
        // 为用户分配角色
        userMapper.insertUserRole(uid,rid);
        result.put("isInsert",isInsert);
        return new ResultJSON(11,"增加用户成功",result);
    }

    // 删除用户
    public ResultJSON deleteUserByUserId(int uid) {
        Map result = new HashMap();
        int isDelete = userMapper.deleteUserByUserId(uid);
        result.put("isDelete",isDelete);
        return new ResultJSON(11,"删除用户信息成功",result);
    }

    // 根据部门代号获取职工列表
    public ResultJSON selectUsersByDepartId(String departId) {
        HashMap<String,Object> result = new HashMap<>();
        try{
            List<User> users = userMapper.selectUsersByDepartId(departId);
//            System.out.println(users);
            List<UserIdName> userIdNameList = new ArrayList();
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                // 检索项
                UserIdName userIdName = new UserIdName();
                userIdName.setValue(user.getId()+" "+user.getRealname());
                userIdName.setTag(user.getId()+" "+user.getRealname());
                userIdName.setId(user.getId());
                userIdNameList.add(userIdName);
            }
            result.put("users",JSONUtil.parse(users));
            result.put("userIdNameList",JSONUtil.parse(userIdNameList));
            return new ResultJSON(11,"根据部门代号获取职工列表 成功",result);
        }catch (Exception e){
            return new ResultJSON(00,"系统错误，根据部门代号获取职工列表 失败",null);
        }
    }

}
