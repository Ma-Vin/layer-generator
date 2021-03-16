package de.mv.ape.layer.generator.config.elements;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.mv.ape.gen.model")
@XmlEnum
@Getter
public enum Models {
    DOMAIN(false, false, true), DAO(true, false, false)
    , DTO(false, true, false), DOMAIN_DAO(true, false, true)
    , DOMAIN_DTO(false, true, true), DAO_DTO(true, true, false)
    , DOMAIN_DAO_DTO(true, true, true);

    private boolean isDao;
    private boolean isDto;
    private boolean isDomain;

    Models(boolean isDao, boolean isDto, boolean isDomain) {
        this.isDao = isDao;
        this.isDto = isDto;
        this.isDomain = isDomain;
    }
}
