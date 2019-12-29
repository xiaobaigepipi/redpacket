package com.lxh.red.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import javax.xml.ws.Service;
import java.util.Properties;

/*
 * @PackageName: com.lxh.red.config
 * @ClassName: RootConfig
 * @Description:
 * @author: 辉
 * @date: 2019/12/28 23:57
 * */
//配置文件类
@Configuration
//定义Spring扫描的包, includeFilters为指定哪些类型有资格用于组件扫描。
@ComponentScan(value = "com.*")
//使用事务驱动管理器
@EnableTransactionManagement
//实现接口TransactionManagementConfigurer， 这样可以配置注解驱动事务
public class RootConfig implements TransactionManagementConfigurer {

    private DataSource dataSource;

    /*
     * @Author 辉
     * @Description //TODO 配置数据库
     * @Date 0:13 2019/12/29
     * @Param
     * @return 数据连接池
     **/
    @Bean(name = "dataSource")
    public DataSource initDataSource() {
        if (dataSource != null) {
            return dataSource;
        }
        Properties props = new Properties();
        props.setProperty("driverClassName", "com.mysql.jdbc.Driver");
        props.setProperty("url", "jdbc:mysql://localhost:3306/redpack?useUnicode=true&characterEncoding=utf8");
        props.setProperty("username", "root");
        props.setProperty("password", "admin");
        props.setProperty("maxActive", "200");
        props.setProperty("minIdle", "3");
        props.setProperty("maxWait", "30000");

        try {
            dataSource = DruidDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    /*
     * @Author 辉
     * @Description //TODO 配置SqlSessionFactoryBean
     * @Date 0:25 2019/12/29
     * @Param []
     * @return org.mybatis.spring.SqlSessionFactoryBean
     **/
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean initSqlSessionFactory() {
        SqlSessionFactoryBean sql = new SqlSessionFactoryBean();
        sql.setDataSource(initDataSource());
        //扫描XML文件
        Resource resource = new ClassPathResource("mybatis-config.xml");
        sql.setConfigLocation(resource);
        return sql;
    }

    /*
     * @Author 辉
     * @Description //TODO 通过自动扫描， 发现Mapper接口
     * @Date 0:38 2019/12/29
     * @Param []
     * @return org.mybatis.spring.mapper.MapperScannerConfigurer
     **/
    @Bean
    public MapperScannerConfigurer initMapperScannerConfigurer() {

        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setBasePackage("com.*");
        msc.setSqlSessionFactoryBeanName("sqlSessionFactory");
        msc.setAnnotationClass(Repository.class);
        return msc;

    }

    /*
     * @Author 辉
     * @Description //TODO 实现接口方法，注册注解事务，当@Transactional使用时产生数据库事务
     * @Date 0:41 2019/12/29
     * @Param []
     * @return org.springframework.transaction.PlatformTransactionManager
     **/
    @Override
    @Bean(name = "annotationDrivenTransactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        DataSourceTransactionManager dtm = new DataSourceTransactionManager();
        dtm.setDataSource(initDataSource());
        return dtm;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate initRedisTemplate() {
        return null;
    }
}
