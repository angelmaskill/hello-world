package starter.configuration;

import starter.annotation.PublisherAnnotation;
import starter.annotation.SubscriberAnnotation;
import starter.publisher.DataAbstractPublisher;
import starter.subscriber.ExecutorSubscriber;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理环境问题
 */
public class DisruptorSpringInit implements BeanPostProcessor {
    /**
     * 定义publisher 对应的订阅者集合
     */
    private static Map<DataAbstractPublisher, List<String>> puberSuberMapping = new HashMap<>();


    private static Map<String, ExecutorSubscriber> executorSubscribers = new HashMap<>();


    public static Map<DataAbstractPublisher, List<String>> getPuberSuberMapping() {
        return puberSuberMapping;
    }


    public static Map<String, ExecutorSubscriber> getExecutorSubscribers() {
        return executorSubscribers;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull final Object bean, @NonNull final String beanName) throws BeansException {
        PublisherAnnotation publisherAnnotation = AnnotationUtils.findAnnotation(bean.getClass(), PublisherAnnotation.class);
        SubscriberAnnotation subscriberAnnotation = AnnotationUtils.findAnnotation(bean.getClass(), SubscriberAnnotation.class);

        /** 如果是生产者, 且映射表中没有生产者, 初始化生产者map */
        if (publisherAnnotation != null && !puberSuberMapping.containsKey(bean)) {
            DataAbstractPublisher publisherInstance = (DataAbstractPublisher) bean;
            puberSuberMapping.put(publisherInstance, new ArrayList<>());
        }
        /*一个生产者发出的消息, 可以被多个消费者消费*/
        if (publisherAnnotation != null && puberSuberMapping.containsKey(bean)) {
            String[] subscribers = publisherAnnotation.subscribers();
            if (0 == subscribers.length) {
                return bean;
            }
            List<String> strings = puberSuberMapping.get((DataAbstractPublisher) bean);
            Collections.addAll(strings, subscribers);
        }
        /*如果是消费者, 放入消费者map*/
        if (subscriberAnnotation != null && !executorSubscribers.containsKey(subscriberAnnotation.name())) {
            executorSubscribers.put(subscriberAnnotation.name(), (ExecutorSubscriber) bean);
        }
        return bean;
    }
}
