package com.nju.netease;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication
@EntityScan
public class NeteaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeteaseApplication.class, args);
    }

}
