package tech.naive.anasystem.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 2018034605 冯天阳
 * @version 1.0
 * @date 10/25/2021 11:11 AM
 */
public class JWTUtil {
    private static String SECRET = "c3bff416-993f-4760-9275-132b00256944";
    private static Integer EXPIRATION_TIME_MILLIS = 1000*60*10;

    public static String token(Long userId,String userName,String password){
        //设置私钥和加密算法
        Algorithm algorithm=Algorithm.HMAC256(JWTUtil.SECRET);
        //设置头部信息
        Map<String,Object> header=new HashMap<>();
        header.put("typ","JWT");
        header.put("alg","HS256");
        String token = JWT.create().withHeader(header).withClaim("requestUserId", userId)
                .withClaim("userName", userName)
                .withClaim("password", password).withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLIS)).sign(algorithm);
        return token;
    }

    public static Boolean verify(String token) throws JWTVerificationException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            if (jwt.getExpiresAt().before(new Date())) {
                System.out.println("token已过期");
                return false;
            }else{
                return true;
            }
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Long decodeUserId(String token){
        return JWT.decode(token).getClaim("requestUserId").asLong();
    }
}
