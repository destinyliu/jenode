package org.destinyshine.jenode.commanding;

/**
 * @author destinyliu
 */
public class AbstractCommand implements Command {

    private Long id;
    
    private Long aggregateRootId;
    
    @Override
    public Long getId() {
        return id;
    }
    
    @Override
    public Long getAggregateRootId() {
        return aggregateRootId;
    }
}
