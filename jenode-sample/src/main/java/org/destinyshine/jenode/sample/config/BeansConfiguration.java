package org.destinyshine.jenode.sample.config;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.destinyshine.jenode.commanding.Command;
import org.destinyshine.jenode.commanding.CommandDispatcher;
import org.destinyshine.jenode.commanding.CommandHandlerAdapter;
import org.destinyshine.jenode.commanding.CommandHandlerMapping;
import org.destinyshine.jenode.commanding.CommandObserver;
import org.destinyshine.jenode.commanding.CommandPublishService;
import org.destinyshine.jenode.commanding.kafka.KafkaCommandObserver;
import org.destinyshine.jenode.commanding.kafka.KafkaCommandPublishService;
import org.destinyshine.jenode.commanding.kafka.KafkaDefaults;
import org.destinyshine.jenode.commanding.method.AnnotationCommandHandlerAdapter;
import org.destinyshine.jenode.commanding.method.AnnotationCommandHandlerMapping;
import org.destinyshine.jenode.kafka.support.serialization.ProtostuffSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

/**
 * Created by liujianyu.ljy on 17/2/11.
 */
@Configuration
public class BeansConfiguration {

    @Bean
    public StringSerializer stringSerializer() {
        return new StringSerializer();
    }

    @Bean
    public Serializer protostuffSerializer() {
        return new ProtostuffSerializer();
    }

    @Bean
    public Deserializer stringDeserializer() {
        return new StringDeserializer();
    }

    @Bean
    public Deserializer protostuffDeserializer() {
        return new StringDeserializer();
    }

    @Bean
    public CommandPublishService commandPublishService(KafkaTemplate<String, Command> kafkaTemplate) {
        KafkaCommandPublishService kafkaCommandPublishService = new KafkaCommandPublishService();
        kafkaCommandPublishService.setKafkaTemplate(kafkaTemplate);
        return kafkaCommandPublishService;
    }

    @Bean
    public CommandHandlerAdapter commandHandlerAdapter() {
        return new AnnotationCommandHandlerAdapter();
    }

    @Bean
    public CommandHandlerMapping commandHandlerMapping() {
        return new AnnotationCommandHandlerMapping();
    }

    @Bean
    public CommandDispatcher commandDispatcher(
            CommandHandlerAdapter commandHandlerAdapter,
            CommandHandlerMapping commandHandlerMapping) {
        CommandDispatcher commandDispatcher = new CommandDispatcher();
        commandDispatcher.setCommandHandlerAdapter(commandHandlerAdapter);
        commandDispatcher.setCommandHandlerMapping(commandHandlerMapping);
        return commandDispatcher;
    }

    @Bean
    public CommandObserver commandObserver(
            CommandDispatcher commandDispatcher) {
        CommandObserver commandObserver = new KafkaCommandObserver();
        commandObserver.setCommandDispatcher(commandDispatcher);
        return commandObserver;
    }

    @Bean(initMethod = "doStart")
    public MessageListenerContainer messageListenerContainer(ConsumerFactory consumerFactory, CommandObserver commandObserver) {

        ContainerProperties containerProperties = new ContainerProperties(KafkaDefaults.DEFAULT_TOPIC);
        containerProperties.setMessageListener(commandObserver);
        ConcurrentMessageListenerContainer kafkaMessageListenerContainer = new ConcurrentMessageListenerContainer(consumerFactory, containerProperties);

        return kafkaMessageListenerContainer;
    }

}
