package com.cazio.springlenrning.bean.factory;

import com.cazio.springlenrning.ioc.overview.dependency.domain.User;
import org.springframework.context.annotation.Configuration;

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
