package starter.datatype;

/**
 * 定义你数据类型模版接口 获取数据类型 ，进行数据 找到对应的消费者
 */
public interface DataTypePattern {
    /**
     * 获取数据类型
     *
     * @return
     */
    Enum<?> getDataType();
}
