package com.github.ma_vin.util.layer_generator.builder;

import lombok.Getter;

@Getter
public enum MapperType {
    ACCESS("AccessMapperFactory"), TRANSPORT("TransportMapperFactory");
    private String factoryClassName;

    MapperType(String factoryClassName) {
        this.factoryClassName = factoryClassName;
    }
}