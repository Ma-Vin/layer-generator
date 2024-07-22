package com.github.ma_vin.util.layer_generator.config.elements.fields;


import com.github.ma_vin.util.layer_generator.config.elements.DaoInfo;
import com.github.ma_vin.util.layer_generator.config.elements.Entity;
import com.github.ma_vin.util.layer_generator.config.elements.Models;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.xml.bind.annotation.*;

import java.util.List;

/**
 * Describes the attribute of some entity
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "com.github.ma_vin.util.layer_generator.gen.model")
@Data
@EqualsAndHashCode(exclude = {"parentEntity"}, callSuper = true)
@ToString(exclude = {"parentEntity"})
public class Field extends AbstractBasicField {

    /**
     * For which object is this field relevant
     */
    @XmlAttribute
    private Models models;

    /**
     * (Optional) additional information for database.
     */
    private DaoInfo daoInfo;

    @XmlTransient
    private Entity parentEntity;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(List<String> messages) {
        return super.isValid(messages)
                && (daoInfo == null || daoInfo.isValid(messages));
    }

    public Models getModels() {
        return models != null ? models : Models.DOMAIN_DAO_DTO;
    }

    public void setModels(Models models) {
        this.models = models;
    }
}
