package com.ability_plus.system;

import com.ability_plus.utils.JwtUtil;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        String token = request.getHeader("token");
        try{
            JwtUtil.verify(token);
            return true;
        }catch (TokenExpiredException e){
//            e.printStackTrace();
            map.put("msg","token expired");
        }catch ( Exception e){
//            e.printStackTrace();
            map.put("msg","token fail");
        }
        String s = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(s);
        return false;
    }
}
