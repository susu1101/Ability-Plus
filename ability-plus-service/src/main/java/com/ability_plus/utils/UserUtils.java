package com.ability_plus.utils;

import com.ability_plus.user.entity.User;
import com.ability_plus.user.entity.UserPOJO;
import com.ability_plus.user.service.IUserService;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sjx
 */
public class UserUtils {
    /**
     * 获得当前用户
     * @return
     */
    public static UserPOJO getCurrentUser(HttpServletRequest http){
        String token = http.getHeader("token");
        UserPOJO user = new UserPOJO();
        DecodedJWT verify = JwtUtil.verify(token);


        user.setId(Integer.parseInt(verify.getClaim("id").asString()));
        user.setIsCompany(Boolean.parseBoolean(verify.getClaim("isCompany").asString()));
        user.setAccount(verify.getClaim("account").asString());
        return user;
    }




}
