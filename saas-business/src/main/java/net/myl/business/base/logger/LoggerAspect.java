package net.myl.business.base.logger;

import net.myl.business.base.utils.AnnotationResolver;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * 日志切面
 *
 * @author yanlu.myl
 */
@Component
@Aspect
public class LoggerAspect extends HandlerInterceptorAdapter {
    private org.slf4j.Logger logger = LoggerFactory.getLogger("entry");

    @Around("@annotation(net.myl.business.base.logger.Logger)")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Logger logger = method.getAnnotation(Logger.class);

        Object value1 = AnnotationResolver.newInstance().resolver(joinPoint, logger.param1()); //利用AnnotationResolver进行解析
        alterAnnotationValue("param1", value1.toString(), logger);

        Object value2 = AnnotationResolver.newInstance().resolver(joinPoint, logger.param2());

        alterAnnotationValue("param2", value2.toString(), logger);
        Object result = joinPoint.proceed();
    }


    public static void alterAnnotationValue(String key, String targetValue, Annotation annoTable) {
        try {
            if (annoTable == null) {
                throw new RuntimeException("please add @Logger for Test");
            }
            // 获取代理处理器
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(annoTable);
            // 过去私有 memberValues 属性
            Field f = invocationHandler.getClass().getDeclaredField("memberValues");
            f.setAccessible(true);
            // 获取实例的属性map
            Map<String, Object> memberValues = (Map<String, Object>) f.get(invocationHandler);
            // 修改属性值
            memberValues.put(key, targetValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}