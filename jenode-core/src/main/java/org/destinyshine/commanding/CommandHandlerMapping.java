package org.destinyshine.commanding;

/**
 * @author destinyliu
 */
public interface CommandHandlerMapping {

    Object getHandler(Command command) throws Exception;


}
