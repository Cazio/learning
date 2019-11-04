package com.cazio.demo.controller;

import com.cazio.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @program jta-atomikos
 * @description 用户测试Controller
 * @author: ca2io
 * @create: 2019-11-04 11:26
 **/
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/commit")
    public Map testCommit(){
        Map map = new HashMap();
        userService.save();
        map.put("flag","ok");
        return map;
    }

    @RequestMapping("/rollback")
    public Map testRollback(){
        Map map = new HashMap();
        userService.rollback();
        map.put("flag","Rollback");
        return map;
    }
}
