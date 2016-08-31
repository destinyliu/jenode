package org.destinyshine.jenode.commanding.context;

/**
 * context hold object. not visible to programmer.
 * Created by fengmian on 16/8/30.
 */
public class AggregateRootContainer<T> {
    
    private T aggregateRoot;
    
    private int id;
    
    private int version;
    
    public T getAggregateRoot() {
        return aggregateRoot;
    }
    
    public void setAggregateRoot(T aggregateRoot) {
        this.aggregateRoot = aggregateRoot;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getVersion() {
        return version;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }
}
