package com.github.ma_vin.util.layer_generator.annotations.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to identify extending access mapper. The extended object has to declare a {@link BaseAccessMapper} annotation.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ExtendingAccessMapper {

}
