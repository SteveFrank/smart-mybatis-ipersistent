package com.smart.ipersistent.config;

import com.smart.ipersistent.utils.ParameterMapping;

import java.util.List;

/**
 * @author frankq
 * @date 2021/11/8
 */
public class BoundSql {

    private String finalSql;

    private List<ParameterMapping> parameterMappings;

    public BoundSql(String finalSql, List<ParameterMapping> parameterMappings) {
        this.finalSql = finalSql;
        this.parameterMappings = parameterMappings;
    }

    public String getFinalSql() {
        return finalSql;
    }

    public void setFinalSql(String finalSql) {
        this.finalSql = finalSql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
