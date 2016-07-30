package org.destinyshine.commanding;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.destinyshine.*;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
/**
 * @author destinyliu
 */
public class AnnotationCommandHandlerMapping extends ApplicationObjectSupport implements CommandHandlerMapping, InitializingBean {

    protected final Log logger = LogFactory.getLog(this.getClass());

    private static final String SCOPED_TARGET_NAME_PREFIX = "scopedTarget.";

    private boolean detectHandlerMethodsInAncestorContexts = false;

    private final MappingRegistry mappingRegistry = new MappingRegistry();


    public void setDetectHandlerMethodsInAncestorContexts(boolean detectHandlerMethodsInAncestorContexts) {
        this.detectHandlerMethodsInAncestorContexts = detectHandlerMethodsInAncestorContexts;
    }

    /**
     * Return a (read-only) map with all mappings and HandlerMethod's.
     */
    public Map<CommandMappingInfo, HandlerMethod> getHandlerMethods() {
        this.mappingRegistry.acquireReadLock();
        try {
//            return Collections.unmodifiableMap(this.mappingRegistry.getMappings());
            return null;
        }
        finally {
            this.mappingRegistry.releaseReadLock();
        }
    }

    /**
     * Return the internal mapping registry. Provided for testing purposes.
     */
    MappingRegistry getMappingRegistry() {
        return this.mappingRegistry;
    }

    /**
     * Register the given mapping.
     * <p>This method may be invoked at runtime after initialization has completed.
     * @param mapping the mapping for the handler method
     * @param handler the handler
     * @param method the method
     */
    public void registerMapping(CommandMappingInfo mapping, Object handler, Method method) {
        this.mappingRegistry.register(mapping, handler, method);
    }

    /**
     * Un-register the given mapping.
     * <p>This method may be invoked at runtime after initialization has completed.
     * @param mapping the mapping to unregister
     */
    public void unregisterMapping(CommandMappingInfo mapping) {
        this.mappingRegistry.unregister(mapping);
    }


    // Handler method detection

    /**
     * Detects handler methods at initialization.
     */
    @Override
    public void afterPropertiesSet() {
        initHandlerMethods();
    }

    /**
     * Scan beans in the ApplicationContext, detect and register handler methods.
     * @see #isHandler(Class)
     * @see #getMappingForMethod(Method, Class)
     * @see #handlerMethodsInitialized(Map)
     */
    protected void initHandlerMethods() {
        if (logger.isDebugEnabled()) {
            logger.debug("Looking for request mappings in application context: " + getApplicationContext());
        }
        String[] beanNames = (this.detectHandlerMethodsInAncestorContexts ?
                BeanFactoryUtils.beanNamesForTypeIncludingAncestors(getApplicationContext(), Object.class) :
                getApplicationContext().getBeanNamesForType(Object.class));

        for (String beanName : beanNames) {
            if (!beanName.startsWith(SCOPED_TARGET_NAME_PREFIX)) {
                Class<?> beanType = null;
                try {
                    beanType = getApplicationContext().getType(beanName);
                }
                catch (Throwable ex) {
                    // An unresolvable bean type, probably from a lazy bean - let's ignore it.
                    if (logger.isDebugEnabled()) {
                        logger.debug("Could not resolve target class for bean with name '" + beanName + "'", ex);
                    }
                }
                if (beanType != null && isHandler(beanType)) {
                    detectHandlerMethods(beanName);
                }
            }
        }
        handlerMethodsInitialized(getHandlerMethods());
    }

    /**
     * Look for handler methods in a handler.
     * @param handler the bean name of a handler or a handler instance
     */
    protected void detectHandlerMethods(final Object handler) {
        Class<?> handlerType = (handler instanceof String ?
                getApplicationContext().getType((String) handler) : handler.getClass());
        final Class<?> userType = ClassUtils.getUserClass(handlerType);

        Map<Method, CommandMappingInfo> methods = MethodIntrospector.selectMethods(userType,
                new MethodIntrospector.MetadataLookup<CommandMappingInfo>() {
                    @Override
                    public CommandMappingInfo inspect(Method method) {
                        return getMappingForMethod(method, userType);
                    }
                });

        if (logger.isDebugEnabled()) {
            logger.debug(methods.size() + " request handler methods found on " + userType + ": " + methods);
        }
        for (Map.Entry<Method, CommandMappingInfo> entry : methods.entrySet()) {
            Method invocableMethod = AopUtils.selectInvocableMethod(entry.getKey(), userType);
            CommandMappingInfo mapping = entry.getValue();
            registerHandlerMethod(handler, invocableMethod, mapping);
        }
    }

    /**
     * Register a handler method and its unique mapping. Invoked at startup for
     * each detected handler method.
     * @param handler the bean name of the handler or the handler instance
     * @param method the method to register
     * @param mapping the mapping conditions associated with the handler method
     * @throws IllegalStateException if another method was already registered
     * under the same mapping
     */
    protected void registerHandlerMethod(Object handler, Method method, CommandMappingInfo mapping) {
        this.mappingRegistry.register(mapping, handler, method);
    }

    /**
     * Create the HandlerMethod instance.
     * @param handler either a bean name or an actual handler instance
     * @param method the target method
     * @return the created HandlerMethod
     */
    protected HandlerMethod createHandlerMethod(Object handler, Method method) {
        HandlerMethod handlerMethod;
        if (handler instanceof String) {
            String beanName = (String) handler;
            handlerMethod = new HandlerMethod(beanName,
                    getApplicationContext().getAutowireCapableBeanFactory(), method);
        }
        else {
            handlerMethod = new HandlerMethod(handler, method);
        }
        return handlerMethod;
    }

    /**
     * Invoked after all handler methods have been detected.
     * @param handlerMethods a read-only map with handler methods and mappings.
     */
    protected void handlerMethodsInitialized(Map<CommandMappingInfo, HandlerMethod> handlerMethods) {
    }


    // Handler method lookup

    /**
     * Look up a handler method for the given request.
     */
    protected HandlerMethod getHandlerInternal(Command command) throws Exception {

        if (logger.isDebugEnabled()) {
            logger.debug("Looking up handler method for command " + command);
        }
        this.mappingRegistry.acquireReadLock();
        try {
            HandlerMethod handlerMethod = lookupHandlerMethod(command);
            if (logger.isDebugEnabled()) {
                if (handlerMethod != null) {
                    logger.debug("Returning handler method [" + handlerMethod + "]");
                }
                else {
                    logger.debug("Did not find handler method for [" + command + "]");
                }
            }
            return (handlerMethod != null ? handlerMethod.createWithResolvedBean() : null);
        }
        finally {
            this.mappingRegistry.releaseReadLock();
        }
    }

    protected HandlerMethod lookupHandlerMethod(Command command) throws Exception {

        Map<CommandMappingInfo, MappingRegistration> mappings = getMappingRegistry().getMappings();

        List<MappingRegistration> founds = new LinkedList<>();
        for(Map.Entry<CommandMappingInfo, MappingRegistration> m:mappings.entrySet()) {
            if (matches(m.getValue(), command)) {
                founds.add(m.getValue());
            }
        }

        return founds.get(0).getHandlerMethod();
    }

    protected boolean isHandler(Class<?> beanType) {
        return AnnotatedElementUtils.hasAnnotation(beanType, CommandHandler.class);
    }

    protected CommandMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        CommandMappingInfo info = createRequestMappingInfo(method);
        return info;
    }

    private CommandMappingInfo createRequestMappingInfo(AnnotatedElement element) {
        Subscribe subscribe = AnnotatedElementUtils.findMergedAnnotation(element, Subscribe.class);
        return (subscribe != null ? createRequestMappingInfo(subscribe) : null);
    }

    protected CommandMappingInfo createRequestMappingInfo(
            Subscribe subscribe) {

        CommandMappingInfo commandMappingInfo = new CommandMappingInfo();
        commandMappingInfo.setCommandType(subscribe.value());
        return commandMappingInfo;
    }

    @Override
    public Object getHandler(Command command) throws Exception {
        return getHandlerInternal(command);
    }

    protected boolean matches(MappingRegistration mappingRegistration, Command command) {
        if (mappingRegistration.getMapping().getCommandType().isAssignableFrom(command.getClass())) {
            return true;
        }
        return false;
    }

    /**
     * A registry that maintains all mappings to handler methods, exposing methods
     * to perform lookups and providing concurrent access.
     *
     * <p>Package-private for testing purposes.
     */
    class MappingRegistry {

        private final Map<CommandMappingInfo, MappingRegistration> mappings = new LinkedHashMap<CommandMappingInfo, MappingRegistration>();

        private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        /**
         * Return all mappings and handler methods. Not thread-safe.
         * @see #acquireReadLock()
         */
        public Map<CommandMappingInfo, MappingRegistration> getMappings() {
            return this.mappings;
        }

        /**
         * Acquire the read lock when using getMappings and getMappingsByUrl.
         */
        public void acquireReadLock() {
            this.readWriteLock.readLock().lock();
        }

        /**
         * Release the read lock after using getMappings and getMappingsByUrl.
         */
        public void releaseReadLock() {
            this.readWriteLock.readLock().unlock();
        }

        public void register(CommandMappingInfo mapping, Object handler, Method method) {
            this.readWriteLock.writeLock().lock();
            try {
                HandlerMethod handlerMethod = createHandlerMethod(handler, method);
                assertUniqueMethodMapping(handlerMethod, mapping);

                if (logger.isInfoEnabled()) {
                    logger.info("Mapped \"" + mapping + "\" onto " + handlerMethod);
                }
                MappingRegistration reg = new MappingRegistration(mapping, handlerMethod);
                this.mappings.put(mapping, reg);

            }
            finally {
                this.readWriteLock.writeLock().unlock();
            }
        }

        private void assertUniqueMethodMapping(HandlerMethod newHandlerMethod, CommandMappingInfo mapping) {
            MappingRegistration mappingRegistration = this.mappings.get(mapping);
            if (mappingRegistration != null && !mappingRegistration.equals(newHandlerMethod)) {
                throw new IllegalStateException(
                        "Ambiguous mapping. Cannot map '" +	newHandlerMethod.getBean() + "' method \n" +
                                newHandlerMethod + "\nto " + mapping + ": There is already '" +
                                mappingRegistration.handlerMethod.getBean() + "' bean method\n" + mappingRegistration + " mapped.");
            }
        }

        public void unregister(CommandMappingInfo mapping) {
            this.readWriteLock.writeLock().lock();
            try {
                this.mappings.remove(mapping);
            } finally {
                this.readWriteLock.writeLock().unlock();
            }
        }

    }

    private static class MappingRegistration {

        private final CommandMappingInfo mapping;

        private final HandlerMethod handlerMethod;


        public MappingRegistration(CommandMappingInfo mapping, HandlerMethod handlerMethod) {
            Assert.notNull(mapping);
            Assert.notNull(handlerMethod);
            this.mapping = mapping;
            this.handlerMethod = handlerMethod;
        }

        public CommandMappingInfo getMapping() {
            return this.mapping;
        }

        public HandlerMethod getHandlerMethod() {
            return this.handlerMethod;
        }

    }

}
