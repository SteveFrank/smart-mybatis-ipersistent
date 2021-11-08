package com.smart.ipersistent.config;

import com.smart.ipersistent.pojo.Configuration;
import com.smart.ipersistent.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 *
 * mapper文件的数据解析
 *
 * @author frankq
 * @date 2021/11/8
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     *  2.解析mapper.xml(封装MappedStatement) 3.将封装好MappedStatement存到configuration的map集合中
     * @param resourceAsSteam
     */
    public void parse(InputStream resourceAsSteam) throws DocumentException {

        Document document = new SAXReader().read(resourceAsSteam);
        Element rootElement = document.getRootElement();

        List<Element> selectList = rootElement.selectNodes("//select");

        String namespace = rootElement.attributeValue("namespace");
        for (Element element : selectList) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String parameterType = element.attributeValue("parameterType");
            String sql = element.getTextTrim();

            // 封装MappedStatement对象
            // statementId
            String statementId = namespace + "." + id;
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setStatementId(statementId);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSqlText(sql);
            mappedStatement.setSqlCommandType("select");

            // 存储到configuration的map集合中
            configuration.getMappedStatementMap().put(statementId, mappedStatement);
        }

    }

}
