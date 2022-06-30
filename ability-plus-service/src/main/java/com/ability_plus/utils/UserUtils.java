package com.ability_plus.utils;

import com.ability_plus.user.entity.User;

public class UserUtils {

    /**
     * 获得当前用户
     * @return
     */
    public static User getCurrentUser(){
        return new User();
    }

    /**
     * 记录用户登录信息
     *
     * @param user
     */
    public static void record(User user) {
//        redisProvider.set(user);
    }

    /**
     * 删除用户登录信息
     *
     * @param user
     */
    public static void del(User user) {
//        redisProvider.del(user);
    }

}
