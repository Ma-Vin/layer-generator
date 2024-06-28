package com.github.ma_vin.util.layer_generator.config.elements.references;

import static com.github.ma_vin.util.layer_generator.config.ConfigElementsUtil.*;

import com.github.ma_vin.util.layer_generator.config.elements.fields.Field;
import com.github.ma_vin.util.layer_generator.config.elements.Entity;
import com.github.ma_vin.util.layer_generator.config.elements.fields.NonOwnerFilterField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.xml.bind.annotation.*;

import java.util.List;

/**
 * Describes the reference to som Entity
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
@Data
@EqualsAndHashCode(exclude = {"realFilterField", "parent"}, callSuper = true)
@ToString(exclude = {"realFilterField", "parent"}, callSuper = true)
public class Reference extends AbstractBasicReference {

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
    private Entity parent;

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

    /**
     * Filtering on non owner references whose target value differs from target entity values
     */
    private NonOwnerFilterField nonOwnerFilterField;

    @Override
    public boolean isValid(List<String> messages) {
        return super.isValid(messages)
                && validateNonRequired(filterField, messages, "filterField")
                && (filterField == null || validateNonRequired(filterFieldValue, messages, "filterFieldValue"))
                && (nonOwnerFilterField == null || (Boolean.FALSE.equals(isOwner) && nonOwnerFilterField.isValid(messages)));
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
                .filter(ref2 -> ref2.isList && ref.targetEntity.equals(ref2.targetEntity) && ref2.filterField == null && ref2.nonOwnerFilterField == null)
                .count() > 1);
        if (!result) {
            messages.add(String.format("There multiple reference from %s to the same target", entity));
        }
        return result;
    }

    /**
     * Checks whether a filter criteria for non owner exists
     *
     * @return {@code true} if there exists a filter
     */
    public boolean isConnectionFiltering() {
        return nonOwnerFilterField != null;
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

        result.copyValues(this);

        result.filterField = getFilterField();
        result.realFilterField = getRealFilterField();
        result.filterFieldValue = getFilterFieldValue();
        result.parent = getParent();
        result.isList = isList();
        result.isAggregated = isAggregated();
        result.isReverse = isReverse();
        result.nonOwnerFilterField =getNonOwnerFilterField();

        return result;
    }


    public boolean isList() {
        return isList;
    }

    public void setIsList(Boolean isList) {
        this.isList = isList;
    }

    public boolean isAggregated() {
        return isAggregated;
    }

    public boolean isReverse() {
        return isReverse;
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public String getFilterField() {
        return filterField;
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public Field getRealFilterField() {
        return realFilterField;
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public String getFilterFieldValue() {
        return filterFieldValue;
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public Entity getParent() {
        return parent;
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public NonOwnerFilterField getNonOwnerFilterField() {
        return nonOwnerFilterField;
    }
}
