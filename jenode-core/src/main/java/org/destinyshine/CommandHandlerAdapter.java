package org.destinyshine;

/**
 * @author destinyliu
 */
public interface CommandHandlerAdapter {

    boolean supports(Object handler);

    Object handle(Object handler, Command command) throws Exception;

}
