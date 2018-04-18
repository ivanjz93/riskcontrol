package org.dan.entity;

import java.io.Serializable;

public class RuleTrigger implements Serializable {
    private int id;
    private String orderId;
    private int ruleId;

    public RuleTrigger(String orderId, int ruleId) {
        this.orderId = orderId;
        this.ruleId = ruleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }
}
