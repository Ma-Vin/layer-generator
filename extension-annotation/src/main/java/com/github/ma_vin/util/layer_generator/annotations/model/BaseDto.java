package com.github.ma_vin.util.layer_generator.annotations.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface BaseDto {
    /**
     * @return The name of package for data transport objects
     */
    String value();
}
