package test;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * Created by jianyu.liu@hnlark.com on 2016/7/11.
 *
 * @author jianyu.liu@hnlark.com
 */
public class RocketMQCommandPublisher implements CommnadPublisher {

    private String producerGroup;

    private String nameServer;

    private DefaultMQProducer

    @Override
    protected DefaultMQProducer createInstance() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);

        producer.setNamesrvAddr(nameServer);
        producer.start();
        return producer;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    @Override
    public void publish() {

    }
}
