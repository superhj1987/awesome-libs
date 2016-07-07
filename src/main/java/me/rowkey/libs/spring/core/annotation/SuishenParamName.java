package me.rowkey.libs.spring.core.annotation;

import java.lang.annotation.*;

/**
 * Author: Bryant Hang
 * Date: 15/1/14
 * Time: 下午5:31
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SuishenParamName {

    /**
     * The name of the request parameter to bind to.
     */
    String value();

}
