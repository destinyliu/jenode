package test;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * Created by jianyu.liu@hnlark.com on 2016/7/11.
 *
 * @author jianyu.liu@hnlark.com
 */
public class DefaultMQProducerFactoryBean extends AbstractFactoryBean<DefaultMQProducer> {

    private String producerGroup;

    private String nameServer;

    @Override
    public Class<?> getObjectType() {
        return DefaultMQProducer.class;
    }

    @Override
    protected DefaultMQProducer createInstance() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);

        producer.setNamesrvAddr(nameServer);
        producer.start();
        return producer;
    }
}
