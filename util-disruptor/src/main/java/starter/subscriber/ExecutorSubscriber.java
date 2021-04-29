package starter.subscriber;

import java.util.Collection;

/**
 * 数据定于者接口
 *
 * @param <T>
 */
public interface ExecutorSubscriber<T> {
    /**
     * Executor.
     *
     * @param dataList the data list
     */
    void executor(Collection<T> dataList);
}
