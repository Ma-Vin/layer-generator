package com.github.ma_vin.util.layer_generator.annotations.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Annotation to identify generated access mapper, which maps objects between data access and domain model.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface BaseAccessMapper {

}
