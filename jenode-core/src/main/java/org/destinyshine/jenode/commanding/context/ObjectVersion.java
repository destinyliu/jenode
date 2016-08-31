package org.destinyshine.jenode.commanding.context;

/**
 * read-only view of ObjectVersion.
 *
 * Created by fengmian on 16/8/30.
 */
public class ObjectVersion {
    
    private final int version;
    
    public ObjectVersion(int version) {
        this.version = version;
    }
    
    public int getVersion() {
        return version;
    }
    
}
