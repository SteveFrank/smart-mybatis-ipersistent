package com.smart.ipersistent.sqlSession;

import java.util.List;

/**
 * @author frankq
 * @date 2021/11/8
 */
public interface SqlSession {

    /**
     * 查询所有
     */
    <E> List<E> selectList(String statementId, Object param) throws Exception;

    /**
     * 查询单个
     */
    <T> T selectOne(String statementId, Object param) throws Exception;

    /**
     * 获取代理对象
     */
    <T> T getMapper(Class<?> mapperClass);

}
