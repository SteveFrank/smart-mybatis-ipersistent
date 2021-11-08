package com.smart.ipersistent.sqlSession;

import com.smart.ipersistent.executor.Executor;
import com.smart.ipersistent.executor.SimpleExecutor;
import com.smart.ipersistent.pojo.Configuration;

/**
 * @author frankq
 * @date 2021/11/8
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 创建出一个SqlSession对象
     */
    @Override
    public SqlSession openSession() {
        // 创建执行器对象
        Executor executor = new SimpleExecutor();
        return new DefaultSqlSession(configuration, executor);
    }
}
