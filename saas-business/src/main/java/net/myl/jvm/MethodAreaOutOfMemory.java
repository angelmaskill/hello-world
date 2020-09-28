package net.myl.jvm;


import org.mockito.cglib.proxy.Enhancer;
import org.mockito.cglib.proxy.MethodInterceptor;
import org.mockito.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


/**
 * 1.8之后, 常量池移出方法区.
 * @Described：方法区溢出测试
 * 使用技术 CBlib
 * @VM args : -XX:PermSize=10M -XX:MaxPermSize=10M
 */

public class MethodAreaOutOfMemory {
    /**
     * @param args
     */
    public static void main(String[] args) {
        while(true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(TestCase.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object arg0, Method arg1, Object[] arg2,
                                        MethodProxy arg3) throws Throwable {
                    return arg3.invokeSuper(arg0, arg2);
                }
            });
            enhancer.create();
        }
    }
}
