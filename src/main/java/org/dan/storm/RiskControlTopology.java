package org.dan.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

public class RiskControlTopology {
    private static String zkConnStr = "nn1-ha:2181,nn2:2181,nn2-ha:2181";
    private static String topicName = "risk_control";

    public static void main(String[] args) throws Exception {
        BrokerHosts hosts = new ZkHosts(zkConnStr);
        SpoutConfig spoutConfig = new SpoutConfig(hosts, topicName, "/" + topicName, "kafkaSpout");
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafkaSpout", kafkaSpout, 2);
        builder.setBolt("paymentInfoParseBolt", new PaymentInfoParserBolt(), 3).shuffleGrouping("kafkaSpout");
        builder.setBolt("save2DBBolt", new Save2DBBolt(), 3).shuffleGrouping("paymentInfoParseBolt");

        Config config = new Config();
        config.setNumWorkers(3);
        if(args.length > 0 ){
            StormSubmitter.submitTopology(args[0], config, builder.createTopology());
        } else {
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology(RiskControlTopology.class.getSimpleName(), config, builder.createTopology());
        }

    }
}
