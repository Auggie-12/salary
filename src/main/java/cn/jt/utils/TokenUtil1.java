package cn.jt.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class  TokenUtil1 {

        // 根据用户 id 生成
        public static String encodeToken(String userId){
            int expireTime = 3600; // 过期时间
            String secrect = "123"; // 密钥
            Date createDate = new Date();
            Date expireDate = DateUtils.addSeconds(createDate, expireTime);
            Map<String, Object> header = new HashMap<>();
            header.put("alg", "HS256");
            header.put("typ", "JWT");
            String token = JWT.create().withHeader(header)
                    .withClaim("userId", userId)
                    .withIssuedAt(createDate)
                    .withExpiresAt(expireDate)
                    .sign(Algorithm.HMAC256(secrect));
            return token;

        }

        public static DecodedJWT decodeToken(String token) {
            DecodedJWT decode = JWT.decode(token);
            return  decode;
        }

}