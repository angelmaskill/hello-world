package net.myl;

import net.myl.business.test.listener.EmailEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class SpringBootApplicationStarter {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringBootApplicationStarter.class, args);
        //创建一个ApplicationEvent对象
        EmailEvent event = new EmailEvent("hello", "abc@163.com", "This is a test");
        //主动触发该事件
        run.publishEvent(event);
    }

    private static void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        //创建一个ApplicationEvent对象
        EmailEvent event = new EmailEvent("hello", "abc@163.com", "This is a test");
        //主动触发该事件
        context.publishEvent(event);
    }
}