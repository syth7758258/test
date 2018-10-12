package com.slx.zsxt.model;

public class accountType {
    private Integer code;

    private String name;

    private String expression;

    public accountType(Integer code, String name, String expression) {
        this.code = code;
        this.name = name;
        this.expression = expression;
    }

    public accountType() {
        super();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression == null ? null : expression.trim();
    }
}