package org.destinyshine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jianyu.liu@hnlark.com on 2016/7/6.
 *
 * @author jianyu.liu@hnlark.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandHandler {

    Class<? extends Command> value();

}
