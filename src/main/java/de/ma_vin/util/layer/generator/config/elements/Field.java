package de.ma_vin.util.layer.generator.config.elements;

import static de.ma_vin.util.layer.generator.config.ConfigElementsUtil.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.xml.bind.annotation.*;

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
    @XmlAttribute()
    private String typePackage;

    /**
     * package of the type if necessary
     */
    @XmlAttribute()
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

    @XmlTransient
    private Entity parentEntity;

    public boolean isValid() {
        return validateRequired(fieldName) && validateRequired(type) && validateNonRequired(description)
                && validateNonRequired(typePackage) && (daoInfo == null || daoInfo.isValid());
    }
}
