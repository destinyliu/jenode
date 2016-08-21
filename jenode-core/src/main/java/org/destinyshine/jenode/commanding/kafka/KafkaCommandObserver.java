package org.destinyshine.jenode.commanding.kafka;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.destinyshine.jenode.commanding.Command;
import org.destinyshine.jenode.commanding.CommandDispatcher;
import org.destinyshine.jenode.commanding.CommandObserver;
import org.springframework.beans.factory.InitializingBean;

import java.util.Arrays;

/**
 * Created by fengmian on 16/8/13.
 */
public class KafkaCommandObserver implements KafkaDefualts, CommandObserver, InitializingBean {

    private KafkaConsumer<String, Command> commandConsumer;

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

        commandConsumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, Command> records = commandConsumer.poll(100);
            for (ConsumerRecord<String, Command> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
                Command command = record.value();
                getCommandDispatcher().dispatch(command);
            }
        }
    }
}
