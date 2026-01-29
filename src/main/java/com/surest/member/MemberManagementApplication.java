package com.surest.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.TimeZone;

@SpringBootApplication
@EnableCaching
public class MemberManagementApplication {

	public static void main(String[] args) {

        SpringApplication.run(MemberManagementApplication.class, args);
    }

}
