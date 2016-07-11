package test;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListener;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import java.util.List;

/**
 * Created by jianyu.liu@hnlark.com on 2016/7/11.
 *
 * @author jianyu.liu@hnlark.com
 */
public class DefaultMQPushConsumerFactoryBean extends AbstractFactoryBean<DefaultMQPushConsumer> implements InitializingBean {

    private String nameServer;

    private String consumerGroup;

    private String consumeFromWhere;

    private MessageListenerConcurrently messageListener;

    @Override
    public Class<?> getObjectType() {
        return DefaultMQPushConsumer.class;
    }

    @Override
    protected DefaultMQPushConsumer createInstance() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(nameServer);
        //订阅PushTopic下Tag为push的消息
        consumer.subscribe("PushTopic", "push");
        //程序第一次启动从消息队列头取数据
        consumer.setConsumeFromWhere(ConsumeFromWhere.valueOf(consumeFromWhere));
        consumer.registerMessageListener(messageListener);
        consumer.start();
        return consumer;
    }

}
