package com.ability_plus.user.service.impl;

import com.ability_plus.system.entity.CheckException;
import com.ability_plus.user.entity.User;
import com.ability_plus.user.entity.VO.UserLoginVO;
import com.ability_plus.user.mapper.UserMapper;
import com.ability_plus.user.service.IUserService;
import com.ability_plus.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    static final long ONE_HOUR = 60*60*1000;

    @Override
    public Integer register(String fullName, String email, String password, String extraData, Boolean isCompany) throws Exception {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", email);
        List<User> users = this.list(wrapper);
        if (users.size()>=1){
            logger.warn("account: "+email+" exist");
            throw new CheckException("user exist");
        }

        User user = new User();
        user.setAccount(email);
        //TODO MD5加密
        user.setPassword(password);
        user.setFullName(fullName);
        user.setIsCompany(isCompany);
        user.setExtraData(extraData);

        this.save(user);
        return user.getId();
    }

    @Override
    public UserLoginVO login(String email, String password) throws Exception {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", email);
        wrapper.eq("password", password);
        List<User> users = this.list(wrapper);

        if (users.size() < 1) {
            logger.warn("account: " + email + " not found");
            throw new CheckException("user not found");
        }
        User user = users.get(0);
        HashMap<String, String> map = new HashMap<>();
        map.put("id",user.getId().toString());
        map.put("account",user.getAccount());
        map.put("isCompany",user.getIsCompany().toString());
        String token = JwtUtil.getToken(map);
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setUserName(user.getFullName());
        userLoginVO.setAccessToken(token);
        userLoginVO.setIsCompany(user.getIsCompany());
        return userLoginVO;
    }
}
