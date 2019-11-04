package com.cazio.demo.service.impl;

import com.cazio.demo.bo.Account;
import com.cazio.demo.bo.User;
import com.cazio.demo.mapper.account.AccountMapper;
import com.cazio.demo.mapper.user.UserMapper;
import com.cazio.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @program jta-atomikos
 * @description 测试多数据源事务Service
 * @author: ca2io
 * @create: 2019-11-01 10:54
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccountMapper orderMapper;


    @Override
    public void save() {
        User user = User
                .builder()
                .id(1)
                .name("cazio")
                .build();
        userMapper.insert(user);

        Account account = Account.builder()
                .userid(1)
                .money(100.0)
                .build();
        orderMapper.insert(account);

    }

    @Override
    public void rollback() {
        User user = User
                .builder()
                .id(2)
                .name("cazio12")
                .build();
        userMapper.insert(user);

        Account account = Account.builder()
                .userid(2)
                .money(100.0)
                .build();
        orderMapper.insert(account);
        throw new RuntimeException("异常情况");
    }
}
