package org.destinyshine;

import org.springframework.web.servlet.ModelAndView;

/**
 * @author destinyliu
 */
public interface CommandHandlerAdapter {

    boolean supports(Object handler);

    ModelAndView handle(Object handler, Command command) throws Exception;

}
