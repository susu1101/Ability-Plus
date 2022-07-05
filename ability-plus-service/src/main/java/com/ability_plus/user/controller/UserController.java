package com.ability_plus.user.controller;


import com.ability_plus.user.service.IUserService;
import com.ability_plus.utils.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @ApiImplicitParam(name = "password", value = "password", required = true),
            @ApiImplicitParam(name = "isCompany", value = "isCompany", required = true),
    })
    public RestResponse<Integer> login(@RequestParam(value = "email") String email,
                                          @RequestParam(value = "password") String password,
                                          @RequestParam(value = "isCompany") Boolean isCompany) throws Exception {
        Integer userId = userService.login(email, password, isCompany);

        return RestResponse.success(userId);

    }
}
