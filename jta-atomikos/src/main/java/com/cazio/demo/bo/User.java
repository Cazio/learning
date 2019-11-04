package com.cazio.demo.bo;

import lombok.Builder;
import lombok.Data;

/**
 * @program jta-atomikos
 * @description 用户类
 * @author: ca2io
 * @create: 2019-11-01 10:28
 **/
@Data
@Builder
public class User {
    private long id;
    private String name;
}
