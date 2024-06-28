package com.github.ma_vin.util.layer_generator.config.elements;

import static com.github.ma_vin.util.layer_generator.config.ConfigElementsUtil.*;

import com.github.ma_vin.util.layer_generator.config.IConfigLog;
import lombok.Data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Can be used to override default jpa column values
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
@Data
public class DaoInfo implements IConfigLog {

    /**
     * different column name compared to fieldName
     */
    @XmlAttribute
    private String columnName;

    /**
     * (Optional) Whether the database column is nullable.
     */
    @XmlAttribute
    private Boolean nullable;
    /**
     * (Optional) The column length. (Applies only if a
     * string-valued column is used.)
     */
    @XmlAttribute
    private Integer length;
    /**
     * (Optional) The precision for a decimal (exact numeric)
     * column. (Applies only if a decimal column is used.)
     * Value must be set by developer if used when generating
     * the DDL for the column.
     */
    @XmlAttribute
    private Integer precision;
    /**
     * (Optional) The scale for a decimal (exact numeric) column.
     * (Applies only if a decimal column is used.)
     */
    @XmlAttribute
    private Integer scale;

    /**
     * True if enum values should be stored by text and not by id
     */
    @XmlAttribute
    private Boolean useEnumText;

    /**
     * (Optional) The SQL fragment that is used when generating the DDL for the column.
     */
    @XmlAttribute
    private String columnDefinition;

    /**
     * Specifies that a persistent property or field should be persisted as a large object to a database-supported large object type.
     */
    @XmlAttribute
    private Boolean isLobType;

    public boolean isValid(List<String> messages) {
        return validateNonRequired(columnName, messages, "columnName")
                && validateNonRequired(columnDefinition, messages, "columnDefinition");
    }
}
