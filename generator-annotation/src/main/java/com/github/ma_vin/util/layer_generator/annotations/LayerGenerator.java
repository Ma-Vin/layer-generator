package com.github.ma_vin.util.layer_generator.annotations;

import javax.tools.Diagnostic.Kind;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to activate and configure the layer generator
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface LayerGenerator {


    /**
     * Indicator whether to generate data transport objects or not. Default is {@code true}
     */
    boolean generateDto() default true;

    /**
     * Indicator whether to generate domain objects or not. Default is {@code true}
     */
    boolean generateDomain() default true;

    /**
     * Indicator whether to generate data access objects or not. Default is {@code true}
     */
    boolean generateDao() default true;

    /**
     * relative project directory,with respect to {@code target/classes}/{@link javax.tools.StandardLocation#CLASS_OUTPUT} , where to get model file from. Default is empty
     */
    String modelDefinitionDirectory() default "";

    /**
     * yaml which provides the model definition. Has to satisfy the config.xsd. Or equivalent json and xml format. Default ist {@code model.yaml}
     */
    String modelDefinitionFilename() default "model.yaml";


    /**
     * log level of processor. Allowed values are ERROR, WARNING, NOTE(info) or OTHER(debug), see also {@link javax.tools.Diagnostic.Kind}
     */
    Kind logLevel() default Kind.WARNING;

}
