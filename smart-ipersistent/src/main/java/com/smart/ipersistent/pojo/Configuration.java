package com.smart.ipersistent.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 第二步：进行配置实体类的设置
 * 全局配置类：存储SqlMapConfig.xml配置文件的内容
 *
 * @author frankq
 * @date 2021/11/8
 */
public class Configuration {

    /**
     * 数据源对象
     */
    private DataSource dataSource;
    /**
     * key:   statementId
     * value: 封装好的MappedStatement
     */
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
