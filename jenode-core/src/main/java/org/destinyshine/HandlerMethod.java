//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.destinyshine;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class HandlerMethod {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final Object bean;
    private final BeanFactory beanFactory;
    private final Class<?> beanType;
    private final Method method;
    private final Method bridgedMethod;
    private final MethodParameter[] parameters;
    private final HandlerMethod resolvedFromHandlerMethod;

    public HandlerMethod(Object bean, Method method) {
        Assert.notNull(bean, "Bean is required");
        Assert.notNull(method, "Method is required");
        this.bean = bean;
        this.beanFactory = null;
        this.beanType = ClassUtils.getUserClass(bean);
        this.method = method;
        this.bridgedMethod = BridgeMethodResolver.findBridgedMethod(method);
        this.parameters = this.initMethodParameters();
        this.resolvedFromHandlerMethod = null;
    }

    public HandlerMethod(Object bean, String methodName, Class... parameterTypes) throws NoSuchMethodException {
        Assert.notNull(bean, "Bean is required");
        Assert.notNull(methodName, "Method name is required");
        this.bean = bean;
        this.beanFactory = null;
        this.beanType = ClassUtils.getUserClass(bean);
        this.method = bean.getClass().getMethod(methodName, parameterTypes);
        this.bridgedMethod = BridgeMethodResolver.findBridgedMethod(this.method);
        this.parameters = this.initMethodParameters();
        this.resolvedFromHandlerMethod = null;
    }

    public HandlerMethod(String beanName, BeanFactory beanFactory, Method method) {
        Assert.hasText(beanName, "Bean name is required");
        Assert.notNull(beanFactory, "BeanFactory is required");
        Assert.notNull(method, "Method is required");
        this.bean = beanName;
        this.beanFactory = beanFactory;
        this.beanType = ClassUtils.getUserClass(beanFactory.getType(beanName));
        this.method = method;
        this.bridgedMethod = BridgeMethodResolver.findBridgedMethod(method);
        this.parameters = this.initMethodParameters();
        this.resolvedFromHandlerMethod = null;
    }

    protected HandlerMethod(HandlerMethod handlerMethod) {
        Assert.notNull(handlerMethod, "HandlerMethod is required");
        this.bean = handlerMethod.bean;
        this.beanFactory = handlerMethod.beanFactory;
        this.beanType = handlerMethod.beanType;
        this.method = handlerMethod.method;
        this.bridgedMethod = handlerMethod.bridgedMethod;
        this.parameters = handlerMethod.parameters;
        this.resolvedFromHandlerMethod = handlerMethod.resolvedFromHandlerMethod;
    }

    private HandlerMethod(HandlerMethod handlerMethod, Object handler) {
        Assert.notNull(handlerMethod, "HandlerMethod is required");
        Assert.notNull(handler, "Handler object is required");
        this.bean = handler;
        this.beanFactory = handlerMethod.beanFactory;
        this.beanType = handlerMethod.beanType;
        this.method = handlerMethod.method;
        this.bridgedMethod = handlerMethod.bridgedMethod;
        this.parameters = handlerMethod.parameters;
        this.resolvedFromHandlerMethod = handlerMethod;
    }

    private MethodParameter[] initMethodParameters() {
        int count = this.bridgedMethod.getParameterTypes().length;
        MethodParameter[] result = new MethodParameter[count];

        for(int i = 0; i < count; ++i) {
            result[i] = new HandlerMethod.HandlerMethodParameter(i);
        }

        return result;
    }

    public Object getBean() {
        return this.bean;
    }

    public Method getMethod() {
        return this.method;
    }

    public Class<?> getBeanType() {
        return this.beanType;
    }

    protected Method getBridgedMethod() {
        return this.bridgedMethod;
    }

    public MethodParameter[] getMethodParameters() {
        return this.parameters;
    }

    public HandlerMethod getResolvedFromHandlerMethod() {
        return this.resolvedFromHandlerMethod;
    }

    public MethodParameter getReturnType() {
        return new HandlerMethod.HandlerMethodParameter(-1);
    }

    public MethodParameter getReturnValueType(Object returnValue) {
        return new HandlerMethod.ReturnValueMethodParameter(returnValue);
    }

    public boolean isVoid() {
        return Void.TYPE.equals(this.getReturnType().getParameterType());
    }

    public <A extends Annotation> A getMethodAnnotation(Class<A> annotationType) {
        return AnnotatedElementUtils.findMergedAnnotation(this.method, annotationType);
    }

    public <A extends Annotation> boolean hasMethodAnnotation(Class<A> annotationType) {
        return AnnotatedElementUtils.hasAnnotation(this.method, annotationType);
    }

    public HandlerMethod createWithResolvedBean() {
        Object handler = this.bean;
        if(this.bean instanceof String) {
            String beanName = (String)this.bean;
            handler = this.beanFactory.getBean(beanName);
        }

        return new HandlerMethod(this, handler);
    }

    public String getShortLogMessage() {
        int args = this.method.getParameterTypes().length;
        return this.getBeanType().getName() + "#" + this.method.getName() + "[" + args + " args]";
    }

    public boolean equals(Object other) {
        if(this == other) {
            return true;
        } else if(!(other instanceof HandlerMethod)) {
            return false;
        } else {
            HandlerMethod otherMethod = (HandlerMethod)other;
            return this.bean.equals(otherMethod.bean) && this.method.equals(otherMethod.method);
        }
    }

    public int hashCode() {
        return this.bean.hashCode() * 31 + this.method.hashCode();
    }

    public String toString() {
        return this.method.toGenericString();
    }

    private class ReturnValueMethodParameter extends HandlerMethod.HandlerMethodParameter {
        private final Object returnValue;

        public ReturnValueMethodParameter(Object returnValue) {
            super();
            this.returnValue = returnValue;
        }

        protected ReturnValueMethodParameter(HandlerMethod.ReturnValueMethodParameter original) {
            super();
            this.returnValue = original.returnValue;
        }

        public Class<?> getParameterType() {
            return this.returnValue != null?this.returnValue.getClass():super.getParameterType();
        }

        public HandlerMethod.ReturnValueMethodParameter clone() {
            return HandlerMethod.this.new ReturnValueMethodParameter(this);
        }
    }

    protected class HandlerMethodParameter extends SynthesizingMethodParameter {
        public HandlerMethodParameter(int index) {
            super(HandlerMethod.this.bridgedMethod, index);
        }

        protected HandlerMethodParameter(HandlerMethod.HandlerMethodParameter original) {
            super(original);
        }

        public Class<?> getContainingClass() {
            return HandlerMethod.this.getBeanType();
        }

        public <T extends Annotation> T getMethodAnnotation(Class<T> annotationType) {
            return HandlerMethod.this.getMethodAnnotation(annotationType);
        }

        public <T extends Annotation> boolean hasMethodAnnotation(Class<T> annotationType) {
            return HandlerMethod.this.hasMethodAnnotation(annotationType);
        }

        public HandlerMethod.HandlerMethodParameter clone() {
            return HandlerMethod.this.new HandlerMethodParameter(this);
        }
    }
}
