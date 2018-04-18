package org.dan.utils;

import org.dan.entity.Condition;
import org.dan.entity.RuleTrigger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RiskControlDao {
    private JdbcTemplate jdbcTemplate;

    public RiskControlDao() {
        try {
            jdbcTemplate = new JdbcTemplate(DataSourceUtil.getDataSource());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Map<String, List<Condition>> loadRuleMap() {
        Map<String, List<Condition>> ruleMap = new HashMap<>();
        List<Condition> conditionList = loadConditionList();
        for(Condition condition : conditionList) {
            List<Condition> ruleIdConditions = ruleMap.get(condition.getRuleId());
            if(ruleIdConditions == null) {
                ruleIdConditions = new ArrayList<>();
                ruleMap.put(condition.getRuleId(), ruleIdConditions);
            }
            ruleIdConditions.add(condition);
        }
        return ruleMap;
    }

    public List<Condition> loadConditionList() {
        String sql = "select * from condition_order_monitor";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Condition>(Condition.class));
    }

    public int[][] saveRuleTriggers(List<RuleTrigger> triggers) {
        String sql = "INSERT INTO trigger_rule" +
                "(id, orderId, ruleId)" +
                "VALUES (?,?,?)";
        return jdbcTemplate.batchUpdate(sql, triggers, triggers.size(),
                (ps, t)-> {
                    ps.setInt(1,1);
                    ps.setString(2, t.getOrderId());
                    ps.setInt(3, t.getRuleId());
            });
    }
}
