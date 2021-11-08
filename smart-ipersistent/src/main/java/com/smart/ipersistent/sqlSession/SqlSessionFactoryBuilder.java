package com.smart.ipersistent.sqlSession;

import com.smart.ipersistent.config.XMLConfigBuilder;
import com.smart.ipersistent.pojo.Configuration;
import org.dom4j.DocumentException;

import java.io.InputStream;

/**
 * @author frankq
 * @date 2021/11/8
 */
public class SqlSessionFactoryBuilder {

    /**
     * 1) 配置文件解析，封装Configuration
     * 2) 创建SqlSessionFactory实现类对象
     * @return
     * @throws DocumentException
     */
    public SqlSessionFactory build(InputStream inputStream) throws Exception {
        // 1)解析配置文件，封装Configuration
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        // 获取到连接信息；以及用户配置的mapper信息
        Configuration configuration = xmlConfigBuilder.parse(inputStream);
        System.out.println(configuration.getDataSource());
        System.out.println(configuration.getMappedStatementMap());
        // 2)创建SqlSessionFactory实现类对象
        return new DefaultSqlSessionFactory(configuration);
    }

}
