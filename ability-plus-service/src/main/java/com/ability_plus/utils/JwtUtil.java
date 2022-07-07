package com.ability_plus.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;



public class JwtUtil {

    private static final String SIGNATURE = "ability-plus-!@#$*&^%$";

    public static String getToken(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, 60);

        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        String token = builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SIGNATURE));

        return token;
    }

    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }

}