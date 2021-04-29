package starter.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义配置项
 */
@Configuration
@ConditionalOnClass(PublisherProperties.class)
@EnableConfigurationProperties(PublisherProperties.class)
public class DisruptorSpringConfiguration {


}
