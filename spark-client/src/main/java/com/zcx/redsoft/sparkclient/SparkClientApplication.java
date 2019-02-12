package com.zcx.redsoft.sparkclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SparkClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SparkClientApplication.class, args);
        Streaming.startRead();
    }
}
