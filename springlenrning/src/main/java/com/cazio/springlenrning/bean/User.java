package com.cazio.springlenrning.bean;

import lombok.Data;

import javax.annotation.PostConstruct;

/**
 * @author Ca2io
 * @version 1.0
 * @description
 * @since 2020/5/9 9:22
 */
@Data
public class User {
    public static void main(String[] args) {
        User user = new User();
    }
    @PostConstruct
    private void postConstruct() {
        System.out.println("psotCOnstruct");
    }

    private long id;
    private String name;
}
