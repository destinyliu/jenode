package org.destinyshine.jenode.kafka.support.serialization;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

/**
 * Created by fengmian on 16/8/27.
 */
public class ProtostuffDeserializer<T> implements Deserializer<T>, InitializingBean {

    private Schema<ValueWrapper> schema;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        ValueWrapper valueWrapper = ValueWrapper.wrap(null);
        ProtostuffIOUtil.mergeFrom(data , valueWrapper, schema);
        return (T) valueWrapper.get();
    }


    @Override
    public void close() {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        schema = RuntimeSchema.getSchema(ValueWrapper.class);
    }
}
