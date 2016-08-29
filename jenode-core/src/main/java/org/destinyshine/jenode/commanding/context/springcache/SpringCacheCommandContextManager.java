package org.destinyshine.jenode.commanding.context.springcache;

import net.sf.ehcache.CacheManager;
import org.destinyshine.jenode.commanding.Command;
import org.destinyshine.jenode.commanding.context.CommandContext;
import org.destinyshine.jenode.commanding.context.CommandContextManager;

import java.util.Map;

/**
 * Created by fengmian on 16/8/30.
 */
public class SpringCacheCommandContextManager implements CommandContextManager {
    
    private CacheManager cacheManager;
    
    private Map<String, CommandContext> commandContexts;
    
    @Override
    public <T> CommandContext<T> getCommandContext(Command command) {
        cacheManager.addCacheIfAbsent("command");
        cacheManager.getCache("command");
        return null;
    }
    
    private <T> CommandContext<T> craeteCommandContext(Command command) {
        
        return (CommandContext<T>) null;
    }
    
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
