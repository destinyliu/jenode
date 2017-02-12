package org.destinyshine.jenode.commanding.kafka;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.destinyshine.jenode.commanding.Command;
import org.destinyshine.jenode.commanding.CommandDispatcher;
import org.destinyshine.jenode.commanding.CommandObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.kafka.listener.MessageListener;

import java.util.Arrays;

/**
 * Created by fengmian on 16/8/13.
 */
public class KafkaCommandObserver implements MessageListener<String, Command>, KafkaDefaults, CommandObserver, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String topic;
    private CommandDispatcher commandDispatcher;

    @Override
    public CommandDispatcher getCommandDispatcher() {
        return commandDispatcher;
    }

    @Override
    public void setCommandDispatcher(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isBlank(topic)) {
            this.topic = DEFAULT_TOPIC;
        }

    }

    @Override
    public void onMessage(ConsumerRecord<String, Command> record) {
        System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
        Command command = record.value();
        try {
            getCommandDispatcher().dispatch(command);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
