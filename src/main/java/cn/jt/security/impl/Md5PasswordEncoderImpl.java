package cn.jt.security.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

/* 密码加密 */
public class Md5PasswordEncoderImpl implements PasswordEncoder {

    /* 加密 */
    @Override
    public String encode(CharSequence charSequence) {
        /* 所有用户密码为 123456 */
        return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
    }

    /* 匹配 */
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        System.out.println("charSequence:"+charSequence);
        System.out.println("pwd:"+s);
        return s.equals(DigestUtils.md5DigestAsHex(charSequence.toString().getBytes())); // return s.equals(charSequence);
    }
}
