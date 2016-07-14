package org.destinyshine.rocketmq.core;


import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.commons.lang3.ArrayUtils;

public class ProtostuffMessageSerializer<T> implements MessageSerializer<T> {

    private static Schema objectWrapperSchema = RuntimeSchema.createFrom(ObjectWrapper.class);

    @Override
    public byte[] serialize(T t) {
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            ObjectWrapper<T> objectWrapper = new ObjectWrapper<>();
            objectWrapper.setValue(t);
            byte[] bytes = ProtostuffIOUtil.toByteArray(objectWrapper, objectWrapperSchema, buffer);
            return bytes;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    @Override
    public T deserialize(byte[] bytes) {
        if(ArrayUtils.isEmpty(bytes)) {
            return null;
        }
        try {
            ObjectWrapper<T> objectWrapper = new ObjectWrapper<>();
            ProtostuffIOUtil.mergeFrom(bytes, objectWrapper, objectWrapperSchema);
            return objectWrapper.getValue();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static class ObjectWrapper<T> {
        private T value;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}