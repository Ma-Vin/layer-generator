package de.ma_vin.util.layer.generator.config.elements.fields;

import de.ma_vin.util.layer.generator.config.IConfigLog;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static de.ma_vin.util.layer.generator.config.ConfigElementsUtil.validateNonRequired;
import static de.ma_vin.util.layer.generator.config.ConfigElementsUtil.validateRequired;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
// getter needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class AbstractBasicField implements IConfigLog {
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
    @XmlAttribute
    private String typePackage;

    /**
     * indication if the field is an enum or not
     */
    @XmlAttribute
    private Boolean isTypeEnum = Boolean.FALSE;

    /**
     * Short description of the attribute
     */
    @XmlAttribute
    private String shortDescription;

    /**
     * Description of the attribute
     */
    private String description;


    public boolean isValid(List<String> messages) {
        return validateRequired(fieldName, messages, "fieldName")
                && validateRequired(type, messages, "type")
                && validateNonRequired(shortDescription, messages, "shortDescription")
                && validateNonRequired(description, messages, "description")
                && validateNonRequired(typePackage, messages, "typePackage");
    }


    public String getFieldName() {
        return fieldName;
    }

    public String getType() {
        return type;
    }

    public String getTypePackage() {
        return typePackage;
    }

    public Boolean getIsTypeEnum() {
        return isTypeEnum;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Sets all known fields given by another class.
     *
     * @param other the source where to get the values from
     */
    protected void copyValues(AbstractBasicField other) {
        fieldName = other.getFieldName();
        type = other.getType();
        typePackage = other.getTypePackage();
        isTypeEnum = other.getIsTypeEnum();
        shortDescription = other.getShortDescription();
        description = other.getDescription();
    }
}
