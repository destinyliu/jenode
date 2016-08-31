package org.destinyshine.jenode.eventstore;

/**
 * Created by fengmian on 16/8/30.
 */
public class EventIndex {
    
    private int aggregateRootId;
    
    private int version;
    
    private int commandId;
    
    public int getAggregateRootId() {
        return aggregateRootId;
    }
    
    public void setAggregateRootId(int aggregateRootId) {
        this.aggregateRootId = aggregateRootId;
    }
    
    public int getVersion() {
        return version;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }
    
    public int getCommandId() {
        return commandId;
    }
    
    public void setCommandId(int commandId) {
        this.commandId = commandId;
    }
}
