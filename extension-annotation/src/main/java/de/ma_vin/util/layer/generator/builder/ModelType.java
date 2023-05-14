package de.ma_vin.util.layer.generator.builder;

import lombok.Getter;

@Getter
public enum ModelType {
    DAO("DaoObjectFactory"), DOMAIN("DomainObjectFactory"), DTO("DtoObjectFactory");
    private String factoryClassName;

    ModelType(String factoryClassName) {
        this.factoryClassName = factoryClassName;
    }
}
