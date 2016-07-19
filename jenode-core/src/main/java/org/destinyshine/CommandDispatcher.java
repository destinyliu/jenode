package org.destinyshine;

/**
 * @author destinyliu
 */
public class CommandDispatcher {

    private CommandHandlerMapping commandHandlerMapping;

    private CommandHandlerAdapter commandHandlerAdapter;


    public void dispatch(Command command) throws Exception {
        Object handler = commandHandlerMapping.getHandler(command);
        commandHandlerAdapter.handle(handler, command);
    }

    public void setCommandHandlerMapping(CommandHandlerMapping commandHandlerMapping) {
        this.commandHandlerMapping = commandHandlerMapping;
    }

    public void setCommandHandlerAdapter(CommandHandlerAdapter commandHandlerAdapter) {
        this.commandHandlerAdapter = commandHandlerAdapter;
    }
}
