package org.dan.storm;

import com.google.gson.Gson;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.dan.entity.PaymentInfo;
import org.dan.entity.RuleTrigger;
import org.dan.utils.RiskControlHandler;

import java.util.List;
import java.util.Map;

public class PaymentInfoParserBolt extends BaseRichBolt {
    private OutputCollector collector;
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String line = input.getString(0);
        PaymentInfo info = new Gson().fromJson(line, PaymentInfo.class);
        List<RuleTrigger> triggers = RiskControlHandler.match(info);
        if(triggers.size() > 0)
            collector.emit(new Values(triggers));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("triggers"));
    }
}
