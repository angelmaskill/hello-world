package starter.publisher;


import starter.executor.DataConsumerExecutor;
import starter.manager.DisruptorProviderManage;
import starter.provider.DisruptorProvider;
import starter.subscriber.ExecutorSubscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 定义生产者实现类
 *
 * @param <T>
 */
public abstract class DataAbstractPublisher<T> implements DataPublisher<T> {
    private DisruptorProviderManage providerManage;

    private DataConsumerExecutor.DataExecutorFactory factory;
    private Integer threadNums;


    /**
     * 设置线程池数量
     *
     * @param threadNums
     */
    public void setThreadNums(Integer threadNums) {
        this.threadNums = threadNums;
    }

    public Integer getThreadNums() {
        return threadNums;
    }


    /**
     * 生产数据启动
     */
    public void start(List<ExecutorSubscriber> executorSubscribers) {

        this.factory = new DataConsumerExecutor.DataExecutorFactory(this.getClass().getSimpleName());
        this.factory.addSubscribers(executorSubscribers);
        this.providerManage = new DisruptorProviderManage(factory, threadNums, DisruptorProviderManage.DEFAULT_SIZE);
        this.providerManage.startup();
    }

    @Override
    public <T> void publish(T t) {
        DisruptorProvider<Object> provider = this.providerManage.getProvider();
        List<T> ts = Collections.singletonList(t);
        provider.onData(f -> f.setData(ts));
    }


    @Override
    public <T> void publishList(T t) {
        DisruptorProvider<Object> provider = this.providerManage.getProvider();
        List<T> convertData = (ArrayList) t;
        convertData.parallelStream().forEach(e -> {
            publish(e);
        });
    }


    /**
     * 关闭线程
     */
    @Override
    public void close() {
        this.providerManage.getProvider().shutdown();
    }
}
