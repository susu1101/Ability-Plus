package com.ability_plus.test.controller;


import com.ability_plus.test.service.ITestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author susu
 * @since 2022-06-25
 */
@RestController
@RequestMapping("/test")
@Api(value="测试swagger")
public class TestController {
    @Autowired
    ITestService testService;

    /**
     * 内容不相对应的
     * @return
     */
    @GetMapping("/print")
    @ApiOperation("测试接口1")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色ID", required = true),
            @ApiImplicitParam(name = "name", value = "用户名", required = true)
    })
    public String test(){
        return testService.recordTest();
    }
}
