package com.github.ma_vin.util.layer_generator.annotations.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to identify extending data transport objects. The extended object has to declare a {@link BaseDto} annotation.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ExtendingDto {

}
