package org.destinyshine.jenode.commanding.context.springcache;

import org.destinyshine.jenode.commanding.Command;
import org.destinyshine.jenode.commanding.context.CommandContext;
import org.destinyshine.jenode.commanding.context.CommandContextManager;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Map;

/**
 * Created by fengmian on 16/8/30.
 */
public class SpringCacheCommandContextManager implements CommandContextManager {
    
    private CacheManager cacheManager;
    
    private Map<String, CommandContext> commandContexts;
    
    @Override
    public <T> CommandContext<T> getCommandContext(Command command) {
        //cacheManager.addCacheIfAbsent("command");
        Cache cache = cacheManager.getCache("command");
        SpringCacheCommandContext commandContext = new SpringCacheCommandContext(cache, command.getClass());
        return commandContext;
    }
    
    private <T> CommandContext<T> craeteCommandContext(Command command) {
        
        return (CommandContext<T>) null;
    }
    
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
