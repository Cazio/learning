package com.cazio.springlenrning.beandefinition.factory;

import com.cazio.springlenrning.overview.domain.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @auther kecz
 * @descript
 * @
 */
@Configuration
public interface UserFactory {
    default User createUser() {
        User user = new User();
        user.setId("1L");
        user.setName("timi");
        return user;
    }
}