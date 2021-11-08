package com.smart.ipersistent.sqlSession;

import com.smart.ipersistent.executor.Executor;
import com.smart.ipersistent.pojo.Configuration;
import com.smart.ipersistent.pojo.MappedStatement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
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

        // 使用JDK的动态代理的方式生成代理对象
        Object proxyInstance = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            // 参数1 : Object proxy 代理对象的引用，很少使用
            // 参数2 : Method method : 当前被调用的方法对象
            // 参数3 : Object[] objects : 被调用的方法参数
            @Override
            public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {

                // 具体执行逻辑：执行底层的JDBC
                // 思路  :  通过调用sqlSession的方法来执行
                // 问题1 : 如何获取statementId根据method获取
                Class<?> declaringClass = method.getDeclaringClass();
                // 类全路径 = namespace 值
                String className = declaringClass.getName();
                String methodName = method.getName();
                String statementId = className + "." + methodName;
                MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);

                // 问题2 : 该调用增删改查什么方法？
                String sqlCommandType = mappedStatement.getSqlCommandType();
                switch (sqlCommandType) {
                    case "select":
                        // 查询操作
                        // 问题3 : 调selectOne 还是调用 selectAll呢？
                        Class<?> returnType = method.getReturnType();
                        boolean assignableFrom = Collection.class.isAssignableFrom(returnType);
                        if(assignableFrom){
                            if(mappedStatement.getParameterType() !=null) {
                                return selectList(statementId, objects[0]);
                            }
                            return selectList(statementId, null);
                        }
                        return selectOne(statementId,objects[0]);
                    case "update":
                        // 更新操作
                        break;
                    case "insert":
                        // 更新操作
                        break;
                    case "delete":
                        // 更新操作
                        break;
                    default:
                        break;
                }
                return null;
            }
        });

        return (T) proxyInstance;
    }
}
