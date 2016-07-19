package org.destinyshine;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author destinyliu
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface CommandHandler {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any
     */
    String value() default "";

}

