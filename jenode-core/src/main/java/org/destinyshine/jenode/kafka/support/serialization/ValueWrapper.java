package org.destinyshine.jenode.kafka.support.serialization;

/**
 * Created by fengmian on 16/8/27.
 */
public class ValueWrapper {

    private Object value;

    public static ValueWrapper wrap(Object value) {
        ValueWrapper wrapper = new ValueWrapper();
        wrapper.set(value);
        return wrapper;
    }

    public Object get() {
        return value;
    }

    public void set(Object value) {
        this.value = value;
    }
}
