package starter.executor;


import starter.datatype.DataTypePattern;
import starter.subscriber.ExecutorSubscriber;

/**
 * 定义数据订阅接口
 */
public interface DataExecutorTypeSubscriber<T extends DataTypePattern> extends ExecutorSubscriber<T> {
    /**
     * 数据类型定义接口 根据数据 返回数据类型
     * Gets type.
     *
     * @return the type
     */
    Enum<?> getType();
}
