package org.destinyshine.jenode.commanding;

import org.springframework.cache.CacheManager;

import java.io.Serializable;

/**
 * Created by fengmian on 16/7/30.
 */
public class DefaultCommandContext<T> implements CommandContext<T> {

    private CacheManager cacheManager;

    private Class<T> commandType;

    @Override
    public void add(T aggregateRoot) {

        cacheManager.getCache(commandType.getName()).putIfAbsent(aggregateRoot.getClass(),  aggregateRoot);
    }

    @Override
    public T get(Serializable id) {
        return null;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
