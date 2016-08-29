package org.destinyshine.jenode.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.beans.factory.FactoryBean;

import java.util.Properties;

/**
 * Created by fengmian on 16/8/27.
 */
public class ProducerFactory<K, V> implements FactoryBean<KafkaProducer<K, V>> {

    private Properties properties;
    private Serializer<K> keySerializer;
    private Serializer<V> valueSerializer;

    @Override
    public KafkaProducer<K, V> getObject() throws Exception {
        return new KafkaProducer<>(properties, keySerializer, valueSerializer);
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

    public Serializer<K> getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(Serializer<K> keySerializer) {
        this.keySerializer = keySerializer;
    }

    public Serializer<V> getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(Serializer<V> valueSerializer) {
        this.valueSerializer = valueSerializer;
    }
}
