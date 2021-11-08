package com.smart.ipersistent.executor;

import com.smart.ipersistent.config.BoundSql;
import com.smart.ipersistent.pojo.Configuration;
import com.smart.ipersistent.pojo.MappedStatement;
import com.smart.ipersistent.utils.GenericTokenParser;
import com.smart.ipersistent.utils.ParameterMapping;
import com.smart.ipersistent.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 具体执行JDBC操作
 *
 * @author frankq
 * @date 2021/11/8
 */
public class SimpleExecutor implements Executor {

    /**
     * 执行JDBC操作
     * @param configuration
     * @param mappedStatement
     * @param param
     * @param <E>
     */
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param)
            throws Exception {

        // 1、加载驱动，获取数据库连接
        Connection connection = configuration.getDataSource().getConnection();

        // 2、获取预编译对象 PrepareStatement
        // select * from user where id = #{id} and username = #{username}
        // 替换占位符：
        // 1）#{} 替换成?
        // 2）#{}里面的值要保存下来
        // #{} 里面的值存储到了BoundSql对象中list集合中
        String sqlText = mappedStatement.getSqlText();
        BoundSql boundSql = getBoundSql(sqlText);

        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getFinalSql());

        // 3、设置参数
        String parameterType = mappedStatement.getParameterType();
        if (parameterType != null) {
            Class<?> parameterTypeClass = Class.forName(parameterType);
            // 问题2：该把对象中的哪一个属性赋值给哪一个占位符呢？
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            for (int i = 0; i < parameterMappings.size(); i ++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                // id or username
                String filedName = parameterMapping.getContent();
                Field declaredField = parameterTypeClass.getDeclaredField(filedName);
                // 暴力访问
                declaredField.setAccessible(true);
                Object value = declaredField.get(param);
                preparedStatement.setObject(i + 1, value);
            }
        }

        // 4、执行SQL
        ResultSet resultSet = preparedStatement.executeQuery();

        // 5、封装返回结果集
        // 问题1: 需要封装到哪个对象
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = Class.forName(resultType);

        List<E> list = new ArrayList<>();
        while (resultSet.next()){
            ResultSetMetaData metaData = resultSet.getMetaData();
            Object o = resultTypeClass.newInstance();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                // id  username
                String columnName = metaData.getColumnName(i);
                Object columnValue = resultSet.getObject(columnName);

                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, columnValue);
            }
            list.add((E) o);
        }

        return list;
    }

    /**
     * 替换占位符：
     *  1）#{} 替换成?
     *  2）#{}里面的值要保存下来
     */
    private BoundSql getBoundSql(String sqlText) {
        // 标记处理器：配合标记解析器完成标记的解析工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        // 直接使用的是mybatis的源码 GenericTokenParser
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        // 带有？的sql语句
        String finalSql = genericTokenParser.parse(sqlText);
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        return new BoundSql(finalSql, parameterMappings);

    }

}
