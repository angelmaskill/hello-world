package starter.configuration;

import starter.publisher.DataAbstractPublisher;
import starter.subscriber.ExecutorSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * disruptor 环境启动类
 *
 * @author
 */

public class DisruptorEnvConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DisruptorEnvConfiguration.class);

    /**
     * 初始化配置信息
     */
    public void init(String propertiesInfo) {
        /** 解析环境配置,指定某个环需要用几个线程来执行*/
        Map<String, Integer> disruptorConfig = parseDisruptorConfig(propertiesInfo);
        /** 获取生产和消费者关系 */
        Map<DataAbstractPublisher, List<String>> puberSuberMapping = DisruptorSpringInit.getPuberSuberMapping();
        /** 获取消费者信息集合 */
        Map<String, ExecutorSubscriber> executorSubscriberMap = DisruptorSpringInit.getExecutorSubscribers();

        Integer threadSum = 0;
        for (Map.Entry<DataAbstractPublisher, List<String>> publisherListEntry : puberSuberMapping.entrySet()) {
            DataAbstractPublisher disruptorInstance = publisherListEntry.getKey();
            String publisherClassSimpleName = disruptorInstance.getClass().getSimpleName();
            Integer threadNums = disruptorInstance.getThreadNums();
            if (null == threadNums && !disruptorConfig.containsKey(publisherClassSimpleName)) {
                logger.warn("disruptor publisher:{} init failure ,because of  not exists thread nums ", publisherClassSimpleName);
                continue;
            }
            if (null == threadNums && disruptorConfig.containsKey(publisherClassSimpleName)) {
                disruptorInstance.setThreadNums(disruptorConfig.get(publisherClassSimpleName));
            }
            /** 取出订阅者信息*/
            List<String> suberNames = publisherListEntry.getValue();
            if (suberNames.size() == 0 || suberNames.isEmpty()) {
                logger.warn("disruptor publisher:{} init failure ,because of  not exists subscribers ", publisherClassSimpleName);
                continue;
            }
            List<ExecutorSubscriber> executorSubscribers = new ArrayList<>(suberNames.size());
            for (String subscriber : suberNames) {
                executorSubscribers.add(executorSubscriberMap.get(subscriber));
            }
            /** 环启动消费者*/
            disruptorInstance.start(executorSubscribers);
            threadSum = threadSum + disruptorInstance.getThreadNums();
            logger.info("disruptor publisher:{}, init success threadNum:{}", publisherClassSimpleName, disruptorInstance.getThreadNums());
        }
        logger.info("disruptor init success total thread nums:{}", threadSum);

    }

    /**
     * 解析外部spring boot 配置
     *
     * @param propertiesInfo
     */
    private Map<String, Integer> parseDisruptorConfig(String propertiesInfo) {
        String[] publisherInfos = propertiesInfo.split(";");
        Map<String, Integer> publisherSpringSets = new HashMap<>(publisherInfos.length);

        for (String publishserInfo : publisherInfos) {
            if (null == publishserInfo || "".equals(publishserInfo)) {
                continue;
            }
            String[] publisherInstanceInfo = publishserInfo.split(":");
            if (null == publisherInstanceInfo[1] || null == publisherInstanceInfo[0]) {
                continue;
            }
            publisherSpringSets.put(publisherInstanceInfo[0], Integer.valueOf(publisherInstanceInfo[1]));
        }
        return publisherSpringSets;
    }


}
