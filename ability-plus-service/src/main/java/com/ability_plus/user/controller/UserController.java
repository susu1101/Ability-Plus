package com.ability_plus.user.controller;


import com.ability_plus.projectRequest.entity.PO.ProjectEditPO;
import com.ability_plus.projectRequest.entity.VO.ProjectDetailInfoVO;
import com.ability_plus.user.entity.PO.UserProfileEditPO;
import com.ability_plus.user.entity.VO.UserLoginVO;
import com.ability_plus.user.entity.VO.UserProfileVO;
import com.ability_plus.user.service.IUserService;
import com.ability_plus.utils.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
@RestController
@RequestMapping("/user")
@Api(value="user")
public class UserController {


    @Autowired
    IUserService userService;

    @PostMapping("/register")
    @ApiOperation("user register")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fullName", value = "name of user", required = true),
            @ApiImplicitParam(name = "email", value = "email of user", required = true),
            @ApiImplicitParam(name = "password", value = "password", required = true),
            @ApiImplicitParam(name = "extraData", value = "json extraData"),
            @ApiImplicitParam(name = "isCompany", value = "isCompany", required = true),

    })
    public RestResponse<Integer> register(@RequestParam(value = "fullName") String fullName,
                                          @RequestParam(value = "email") String email,
                                          @RequestParam(value = "password") String password,
                                          @RequestParam(value = "extraData",required=false) String extraData,
                                          @RequestParam(value = "isCompany") Boolean isCompany) throws Exception {
        Integer userId = userService.register(fullName, email, password, extraData, isCompany);

        return RestResponse.success(userId);

    }

    @PostMapping("/login")
    @ApiOperation("user login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "email of user", required = true),
            @ApiImplicitParam(name = "password", value = "password", required = true)
    })
    public RestResponse<UserLoginVO> login(@RequestParam(value = "email") String email,
                                           @RequestParam(value = "password") String password) throws Exception {
        UserLoginVO userLoginVO = userService.login(email, password);

        return RestResponse.success(userLoginVO);

    }

    @GetMapping("/view_own_profile_info")
    @ApiOperation("view own profile info")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "user id", required = true),
    })
    public RestResponse<UserProfileVO> getProfileInfo(@RequestParam Integer id) throws Exception{
        UserProfileVO userProfileVO = userService.getProfileInfo(id);
        return RestResponse.success(userProfileVO);
    }

    @PostMapping("/edit_own_profile_info")
    @ApiOperation("edit own profile info")
    public RestResponse editProfile(@RequestBody UserProfileEditPO po) throws Exception{
        userService.editProfile(po);
        return RestResponse.success();
    }

    @PostMapping("/delete_account")
    @ApiOperation("delete account")
    public RestResponse deleteAccount(@RequestParam Integer id) throws Exception{
        userService.deleteAccount(id);
        return RestResponse.success();
    }
}
