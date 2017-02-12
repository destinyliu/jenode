package org.destinyshine.jenode.commanding.kafka;

import org.apache.commons.lang3.StringUtils;
import org.destinyshine.jenode.commanding.Command;
import org.destinyshine.jenode.commanding.CommandPublishService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author destinyliu
 */
public class KafkaCommandPublishService implements KafkaDefaults, CommandPublishService, InitializingBean {


    private KafkaTemplate<String, Command> KafkaTemplate;


    private String topic;

    @Override
    public void publish(Command command) {
        String key = String.valueOf(command.getId());
        KafkaTemplate.send(topic, key, command);
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setKafkaTemplate(KafkaTemplate<String, Command> kafkaTemplate) {
        KafkaTemplate = kafkaTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isBlank(topic)) {
            this.topic = DEFAULT_TOPIC;
        }
    }

    //public void setKafkaTemplate(KafkaTemplate<String, Command> kafkaTemplate) {
    //this.kafkaTemplate = kafkaTemplate;
    //}
}
