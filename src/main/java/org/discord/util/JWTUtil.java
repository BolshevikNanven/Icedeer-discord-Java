package org.discord.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JWTUtil {

    private static final long EXPIRE_TIME = 14 * 24 * 60 * 60 * 1000;

    private static final String secret = "123456";

    /**
     * 校验token是否正确
     *
     * @param token 密钥
     * @return 是否正确
     */
    public static DecodedJWT verify(String token) throws Exception {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }


    /**
     * 生成签名
     *
     * @param id 用户名
     * @return 加密的token
     */
    public static String sign(String id) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    .withClaim("id", id)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String decodeId(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Claim claim = decodedJWT.getClaim("id");

        return claim.asString();
    }
}
