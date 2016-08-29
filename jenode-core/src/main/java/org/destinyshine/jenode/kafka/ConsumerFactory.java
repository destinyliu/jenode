package org.destinyshine.jenode.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.beans.factory.FactoryBean;

import java.util.Properties;

/**
 * Created by fengmian on 16/8/27.
 */
public class ConsumerFactory<K, V> implements FactoryBean<KafkaConsumer<K, V>> {

    private Properties properties;
    private Deserializer<K> keyDeserializer;
    private Deserializer<V> valueDeserializer;

    @Override
    public KafkaConsumer<K, V> getObject() throws Exception {
        return new KafkaConsumer<>(properties, keyDeserializer, valueDeserializer);
    }

    @Override
    public Class<?> getObjectType() {
        return KafkaProducer.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Deserializer<K> getKeyDeserializer() {
        return keyDeserializer;
    }

    public void setKeyDeserializer(Deserializer<K> keyDeserializer) {
        this.keyDeserializer = keyDeserializer;
    }

    public Deserializer<V> getValueDeserializer() {
        return valueDeserializer;
    }

    public void setValueDeserializer(Deserializer<V> valueDeserializer) {
        this.valueDeserializer = valueDeserializer;
    }
}
