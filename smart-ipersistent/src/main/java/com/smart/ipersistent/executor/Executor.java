package com.smart.ipersistent.executor;

import com.smart.ipersistent.pojo.Configuration;
import com.smart.ipersistent.pojo.MappedStatement;

import java.util.List;

/**
 * @author frankq
 * @date 2021/11/8
 */
public interface Executor {

    /**
     * 封装统一查询接口
     * @param configuration
     * @param mappedStatement
     * @param param
     * @param <E>
     * @return
     * @throws Exception
     */
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception;

}
