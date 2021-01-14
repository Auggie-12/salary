package cn.jt.salary01;

import cn.jt.mapper.UserMapper;
import cn.jt.pojo.User;
import cn.jt.service.UserService;
import cn.jt.utils.TokenUtil;
import cn.jt.utils.TokenUtil1;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

@SpringBootTest
public class MyTest {

    @Autowired
    StringEncryptor encryptor;

    @Autowired
    UserService userService;

    @Resource
    UserMapper userMapper;

    /* 更新用户信息（通过 id） */
    @Test
    public void updateUserByUserId() {
        User user = new User();
        user.setId(5);
        user.setDepartId("B");
        user.setIdent("123");
        user.setPwd("123");
        user.setRealname("123");
        user.setNickname("123");
        user.setTell("123");
        user.setAge(1);
        user.setSex("f");
        user.setIntroduction("无");
        user.setEmail("123");
        user.setWorkAge(12);
        int a = userMapper.updateUserByUserId(user);
        System.out.println(a);
    }

    /* MD5 解密 */
    @Test
    public void myTest() {
        System.out.println(DigestUtils.md5DigestAsHex("123456".toString().getBytes()));

    }

    /* 测试数据库连接密码加密 */
    @Test
    public void test(){
        String name = encryptor.encrypt("root");
        String password = encryptor.encrypt("123456");
        System.out.println(name);
        System.out.println(password);
    }

    /* 测试查询用户信息的接口 */
    @Test
    public void test1(){
        System.out.println(userService.findById(1));
    }

    /* 测试 token 格式是否正确 */
    @Test
    public void token() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDY1Nzk1MDgsInVzZXJJZCI6IjEiLCJpYXQiOjE2MDY1NzU5MDh9.qbPjibk51chZqnmnIkgQYK8gGkbew96Y2qnHmEi5nIE";
        DecodedJWT decodedJWT = TokenUtil1.decodeToken(token);
        System.out.println(decodedJWT.getClaims().get("userId").asString());

    }
}
