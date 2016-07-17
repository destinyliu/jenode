package org.destinyshine;

import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author destinyliu
 */
public class KafkaCommandPublishService implements CommandPublishService {

    private KafkaTemplate<String, Command> kafkaTemplate;

    private String topic;

    @Override
    public void publish(Command command) {
        kafkaTemplate.send(topic, command);
    }

    public void setKafkaTemplate(KafkaTemplate<String, Command> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
