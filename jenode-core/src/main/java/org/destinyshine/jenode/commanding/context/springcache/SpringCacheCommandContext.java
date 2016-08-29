package org.destinyshine.jenode.commanding.context.springcache;

import org.destinyshine.jenode.commanding.context.CommandContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.io.Serializable;

/**
 * Created by fengmian on 16/7/30.
 */
public class SpringCacheCommandContext<T> implements CommandContext<T>, InitializingBean {

    private CacheManager cacheManager;
    
    private Cache cache;

    private Class<T> commandType;

    @Override
    public void add(T aggregateRoot) {
        cacheManager.getCache(commandType.getName())
                .putIfAbsent(aggregateRoot.getClass(), aggregateRoot);
    }

    @Override
    public T get(Serializable id) {
        return (T) cache.get(id);
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        //this.cache = cacheManager.()
    }
}
