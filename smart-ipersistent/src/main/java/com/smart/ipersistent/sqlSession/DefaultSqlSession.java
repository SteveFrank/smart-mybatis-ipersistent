package com.smart.ipersistent.sqlSession;

import com.smart.ipersistent.executor.Executor;
import com.smart.ipersistent.pojo.Configuration;
import com.smart.ipersistent.pojo.MappedStatement;

import java.util.List;

/**
 * @author frankq
 * @date 2021/11/8
 */
public class DefaultSqlSession implements SqlSession {

    /**
     * 需要往下传递的
     */
    private Configuration configuration;
    /**
     * 委派给执行器进行具体的SQL操作
     * mybatis 有多种类型，此处暂时使用一种进行展示和使用
     */
    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    /**
     * 集合泛型：E
     */
    @Override
    public <E> List<E> selectList(String statementId, Object param) throws Exception {
        // 要传递什么参数呢？
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<E> list = executor.query(configuration, mappedStatement, param);
        return list;
    }

    @Override
    public <T> T selectOne(String statementId, Object param) throws Exception {
        // 调用selectList方法
        List<Object> list = this.selectList(statementId, param);
        if(list.size() == 1) {
            return (T) list.get(0);
        } else if(list.size() > 1) {
            throw new RuntimeException("返回结果过多...");
        }
        return null;
    }

    /**
     * 生成代理对象
     */
    @Override
    public <T> T getMapper(Class<?> mapperClass) {

        return null;
    }
}
