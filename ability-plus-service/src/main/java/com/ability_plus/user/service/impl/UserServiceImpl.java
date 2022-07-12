package com.ability_plus.user.service.impl;

import com.ability_plus.system.entity.CheckException;
import com.ability_plus.user.entity.PO.ChangePasswordPO;
import com.ability_plus.user.entity.PO.UserProfileEditPO;
import com.ability_plus.user.entity.POJO.UserPOJO;
import com.ability_plus.user.entity.User;
import com.ability_plus.user.entity.VO.UserLoginVO;
import com.ability_plus.user.entity.VO.UserProfileVO;
import com.ability_plus.user.mapper.UserMapper;
import com.ability_plus.user.service.IUserService;
import com.ability_plus.utils.CheckUtils;
import com.ability_plus.utils.JwtUtil;
import com.ability_plus.utils.UserUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    @Override
    public UserProfileVO getProfileInfo(HttpServletRequest http) {
        UserProfileVO userProfileVO = new UserProfileVO();
        UserPOJO user = UserUtils.getCurrentUser(http);
        User userData = this.getById(user.getId());
        CheckUtils.assertNotNull(userData,"user not exists");
        BeanUtils.copyProperties(userData,userProfileVO);

        return userProfileVO;
    }



    @Override
    public void editProfile(UserProfileEditPO po,HttpServletRequest http){
        UserPOJO user = UserUtils.getCurrentUser(http);
        Integer userID = user.getId();
        String userName=po.getUserName();
        String extraData=po.getExtraData().toString();
        UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",userID);
        User userData=new User();
        //if need change password

        userData.setExtraData(extraData);
        if (CheckUtils.isNull(userName)){
            throw new CheckException("User name cannot be Null");
        }
        userData.setFullName(userName);
        this.update(userData,updateWrapper);
    }

    @Override
    public void changePassword(ChangePasswordPO po,HttpServletRequest http){
        UserPOJO user = UserUtils.getCurrentUser(http);
        Integer userID = user.getId();
        String password=po.getNewPassword();
        String oldPassword=po.getOldPassword();
        UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",userID);
        User userData=new User();
        if (!CheckUtils.isNull(password)){
            //old password cannot be null
            if (CheckUtils.isNull(oldPassword)){
                throw new CheckException("required old password");
            }
            if (this.getById(userID).getPassword().equals(oldPassword)){
                userData.setPassword(password);
            }else{
                throw new CheckException("Wrong old password!");
            }
        }
        this.update(userData,updateWrapper);


    }

    @Override
    public void deleteAccount(Integer id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        this.remove(queryWrapper);
    }
}
