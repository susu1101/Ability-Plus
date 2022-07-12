package com.ability_plus.user.service;


import com.ability_plus.projectRequest.entity.PO.ProjectEditPO;
import com.ability_plus.user.entity.PO.UserProfileEditPO;
import com.ability_plus.user.entity.User;
import com.ability_plus.user.entity.VO.UserLoginVO;
import com.ability_plus.user.entity.VO.UserProfileEditVO;
import com.ability_plus.user.entity.VO.UserProfileVO;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

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
    @param id
    @return info
    @throws Exception*/

    UserProfileVO getProfileInfo(Integer id) throws Exception;

    /*get profile edit info
    @param id
    @return info
    @throws Exception*/

    UserProfileEditVO getProfileEditInfo(Integer id) throws Exception;

    /**
     * edit a profile
     * @param po
     */
    public void editProfile(UserProfileEditPO po) throws Exception;

    /*delete account
    @param id
    @throws Exception*/
    public void deleteAccount(Integer id) throws Exception;
}
