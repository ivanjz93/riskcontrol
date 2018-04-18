package org.dan.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.dan.entity.RuleTrigger;
import org.dan.utils.RiskControlDao;
import org.dan.utils.RiskControlHandler;

import java.util.List;
import java.util.Map;

public class Save2DBBolt extends BaseRichBolt {

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

    }

    @Override
    public void execute(Tuple input) {
        List<RuleTrigger> triggerList = (List<RuleTrigger>)input.getValueByField("triggers");
        RiskControlHandler.saveTriggers(triggerList);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
