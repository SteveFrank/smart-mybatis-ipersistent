package com.smart.framework.test;

import com.smart.framework.demo.mapper.UserMapper;
import com.smart.framework.demo.pojo.User;
import com.smart.ipersistent.io.Resources;
import com.smart.ipersistent.sqlSession.SqlSession;
import com.smart.ipersistent.sqlSession.SqlSessionFactory;
import com.smart.ipersistent.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author frankq
 * @date 2021/11/8
 */
public class IpersistentTest {

    /**
     * 逐步测试
     */
    @Test
    public void test1() throws Exception {
        InputStream resourceAsSteam = Resources.getResourceAsStream("sqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        System.out.println("start user.selectList ... ...");

        List<User> list = sqlSession.selectList("com.smart.framework.demo.mapper.UserMapper.findAllUser", null);

        for (User user : list) {
            System.out.println(user);
        }

        User userTemp = new User();
        userTemp.setId(1);
        userTemp.setUsername("tom");

        User user1 = sqlSession.selectOne("com.smart.framework.demo.mapper.UserMapper.findOneUser", userTemp);
        System.out.println(user1);
    }

    @Test
    public void test2() throws Exception {
        // 1. 根据配置文件的路径，将配置文件加载成字节输入流，存入到内存中
        InputStream resourceAsSteam = Resources.getResourceAsStream("sqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(1);
        user.setUsername("tom");

        // 代理对象
        UserMapper mapperProxy = sqlSession.getMapper(UserMapper.class);

        User user2 = mapperProxy.findOneUser(user);
        System.out.println(user2);

        List<User> users = mapperProxy.findAllUser();
        System.out.println(users);
    }


}
