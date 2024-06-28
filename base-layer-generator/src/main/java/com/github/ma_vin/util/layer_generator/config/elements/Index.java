package com.github.ma_vin.util.layer_generator.config.elements;

import static com.github.ma_vin.util.layer_generator.config.ConfigElementsUtil.validateRequired;

import com.github.ma_vin.util.layer_generator.config.elements.fields.FieldSorting;
import com.github.ma_vin.util.layer_generator.config.IConfigLog;
import lombok.Data;

import jakarta.xml.bind.annotation.*;
import java.util.List;

/**
 * Description of an index which will be used at table annotation of data access objects
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
@Data
public class Index implements IConfigLog {
    /**
     * Name of the index
     */
    @XmlAttribute(required = true)
    private String indexName;

    /**
     * Indicator if this is an unique index
     */
    @XmlAttribute
    private Boolean isUnique = Boolean.FALSE;

    /**
     * List of field names which define columns of the index.
     */
    @XmlAttribute(required = true)
    private String fieldList;

    /**
     * resolved entities from the {@code columnList}
     */
    @XmlTransient
    List<FieldSorting> fields;

    /**
     * Checks whether the index has valid properties or not
     *
     * @param messages List where to add messages
     * @return {@code true} if the index is valid
     */
    public boolean isValid(List<String> messages) {
        return validateRequired(indexName, messages, "indexName")
                && validateRequired(fieldList, messages, "fieldList");
    }
}
