package org.destinyshine.jenode.commanding.context;

import org.destinyshine.jenode.commanding.Command;

/**
 * Created by fengmian on 16/8/28.
 */
public interface CommandContextManager {
    
    <T> CommandContext<T> getCommandContext(Command command);
    
}
