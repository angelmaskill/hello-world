package starter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 生产者注解类
 *
 * @author
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PublisherAnnotation {

    /**
     * 订阅者信息
     *
     * @return
     */
    String[] subscribers() default {};


}
