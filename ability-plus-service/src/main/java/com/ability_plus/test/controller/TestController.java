package com.ability_plus.test.controller;


import com.ability_plus.test.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class TestController {
    @Autowired
    ITestService testService;
    @GetMapping("/print")
    public String test(){
        return testService.recordTest();
    }
}
