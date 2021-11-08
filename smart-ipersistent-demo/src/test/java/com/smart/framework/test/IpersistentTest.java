package com.smart.framework.test;

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

        List<User> list = sqlSession.selectList("com.smart.framework.demo.mapper.UserMapper.selectList", null);

        for (User user : list) {
            System.out.println(user);
        }

        User userTemp = new User();
        userTemp.setId(1);
        userTemp.setUsername("tom");

        User user1 = sqlSession.selectOne("com.smart.framework.demo.mapper.UserMapper.selectOne", userTemp);
        System.out.println(user1);

    }

}
