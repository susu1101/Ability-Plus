package com.ability_plus.user.service;


import com.ability_plus.user.entity.PO.ChangePasswordPO;
import com.ability_plus.user.entity.PO.UserProfileEditPO;
import com.ability_plus.user.entity.User;
import com.ability_plus.user.entity.VO.UserLoginVO;
import com.ability_plus.user.entity.VO.UserProfileVO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
public interface IUserService extends IService<User> {
    /**
     * user register
     * @param fullName
     * @param email
     * @param password
     * @param extraData
     * @param isCompany
     * @return
     */
    Integer register(String fullName,String email, String password,String extraData,Boolean isCompany) throws Exception;

    /**
     * user login
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    UserLoginVO login(String email, String password) throws Exception;

    /*get profile info
    @param http
    @return info
    @throws Exception*/

    UserProfileVO viewOwnProfileInfo(HttpServletRequest http) throws Exception;

    /**
     * user login
     * @param userId
     * @return
     * @throws Exception
     */
    UserProfileVO getProfileInfo(Integer userId) throws Exception;


    /**
     * edit a profile
     * @param po,http
     */
    public void editProfile(UserProfileEditPO po,HttpServletRequest http) throws Exception;


    /**
     * change password
     * @param po,http
     */
    public void changePassword(ChangePasswordPO po, HttpServletRequest http) throws Exception;

}
