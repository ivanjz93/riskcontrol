package org.dan.utils;

import org.dan.entity.Condition;
import org.dan.entity.PaymentInfo;
import org.dan.entity.RuleTrigger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RiskControlHandler {
    private static RiskControlDao dao = new RiskControlDao();

    private static Map<String, List<Condition>> ruleMap;

    static {
        loadRuleMap();
    }

    public static List<RuleTrigger> match(PaymentInfo info){
        List<RuleTrigger> ruleTriggerList = new ArrayList<>();
        for(String ruleId : ruleMap.keySet()) {
            boolean isTrigger = false;
            List<Condition> conditionList = ruleMap.get(ruleId);
            for(Condition condition : conditionList) {
                int compare = condition.getCompare();
                if(compare == 1) {
                    String value = info.getFieldValue(condition.getField());
                    if(!condition.getField().equals("totalPrice")) {
                        if(!condition.getValue().equals(value))
                            isTrigger = true;
                    } else {
                        double doubleValue = Double.parseDouble(value);
                        if(doubleValue != Double.parseDouble(condition.getValue())){
                            isTrigger = true;
                        }
                    }

                } else if( compare == 2) {

                }
                //else if....

                if(isTrigger) {
                    RuleTrigger ruleTrigger = new RuleTrigger(info.getOrderId(), Integer.parseInt(ruleId));
                    ruleTriggerList.add(ruleTrigger);
                    break;
                }
            }
        }
        return ruleTriggerList;
    }

    public static void saveTriggers(List<RuleTrigger> triggers) {
        dao.saveRuleTriggers(triggers);
    }
    private static void loadRuleMap() {
        ruleMap = dao.loadRuleMap();
    }


}
