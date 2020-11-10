package com.caizo.autoconfiguration;

import com.cazio.config.CazioApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AutoconfigurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoconfigurationApplication.class, args);
    }

//    @Bean
//    public CazioApplicationRunner cazioApplicationRunner1(){
//        return new CazioApplicationRunner("sb");
//    }
}
