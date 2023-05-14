package de.ma_vin.util.layer.generator.config.elements;

import static de.ma_vin.util.layer.generator.config.ConfigElementsUtil.validateRequired;

import lombok.Data;

import jakarta.xml.bind.annotation.*;
import java.util.List;

/**
 * Describes an filter criteria for non owner reference whose filter enum differs from target entity fields.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
@Data
public class NonOwnerFilterField {
    /**
     * Package of the filtering enum. Only in case of filtered reference, non ownership. Will be used at connection table.
     */
    @XmlAttribute
    private String filterFieldPackage;

    /**
     * Field of enum type to filter references from one entity to another multiple times
     */
    @XmlAttribute
    private String filterFieldType;

    /**
     * Value which should be used for filtering.
     */
    @XmlAttribute
    private String filterFieldValue;

    public String getFilterFieldName() {
        return "filter" + filterFieldType;
    }

    public boolean isValid(List<String> messages) {
        return validateRequired(filterFieldPackage, messages, "filterFieldPackage")
                && validateRequired(filterFieldType, messages, "filterFieldType")
                && validateRequired(filterFieldValue, messages, "filterFieldValue");
    }
}
