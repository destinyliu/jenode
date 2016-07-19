package org.destinyshine;

import org.springframework.classify.util.MethodInvokerUtils;
import org.springframework.util.ReflectionUtils;

/**
 * @author destinyliu
 */
public class AnnotationCommandHandlerAdapter implements CommandHandlerAdapter{

    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerMethod;
    }

    @Override
    public Object handle(Object handler, Command command) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Object target = handlerMethod.getBean();
        ReflectionUtils.invokeMethod(handlerMethod.getMethod(), target, command);
        return null;
    }
}
