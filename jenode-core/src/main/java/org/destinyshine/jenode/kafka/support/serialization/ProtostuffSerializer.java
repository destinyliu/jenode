package org.destinyshine.jenode.kafka.support.serialization;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * Created by fengmian on 16/8/27.
 */
public class ProtostuffSerializer<T> implements Serializer<T> {

    private Schema<ValueWrapper> schema;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        schema = RuntimeSchema.getSchema(ValueWrapper.class);
    }

    @Override
    public byte[] serialize(String topic, T data) {
        LinkedBuffer linkedBuffer = LinkedBuffer.allocate(4096);
        byte[] bytes = ProtostuffIOUtil.toByteArray(ValueWrapper.wrap(data), schema, linkedBuffer);
        return bytes;
    }

    @Override
    public void close() {

    }

}
