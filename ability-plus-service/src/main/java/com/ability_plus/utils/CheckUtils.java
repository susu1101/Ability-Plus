package com.ability_plus.utils;


import com.ability_plus.system.entity.CheckException;
import com.ability_plus.user.entity.UserPOJO;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @version V1.0
 * @package club.geemi.utils
 * @description:
 * @author: Geemi @Everlin
 * @date: 2018/3/10 17:01
 * Copyright©2018 All rights reserved.
 */
public class CheckUtils {

    /**
     * Check if the request user is company
     * @param user
     */
    public static final void assertNotCompany(UserPOJO user) {
        if (user.getIsCompany()){
            throw new CheckException("Company do not have permission to execute this operation");
        }
    }

    /**
     * Check if the request user is student
     * @param user
     */
    public static final void assertNotStudent(UserPOJO user) {
        if (!user.getIsCompany()){
            throw new CheckException("Student do not have permission to execute this operation");
        }
    }


    public static final boolean isNull(Object object) {
        return object == null;
    }

    public static final void assertNotNull(Object object, String message, Object... args) {
        if (isNull(object)) {
            throw new CheckException(String.format(message, args));
        }
    }

    public static final void assertTrue(boolean flag, String message, Object... args) {
        if (!flag) {
            throw new CheckException(String.format(message, args));
        }
    }

    public static final void assertNotBlank(String str, String message) {
        if (isBlank(str)) {
            throw new CheckException(message);
        }
    }

    public static final boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    public static final boolean isEmpty(Collection collection) {
        return collection == null || collection.size() <= 0;
    }

    public static final boolean isEmpty(Map map) {
        return map == null || map.size() <= 0;
    }

    public static final boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static final void assertNotEmpty(Collection collection, String message, Object... args) {
        if (isEmpty(collection)) {
            throw new CheckException(String.format(message, args));
        }
    }

    public static final void assertEmpty(Collection collection, String message) {
        if (!isEmpty(collection)) {
            throw new CheckException(message);
        }
    }
}
