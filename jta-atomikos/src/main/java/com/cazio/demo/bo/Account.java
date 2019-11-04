package com.cazio.demo.bo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @program jta-atomikos
 * @description 订单Object
 * @author: ca2io
 * @create: 2019-11-01 10:30
 **/
@Data
@Builder
public class Account {
    private long userid;
    private double money;
}
