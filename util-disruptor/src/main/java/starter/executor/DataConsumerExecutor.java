package starter.executor;


import starter.consumer.AbstractQueueConsumerFactory;
import starter.consumer.QueueConsumerExecutor;
import starter.datatype.DataTypePattern;
import starter.subscriber.ExecutorSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定义数据消费执行器
 */
public final class DataConsumerExecutor extends QueueConsumerExecutor<List<DataTypePattern>> {
    /**
     * 定义数据类型对应的订阅执行器关系
     */
    private Map<Enum<?>, ExecutorSubscriber> subscribers = new HashMap<>();
    /**
     * 数据消费者执行器名称
     */
    private String dataConsumerExecutorName;

    public DataConsumerExecutor(Map<Enum<?>, ExecutorSubscriber> subscriberMap) {
        this.subscribers.putAll(subscriberMap);

    }

    public DataConsumerExecutor(Map<Enum<?>, ExecutorSubscriber> subscriberMap, String dataConsumerExecutorName) {
        this.subscribers.putAll(subscriberMap);
        this.dataConsumerExecutorName = dataConsumerExecutorName;
    }

    @Override
    public void run() {
        //得到数据类型
        List<DataTypePattern> dataTypePatterns = getData();
        /**
         * 获取数据订阅者
         */
        getSubscriber(dataTypePatterns).executor(dataTypePatterns);
    }

    /**
     * 获取数据类型对应的订阅者
     *
     * @param list
     * @return
     */
    private ExecutorSubscriber getSubscriber(final List<DataTypePattern> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        DataTypePattern result = list.get(0);
        return subscribers.get(result.getDataType());
    }

    public static class DataExecutorFactory extends AbstractQueueConsumerFactory {
        private String name;

        public DataExecutorFactory(String name) {
            this.name = name;
        }

        @Override
        public QueueConsumerExecutor create() {
            Map<Enum<?>, ExecutorSubscriber> maps
                    = getSubscribers().stream().map(e -> (DataExecutorTypeSubscriber) e)
                    .collect(Collectors.toMap(DataExecutorTypeSubscriber::getType, e -> e));
            return new DataConsumerExecutor(maps);
        }

        @Override
        public String fixName() {
            return this.name;
        }
    }
}
