package com.smart.framework.demo.mapper;

import com.smart.framework.demo.pojo.User;

import java.util.List;

/**
 * @author frankq
 * @date 2021/11/8
 */
public interface UserMapper {

    /**
     * 查询所有
     */
    <E> List<E> findAllUser() throws Exception;

    /**
     * 查询单个
     */
    <T> T findOneUser(User user) throws Exception;

}
