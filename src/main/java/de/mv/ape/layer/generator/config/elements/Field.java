package de.mv.ape.layer.generator.config.elements;

import static de.mv.ape.layer.generator.config.ValidationUtil.*;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * Describes the attribute of some entity
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.mv.ape.gen.model")
@Data
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
    private String typePackage;

    /**
     * package of the type if necessary
     */
    private boolean isTypeEnum;

    /**
     * Description of the attribute
     */
    private String description;

    /**
     * For which object is this field relevant
     */
    @XmlAttribute()
    private Models models;

    /**
     * (Optional) additional information for database.
     */
    private DaoInfo daoInfo;

    public boolean isValid() {
        return validateRequired(fieldName) && validateRequired(type) && validateNonRequired(description) && validateNonRequired(typePackage) && (daoInfo == null || daoInfo.isValid());
    }
}
