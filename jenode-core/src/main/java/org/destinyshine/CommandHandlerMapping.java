package org.destinyshine;

import org.springframework.web.servlet.HandlerExecutionChain;

/**
 * @author destinyliu
 */
public interface CommandHandlerMapping {

    Object getHandler(Command command) throws Exception;


}
