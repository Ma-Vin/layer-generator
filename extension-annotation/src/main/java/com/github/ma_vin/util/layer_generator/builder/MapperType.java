package com.github.ma_vin.util.layer_generator.builder;

import lombok.Getter;

/**
 * Enum with possible model mappers
 */
@Getter
public enum MapperType {
    /**
     * Access mapper to map between data access and domain model
     */
    ACCESS("AccessMapperFactory"),
    /**
     * Transport mapper to map between data transport and domain model
     */
    TRANSPORT("TransportMapperFactory");
    private String factoryClassName;

    MapperType(String factoryClassName) {
        this.factoryClassName = factoryClassName;
    }
}