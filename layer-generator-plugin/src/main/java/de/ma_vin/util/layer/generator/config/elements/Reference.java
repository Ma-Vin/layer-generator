package de.ma_vin.util.layer.generator.config.elements;

import static de.ma_vin.util.layer.generator.config.ConfigElementsUtil.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Describes the reference to som Entity
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
@Data
@EqualsAndHashCode(exclude = {"realFilterField", "realTargetEntity", "parent"})
@ToString(exclude = {"realFilterField", "realTargetEntity", "parent"})
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

    /**
     * Field of enum type to filter references from one entity to another multiple times
     */
    @XmlAttribute
    private String filterField;

    @XmlTransient
    private Field realFilterField;

    /**
     * Value which should be used for filtering.
     */
    @XmlAttribute
    private String filterFieldValue;

    @XmlTransient
    private Entity realTargetEntity;

    @XmlTransient
    private Entity parent;

    /**
     * {@code true} if the parent should also be the parent at database. Otherwise some connection table will be generated
     */
    @XmlAttribute
    private Boolean isOwner = Boolean.FALSE;

    /**
     * Indicator if a one to one relation or an one to many relation exists
     */
    @XmlAttribute
    private Boolean isList = Boolean.FALSE;

    /**
     * Indicator if this reference is created while aggregation and represents two or more references
     */
    @XmlTransient
    private boolean isAggregated;

    /**
     * Indicator if this reference is pointing to the parent
     */
    @XmlTransient
    private boolean isReverse;

    public boolean isValid(List<String> messages) {
        return validateRequired(targetEntity, messages, "targetEntity")
                && validateRequired(referenceName, messages, "referenceName")
                && validateNonRequired(filterField, messages, "filterField")
                && (filterField == null || validateNonRequired(filterFieldValue, messages, "filterFieldValue"));
    }

    /**
     * Util method since schemagen is called before getter will be created by lombok
     * If more than one references point to the same entity only one is allowed to have a {@code null} at {@link Reference#filterField}
     *
     * @param entity     The name of the owner entity
     * @param references references to check
     * @param messages   List where to add messages
     * @return {@code true} if valid
     */
    public static boolean isFilterFieldValid(String entity, List<Reference> references, List<String> messages) {
        boolean result = references.stream().filter(ref -> ref.isList).noneMatch(ref -> references.stream()
                .filter(ref2 -> ref2.isList && ref.targetEntity.equals(ref2.targetEntity) && ref2.filterField == null)
                .count() > 1);
        if (!result) {
            messages.add(String.format("There multiple reference from %s to the same target", entity));
        }
        return result;
    }

    /**
     * Copies the the references to a new object
     * This is just a workaround since schemagen is called before getter will be created by lombok ("cannot find symbol" errors)
     * and methods are to use to be able to call doCallRealMethod
     *
     * @return The copy
     */
    public Reference copy() {
        Reference result = new Reference();

        result.referenceName = getReferenceName();
        result.targetEntity = getTargetEntity();
        result.filterField = getFilterField();
        result.realFilterField = getRealFilterField();
        result.filterFieldValue = getFilterFieldValue();
        result.realTargetEntity = getRealTargetEntity();
        result.parent = getParent();
        result.isOwner = isOwner();
        result.isList = isList();
        result.isAggregated = isAggregated();
        result.isReverse = isReverse();

        return result;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public String getTargetEntity() {
        return targetEntity;
    }

    public String getFilterField() {
        return filterField;
    }

    public Field getRealFilterField() {
        return realFilterField;
    }

    public String getFilterFieldValue() {
        return filterFieldValue;
    }

    public Entity getRealTargetEntity() {
        return realTargetEntity;
    }

    public Entity getParent() {
        return parent;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public boolean isList() {
        return isList;
    }

    public boolean isAggregated() {
        return isAggregated;
    }

    public boolean isReverse() {
        return isReverse;
    }
}
