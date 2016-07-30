package org.destinyshine.commanding;

/**
 * @author destinyliu
 */
public interface CommandHandlerAdapter {

    boolean supports(Object handler);

    Object handle(Object handler, Command command) throws Exception;

}
