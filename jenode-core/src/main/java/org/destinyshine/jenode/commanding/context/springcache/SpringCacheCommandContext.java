package org.destinyshine.jenode.commanding.context.springcache;

import org.destinyshine.jenode.commanding.context.AggregateRootContainer;
import org.destinyshine.jenode.commanding.context.CommandContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.io.Serializable;

/**
 * Created by fengmian on 16/7/30.
 */
public class SpringCacheCommandContext<T> implements CommandContext<T>, InitializingBean {
    
    private final Cache cache;
    
    private final Class<T> commandType;
    
    public SpringCacheCommandContext(Cache cache, Class<T> commandType) {
        this.commandType = commandType;
        this.cache = cache;
    }
    
    @Override
    public void add(T aggregateRoot) {
        AggregateRootContainer container = createAggregateRootContainer(aggregateRoot);
        cache.put(container.getId(), container);
    }
    
    @Override
    public T get(Serializable id) {
        AggregateRootContainer<?> container = cache.get(id, AggregateRootContainer.class);
        return (T) container.getAggregateRoot();
    }
    
    public AggregateRootContainer createAggregateRootContainer(T aggregateRoot) {
        AggregateRootContainer container = new AggregateRootContainer();
        container.setAggregateRoot(aggregateRoot);
        container.setVersion(0);
        return container;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
