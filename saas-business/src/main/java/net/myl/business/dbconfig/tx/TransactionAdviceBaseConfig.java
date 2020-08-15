package net.myl.business.dbconfig.tx;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 马彦卢
 * @version V1.0
 * @Description
 * @Package net.newcapec.cloudpay.core.config
 * @date 2020/5/29 9:54
 */
@Aspect
@Configuration
public class TransactionAdviceBaseConfig {

    private  final String BASECONFIG_EXPRESSION = "execution(* net.myl.business.service..*.*(..))";

    @Resource(name = "baseConfigTransactionManager")
    private PlatformTransactionManager transactionManager;

    /**
     * @description 事务管理配置
     */
    @Bean(name = "baseConfigTxAdvice")
    public TransactionInterceptor TxAdvice() {
        // 事务管理规则，承载需要进行事务管理的方法名（模糊匹配）及设置的事务管理属性
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        // 设置第一个事务管理的模式（适用于“增删改”）
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        // 当抛出设置的对应异常后，进行事务回滚（此处设置为“Exception”级别）
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        // 设置传播行为（存在事务则加入其中，不存在则新建事务）
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 设置隔离级别（该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下的推荐值。 ）
        requiredTx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);

        // 设置第二个事务管理的模式（适用于“查”）
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        // 当抛出设置的对应异常后，进行事务回滚（此处设置为“Exception”级别）
        readOnlyTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        // 设置传播行为（存在事务则挂起该事务，执行当前逻辑，结束后再恢复上下文事务）
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        // 设置隔离级别（该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下的推荐值。 ）
        readOnlyTx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        // 设置事务是否“只读”（非必需，只是声明该事务中不会进行修改数据库的操作，可减轻由事务造成的数据库压力，属于性能优化的推荐配置）
        readOnlyTx.setReadOnly(true);

        // 建立一个map，用来储存要需要进行事务管理的方法名（模糊匹配）
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("insert*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("add*",requiredTx);
        txMap.put("modify*",requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("remove*",requiredTx);

        txMap.put("query*", readOnlyTx);
        txMap.put("get*", readOnlyTx);
        txMap.put("select*", readOnlyTx);
        txMap.put("find*", readOnlyTx);

        // 注入设置好的map
        source.setNameMap(txMap);
        // 实例化事务拦截器
        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, source);
        return txAdvice;
    }

    /**
     * @description 利用AspectJExpressionPointcut设置切面
     */
    @Bean(name = "baseConfigTxAdviceAdvisor")
    public Advisor txAdviceAdvisor() {
        // 声明切点要切入的面  切面（Aspect）：切面就是通知和切入点的结合。
        // 通知和切入点共同定义了关于切面的全部内容——它的功能、在何时和何地完成其功能。
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // 设置需要被拦截的路径
        pointcut.setExpression(BASECONFIG_EXPRESSION);
        // 设置切面=切点pointcut+通知TxAdvice
        return new DefaultPointcutAdvisor(pointcut, TxAdvice());
    }
}
