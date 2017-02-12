package org.destinyshine.jenode.commanding.method;

import org.destinyshine.jenode.commanding.Command;
import org.destinyshine.jenode.commanding.CommandHandlerAdapter;
import org.destinyshine.jenode.commanding.HandlerMethod;
import org.springframework.util.ReflectionUtils;

/**
 * @author destinyliu
 */
public class AnnotationCommandHandlerAdapter implements CommandHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerMethod;
    }

    @Override
    public Object handle(Object handler, Command command) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Object target = handlerMethod.getBean();
        ReflectionUtils.invokeMethod(handlerMethod.getMethod(), target,null, command);
        return null;
    }
}
