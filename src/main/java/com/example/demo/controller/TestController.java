package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.config.RedisConfig.redisConfig;

/**
 * @Author: liuyi on 2020/2/13
 * @Description:
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public Object hello(){
        return "hello world!";
    }

    @GetMapping("/test")
    public Object test(){
        return "hello world! this is Test";
    }

    @GetMapping("/redis")
    public Object redisTest(){
        redisConfig().set("hello:hello", "test111");
        String hello = redisConfig().get("hello");
        return hello;
    }
}
