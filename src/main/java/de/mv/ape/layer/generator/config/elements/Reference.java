package de.mv.ape.layer.generator.config.elements;

import lombok.Data;

import javax.xml.bind.annotation.*;

import static de.mv.ape.layer.generator.config.ValidationUtil.validateRequired;

/**
 * Describes the reference to som Entity
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.mv.ape.gen.model")
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
        return validateRequired(targetEntity) && validateRequired(referenceName);
    }
}
