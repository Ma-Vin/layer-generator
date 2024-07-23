package com.github.ma_vin.util.layer_generator.annotations.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to identify generated data access objects
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface BaseDao {
    /**
     * @return The name of package for data access objects
     */
    String value();
}
