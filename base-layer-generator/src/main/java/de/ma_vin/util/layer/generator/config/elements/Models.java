package de.ma_vin.util.layer.generator.config.elements;

import lombok.Getter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
@XmlEnum
@Getter
public enum Models {
    DOMAIN(false, false, true), DAO(true, false, false), DTO(false, true, false), DOMAIN_DAO(true, false, true), DOMAIN_DTO(false, true, true), DAO_DTO(true, true, false), DOMAIN_DAO_DTO(true, true, true);

    private boolean isDao;
    private boolean isDto;
    private boolean isDomain;

    Models(boolean isDao, boolean isDto, boolean isDomain) {
        this.isDao = isDao;
        this.isDto = isDto;
        this.isDomain = isDomain;
    }

    /**
     * Checks whether the other model ist contained in the actual one
     *
     * @param other The model which should be contained
     * @return {@code true} if the {@code other} is also supported by {@code this}
     */
    public boolean includes(Models other) {
        return (!other.isDao || isDao) && (!other.isDto || isDto) && (!other.isDomain || isDomain);
    }
}
