package org.destinyshine.jenode.eventstore;

/**
 * Created by fengmian on 16/8/30.
 */
public abstract class AbstractEvent {
    
    private int version;
    
    public int getVersion() {
        return version;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }
}
