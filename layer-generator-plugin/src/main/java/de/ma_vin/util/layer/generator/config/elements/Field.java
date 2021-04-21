package de.ma_vin.util.layer.generator.config.elements;

import static de.ma_vin.util.layer.generator.config.ConfigElementsUtil.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Describes the attribute of some entity
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
@Data
@EqualsAndHashCode(exclude = {"parentEntity"})
@ToString(exclude = {"parentEntity"})
public class Field {
    /**
     * Name of the attribute
     */
    @XmlAttribute(required = true)
    private String fieldName;

    /**
     * Type of the attribute
     */
    @XmlAttribute(required = true)
    private String type;

    /**
     * package of the type if necessary
     */
    @XmlAttribute
    private String typePackage;

    /**
     * package of the type if necessary
     */
    @XmlAttribute
    private Boolean isTypeEnum = Boolean.FALSE;

    /**
     * Description of the attribute
     */
    private String description;

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

    public boolean isValid(List<String> messages) {
        return validateRequired(fieldName, messages, "fieldName")
                && validateRequired(type, messages, "type")
                && validateNonRequired(description, messages, "description")
                && validateNonRequired(typePackage, messages, "typePackage")
                && (daoInfo == null || daoInfo.isValid());
    }

    public Models getModels() {
        return models != null ? models : Models.DOMAIN_DAO_DTO;
    }
}
