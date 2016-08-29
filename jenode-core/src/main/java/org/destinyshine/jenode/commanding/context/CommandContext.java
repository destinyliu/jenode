package org.destinyshine.jenode.commanding.context;

import java.io.Serializable;

/**
 * Created by fengmian on 16/7/30.
 */
public interface CommandContext<T> {

    void add(T aggregateRoot);

    T get(Serializable id);
}
