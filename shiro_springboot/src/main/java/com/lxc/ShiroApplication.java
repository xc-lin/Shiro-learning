package com.lxc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Frank_lin
 * @date 2022/11/27
 */
@SpringBootApplication
public class ShiroApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ShiroApplication.class, args);

    }
}
