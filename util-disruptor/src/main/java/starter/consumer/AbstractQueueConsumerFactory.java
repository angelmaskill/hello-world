package starter.consumer;

import starter.subscriber.ExecutorSubscriber;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 定义消费者任务队列工厂
 */
public abstract class AbstractQueueConsumerFactory implements QueueConsumerFactory {
    /**
     * 定于订阅者数据集
     */
    private Set<ExecutorSubscriber> subscribers = new HashSet<>();

    /**
     * Add subscribers abstract queue consumer factory.
     *
     * @param subscriber the subscriber
     * @return the abstract queue consumer factory
     */
    public AbstractQueueConsumerFactory addSubscribers(final List<ExecutorSubscriber> subscriber) {
        int length = subscriber.size();
        for (int i = 0; i < length; i++) {
            subscribers.add(subscriber.get(i));
        }
        return this;
    }

    /**
     * Gets subscribers.
     *
     * @return the subscribers
     */
    public Set<ExecutorSubscriber> getSubscribers() {
        return subscribers;
    }
}
