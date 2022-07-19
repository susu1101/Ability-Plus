package com.ability_plus.user.service.impl;

import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.mapper.ProjectRequestMapper;
import com.ability_plus.projectRequest.service.IProjectRequestService;
import com.ability_plus.system.entity.CheckException;
import com.ability_plus.user.entity.PO.ChangePasswordPO;
import com.ability_plus.user.entity.PO.UserProfileEditPO;
import com.ability_plus.user.entity.POJO.UserPOJO;
import com.ability_plus.user.entity.User;
import com.ability_plus.user.entity.VO.CompaniesVO;
import com.ability_plus.user.entity.VO.UserLoginVO;
import com.ability_plus.user.entity.VO.UserProfileVO;
import com.ability_plus.user.mapper.UserMapper;
import com.ability_plus.user.service.IUserService;
import com.ability_plus.utils.CheckUtils;
import com.ability_plus.utils.JwtUtil;
import com.ability_plus.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
        //TODO 校验extradata是不是json
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
    public UserProfileVO viewOwnProfileInfo(HttpServletRequest http) {
        UserProfileVO userProfileVO = new UserProfileVO();
        UserPOJO user = UserUtils.getCurrentUser(http);
        User userData = this.getById(user.getId());
        CheckUtils.assertNotNull(userData,"user not exists");
        BeanUtils.copyProperties(userData,userProfileVO);

        return userProfileVO;
    }


    @Override
    public UserProfileVO getProfileInfo(HttpServletRequest http){
        UserProfileVO userProfileVO = new UserProfileVO();
        UserPOJO currentUser = UserUtils.getCurrentUser(http);
        User user=this.getById(currentUser.getId());
        CheckUtils.assertNotNull(user,"user not exists");
        BeanUtils.copyProperties(user,userProfileVO);

        return userProfileVO;
    }


    @Override
    public void editProfile(UserProfileEditPO po,HttpServletRequest http){
        UserPOJO user = UserUtils.getCurrentUser(http);
        Integer userId = user.getId();
        String userName=po.getUserName();
        String extraData= JSON.toJSONString(po.getExtraData());
        User userData = this.getById(userId);
        //if need change password
        userData.setExtraData(extraData);
        if (CheckUtils.isNull(userName)){
            throw new CheckException("User name cannot be Null");
        }
        userData.setFullName(userName);
        updateById(userData);
    }

    @Override
    public void changePassword(ChangePasswordPO po,HttpServletRequest http){
        UserPOJO user = UserUtils.getCurrentUser(http);
        Integer userId = user.getId();
        String password=po.getNewPassword();
        String oldPassword=po.getOldPassword();

        User userData = this.getById(userId);
        if (!CheckUtils.isNull(password)){
            //old password cannot be null
            if (CheckUtils.isNull(oldPassword)){
                throw new CheckException("required old password");
            }
            if (userData.getPassword().equals(oldPassword)){
                userData.setPassword(password);
            }else{
                throw new CheckException("Wrong old password!");
            }
        }
        this.updateById(userData);
    }

    @Override
    public List<CompaniesVO> listCompany() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("is_company",true);

        List<User> list = this.list(wrapper);
        List<CompaniesVO> companiesVOS = new ArrayList<>();
        for (User user:list){
            CompaniesVO companiesVO = new CompaniesVO();
            BeanUtils.copyProperties(user,companiesVO);
            companiesVOS.add(companiesVO);
        }


        return companiesVOS;
    }


}
