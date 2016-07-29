package org.destinyshine.jenode.sample; /**
 * Created by fengmian on 16/7/29.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by wenchao.ren on 2014/4/26.
 */
@EnableAutoConfiguration
@ImportResource("classpath:/applicationContext.xml")
@Configuration
@ComponentScan()
public class AppLauncher {

    public static void main(String[] args) {
        SpringApplication.run(AppLauncher.class, args);
    }
}