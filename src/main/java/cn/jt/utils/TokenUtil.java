package cn.jt.utils;

import io.jsonwebtoken.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * token 生成工具
 * */
public class TokenUtil {

    private static final int EXPIRATION_TIME = 60*60*24;                // 过期时间

    private static final String SECRET = "023bdc63c3c5a4587*9ee6581508b9d03ad39a74fc0c9a9cce604743367c9646b";   // 自定义密钥

    public static final String TOKEN_PREFIX = "Bearer ";                // 前缀

    public static final String AUTHORIZATION = "Authorization";         // 表头授权

    /* 加密，返回一个 token */
    public static String generateToken(String username) {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, EXPIRATION_TIME);
        Date time = calendar.getTime();
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username);
        String token = Jwts.builder()
                .setClaims(map)        //签发时间
                .setIssuedAt(now)
                .setExpiration(time)    //过期时间
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        // token 前面一般都会加 Bearer
        return TOKEN_PREFIX + token;

    }

    /* 解密，通过加密设置的属性 */
    public static String validateToken(String token) {
        try {
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            String username = body.get("username").toString();
            return username;
        }
        catch(Exception e){
            throw e;
        }
    }
}

