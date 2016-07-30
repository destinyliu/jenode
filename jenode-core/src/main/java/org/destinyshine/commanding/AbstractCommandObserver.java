package org.destinyshine.commanding;

/**
 * Created by fengmian on 16/7/30.
 */
public abstract class AbstractCommandObserver implements CommandObserver {

    private CommandDispatcher commandDispatcher;

    @Override
    public CommandDispatcher getCommandDispatcher() {
        return commandDispatcher;
    }

    @Override
    public void setCommandDispatcher(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }
}
