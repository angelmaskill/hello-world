package net.myl.business.dbconfig.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import net.myl.business.dbconfig.db.properties.DruidProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 基础业务数据数据库配置
 * Created by sw on 2020/4/13.
 *
 */
@Configuration
@MapperScan(basePackages={"net.myl.business.mapper"},
        sqlSessionFactoryRef = "baseConfigSqlSessionFactory")
public class BaseConfigDBConfig {
    @Autowired
    private DruidProperties druidProperties;

    /**
     * 初始化基础业务数据数据源
     * @param druidProperties
     * @return
     */
    @Bean(name = "baseConfigDataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource baseDataSource(DruidProperties druidProperties)
    {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    /**
     * 创建连接工厂
     * @param baseConfigDataSource
     * @return
     */
    @Bean(name = "baseConfigSqlSessionFactory")
    @Primary
    public SqlSessionFactory businessSqlSessionFactory(@Qualifier("baseConfigDataSource") DataSource baseConfigDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(baseConfigDataSource);
        sessionFactory.setMapperLocations(resolveMapperLocations());
        sessionFactory.setTypeAliasesPackage(
                "net.myl.business.domain");
        return sessionFactory.getObject();
    }

    /**
     * 加载多路径配置文件
     * @return
     */
    private Resource[] resolveMapperLocations() {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<String> mapperLocations = new ArrayList<>();
        mapperLocations.add("classpath*:mapper/**/*.xml");
        List<Resource> resources = new ArrayList();
        if (mapperLocations != null) {
            for (String mapperLocation : mapperLocations) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }

    /**
     * 创建事务
     * @return
     */
    @Bean(name = "baseConfigTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("baseConfigDataSource") DataSource baseConfigDataSource) {
        return new DataSourceTransactionManager(baseConfigDataSource);
    }

}
