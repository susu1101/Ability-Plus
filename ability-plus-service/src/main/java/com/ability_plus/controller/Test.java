package com.ability_plus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sjx
 */
@RestController
@RequestMapping
public class Test {
    @GetMapping("/test")
    public String test(){return "good luck have fun";}
}
