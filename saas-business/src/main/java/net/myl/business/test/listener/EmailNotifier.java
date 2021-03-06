package net.myl.business.test.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName EmailNotifier
 * @Description TODO
 * @Author yanlu.myl
 * @Date 2021-04-29 11:48
 */
@Component
public class EmailNotifier implements ApplicationListener {
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof EmailEvent) {
            EmailEvent emailEvent = (EmailEvent)event;
            System.out.println("邮件地址：" + emailEvent.getAddress());
            System.out.println("邮件内容：" + emailEvent.getText());
        } else {
            System.out.println("容器本身事件：" + event);
        }
    }
}
