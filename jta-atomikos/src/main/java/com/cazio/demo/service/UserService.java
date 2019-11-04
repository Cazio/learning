package com.cazio.demo.service;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @program jta-atomikos
 * @description 用户service接口
 * @author: ca2io
 * @create: 2019-11-01 10:56
 **/
@Service
public interface UserService {
    /**
     * 正常保存
     */
    @Transactional
    void save();

    /**
     * 异常保存，回滚情况
     */
    @Transactional
    void rollback();

}
