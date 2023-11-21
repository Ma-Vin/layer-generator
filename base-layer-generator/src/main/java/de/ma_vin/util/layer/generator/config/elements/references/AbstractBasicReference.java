package de.ma_vin.util.layer.generator.config.elements.references;

import de.ma_vin.util.layer.generator.config.elements.Entity;
import jakarta.xml.bind.annotation.*;
import lombok.*;

import java.util.List;

import static de.ma_vin.util.layer.generator.config.ConfigElementsUtil.validateNonRequired;
import static de.ma_vin.util.layer.generator.config.ConfigElementsUtil.validateRequired;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
// getter needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
@NoArgsConstructor
@Setter
@EqualsAndHashCode(exclude = {"realTargetEntity"})
@ToString(exclude = {"realTargetEntity"})
public abstract class AbstractBasicReference {

    /**
     * Name of the reference
     */
    @XmlAttribute(required = true)
    protected String referenceName;

    /**
     * The baseName of the entity where to point at
     */
    @XmlAttribute(required = true)
    protected String targetEntity;

    /**
     * Short description of the reference
     */
    @XmlAttribute
    protected String shortDescription;


    @XmlTransient
    protected Entity realTargetEntity;

    /**
     * {@code true} if the parent should also be the parent at database. Otherwise some connection table will be generated
     */
    @XmlAttribute
    protected Boolean isOwner = Boolean.FALSE;


    public boolean isOwner() {
        return isOwner;
    }
    
    public Boolean getIsOwner() {
        return isOwner;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public String getTargetEntity() {
        return targetEntity;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public Entity getRealTargetEntity() {
        return realTargetEntity;
    }

    public boolean isValid(List<String> messages) {
        return validateRequired(targetEntity, messages, "targetEntity")
                && validateRequired(referenceName, messages, "referenceName")
                && validateNonRequired(shortDescription, messages, "shortDescription");
    }

    /**
     * Sets all known fields given by another class.
     *
     * @param other the source where to get the values from
     */
    protected void copyValues(AbstractBasicReference other) {
        referenceName = other.getReferenceName();
        targetEntity = other.getTargetEntity();
        shortDescription = other.getShortDescription();
        realTargetEntity = other.getRealTargetEntity();
        isOwner = other.isOwner();
    }

}
