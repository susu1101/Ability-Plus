package com.ability_plus.user.service;


import com.ability_plus.user.entity.User;
import com.ability_plus.user.entity.VO.UserLoginVO;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
