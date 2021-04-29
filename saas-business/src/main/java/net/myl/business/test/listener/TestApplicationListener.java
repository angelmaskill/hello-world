package net.myl.business.test.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @ClassName TestApplicationListener
 * @Description TODO
 * @Author yanlu.myl
 * @Date 2021-04-29 11:43
 */
@Component
public class TestApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("TestApplicationListener............................");
    }
}
