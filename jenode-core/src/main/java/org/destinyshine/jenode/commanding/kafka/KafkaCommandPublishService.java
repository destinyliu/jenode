package org.destinyshine.jenode.commanding.kafka;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.destinyshine.jenode.commanding.Command;
import org.destinyshine.jenode.commanding.CommandPublishService;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author destinyliu
 */
public class KafkaCommandPublishService implements KafkaDefualts, CommandPublishService, InitializingBean {

//    private KafkaTemplate<String, Command> kafkaTemplate;

    private KafkaProducer<String, Command> kafkaProducer;


    private String topic;

    @Override
    public void publish(Command command) {
        ProducerRecord<String, Command> producerRecord = assembleProducerRecord(command);
        kafkaProducer.send(producerRecord);
    }

    protected ProducerRecord<String, Command> assembleProducerRecord(Command command) {
        String key = String.valueOf(command.getId());
        return new ProducerRecord<>(topic, key, command);
    }


    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setKafkaProducer(KafkaProducer<String, Command> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
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
