package de.ma_vin.util.layer.generator.config.elements;

import de.ma_vin.util.layer.generator.config.ValidationUtil;
import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * Describes the reference to som Entity
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
@Data
public class Reference {

    /**
     * Name of the reference
     */
    @XmlAttribute(required = true)
    private String referenceName;

    /**
     * The baseName of the entity where to point at
     */
    @XmlAttribute(required = true)
    private String targetEntity;

    @XmlTransient
    private Entity realTargetEntity;

    @XmlTransient
    private Entity parent;

    /**
     * {@code true} if the parent should also be the parent at database. Otherwise some connection table will be generated
     */
    @XmlAttribute
    private boolean isOwner;

    /**
     * Indicator if a one to one relation or an one to many relation exists
     */
    @XmlAttribute
    private boolean isList;

    public boolean isValid() {
        return ValidationUtil.validateRequired(targetEntity) && ValidationUtil.validateRequired(referenceName);
    }
}
