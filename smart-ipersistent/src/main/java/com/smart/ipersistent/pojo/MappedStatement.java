package com.smart.ipersistent.pojo;

/**
 * @author frankq
 * @date 2021/11/8
 */
public class MappedStatement {

    /**
     * 唯一标识 namespace.id
     */
    private String statementId;
    private String resultType;
    private String parameterType;
    private String sqlText;

    /**
     * 保存 标签的类型
     */
    private String sqlCommandType;

    public String getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(String sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }
}
