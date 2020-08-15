package net.myl.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by sw on 2020/3/18.
 */
@SpringBootApplication(scanBasePackages = "net.myl.business")
public class SpringBootApplicationStarter {
    public static void main(String[] args)
    {
        SpringApplication.run(SpringBootApplicationStarter.class, args);
    }
}