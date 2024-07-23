package com.github.ma_vin.util.layer_generator.builder;

import lombok.Getter;

/**
 * Enum with possible model types
 */
@Getter
public enum ModelType {
    /**
     * Data access model type
     */
    DAO("DaoObjectFactory"),
    /**
     * Domain model type
     */
    DOMAIN("DomainObjectFactory"),
    /**
     * Data transport model type
     */
    DTO("DtoObjectFactory");
    private String factoryClassName;

    ModelType(String factoryClassName) {
        this.factoryClassName = factoryClassName;
    }
}
