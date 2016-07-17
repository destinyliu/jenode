package org.destinyshine;

import org.springframework.web.servlet.ModelAndView;

/**
 * @author destinyliu
 */
public class AnnotationCommandHandlerAdapter implements CommandHandlerAdapter{

    @Override
    public boolean supports(Object handler) {
        return false;
    }

    @Override
    public ModelAndView handle(Object handler, Command command) throws Exception {
        return null;
    }
}
