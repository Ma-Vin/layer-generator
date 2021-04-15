package de.ma_vin.util.layer.generator.builder;

import lombok.Data;

/**
 * Value container for generation information
 * <br>
 * SuppressWarnings("java:S1068"): the private fields are used by lombok
 */
@Data
@SuppressWarnings("java:S1068")
public class GenerateInformation {
    private String modelPackage;
    private String className;
    private String packageName;
    private String baseClassName;
    private String basePackageName;
}
