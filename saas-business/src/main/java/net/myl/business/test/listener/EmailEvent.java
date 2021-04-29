package net.myl.business.test.listener;

import org.springframework.context.ApplicationEvent;

/**
 * @ClassName EmailEvent
 * @Description TODO
 * @Author yanlu.myl
 * @Date 2021-04-29 11:47
 */
public class EmailEvent extends ApplicationEvent {
    private String address;
    private String text;

    public EmailEvent(Object source, String address, String text) {
        super(source);
        this.address = address;
        this.text = text;
    }

    public EmailEvent(Object source) {
        super(source);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

