package net.myl.business.base.logger;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yanlu.myl
 * @date 2016年4月13日 上午7:53:52
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Logger {
    String param1() default "";
    String param2() default "" ;
}
