package starter;

import starter.configuration.DisruptorEnvConfiguration;
import starter.configuration.DisruptorSpringInit;
import starter.properties.PublisherProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

/**
 * 定义disruptor spring boot 启动类
 */
@Configuration
@Order(-200000)
public class DisruptorSpringbootStarter {
    private static final Logger logger = LoggerFactory.getLogger(DisruptorSpringbootStarter.class);
    @Resource
    private PublisherProperties publisherProperties;

    @Bean
    public DisruptorSpringInit disruptorSpringInit() {
        return new DisruptorSpringInit();
    }

    /**
     * 初始化disruptor 环境配置
     *
     * @return
     */
    @Bean
    public DisruptorEnvConfiguration disruptorEnvConfiguration() {
        String propertiesInfo = publisherProperties.getInfo();
        DisruptorEnvConfiguration disruptorEnvConfiguration = new DisruptorEnvConfiguration();
        try {
            disruptorEnvConfiguration.init(propertiesInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("disruptor init error:{}", e.getMessage());
        }
        return disruptorEnvConfiguration;
    }

}
