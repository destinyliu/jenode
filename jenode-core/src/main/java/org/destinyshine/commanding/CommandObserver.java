package org.destinyshine.commanding;

/**
 * Created by fengmian on 16/7/30.
 */
public interface CommandObserver {
    CommandDispatcher getCommandDispatcher();

    void setCommandDispatcher(CommandDispatcher commandDispatcher);
}
