package de.ma_vin.util.layer.generator.config.elements;

import de.ma_vin.util.layer.generator.config.ValidationUtil;
import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * Describes the attribute of some entity
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
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

    public boolean isValid() {
        return ValidationUtil.validateRequired(fieldName) && ValidationUtil.validateRequired(type) && ValidationUtil.validateNonRequired(description) && ValidationUtil.validateNonRequired(typePackage) && (daoInfo == null || daoInfo.isValid());
    }
}
