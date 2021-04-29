package starter.publisher;

/**
 * 定义数据发布者
 */
public interface DataPublisher<T> {
    /**
     * publish message
     *
     * @param t
     * @param <T>
     */
    <T> void publish(T t);

    /**
     * push message List
     *
     * @param t
     * @param <T>
     */
    public <T> void publishList(T t);

    /**
     * 关闭
     */
    public void close();
}
