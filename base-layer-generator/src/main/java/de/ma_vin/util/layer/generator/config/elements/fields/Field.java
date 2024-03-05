package de.ma_vin.util.layer.generator.config.elements.fields;


import de.ma_vin.util.layer.generator.config.elements.DaoInfo;
import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.Models;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.xml.bind.annotation.*;
import java.util.List;

/**
 * Describes the attribute of some entity
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
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
