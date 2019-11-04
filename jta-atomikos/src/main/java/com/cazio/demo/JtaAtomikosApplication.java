package com.cazio.demo;

import com.cazio.demo.config.AccountDBProperties;
import com.cazio.demo.config.UserDBProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(value = {UserDBProperties.class, AccountDBProperties.class})
public class JtaAtomikosApplication {
	public static void main(String[] args) {
		SpringApplication.run(JtaAtomikosApplication.class, args);
	}

}
