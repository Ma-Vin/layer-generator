package de.ma_vin.util.layer.generator.config.elements;

import static de.ma_vin.util.layer.generator.config.ConfigElementsUtil.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Description of an entity which will be used to generate domain object, dto or dao
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
@Data
@EqualsAndHashCode(exclude = {"references", "parentRefs", "fields", "realParent"})
@ToString(exclude = {"references", "parentRefs", "fields", "realParent"})
public class Entity {

    /**
     * Base name of the objects, which will be extended by some postfix for dto or dao
     */
    @XmlAttribute(required = true)
    private String baseName;

    /**
     * Which objects should be generated
     */
    @XmlAttribute(required = true)
    private Models models;

    /**
     * Description of the attribute
     */
    @XmlAttribute
    private String description;

    /**
     * Prefix which will be added in front of dto and domain id
     */
    @XmlAttribute
    private String identificationPrefix;

    /**
     * super entity
     */
    @XmlAttribute
    private String parent;

    /**
     * indication if the generated java class should be abstract
     */
    @XmlAttribute
    private Boolean isAbstract = Boolean.FALSE;

    /**
     * Attributes of the entity
     */
    @XmlElementWrapper
    @XmlElement(name = "field")
    private List<Field> fields;

    /**
     * References to other entities
     */
    @XmlElementWrapper
    @XmlElement(name = "reference")
    private List<Reference> references;

    @XmlTransient
    private List<Reference> parentRefs;

    @XmlTransient
    private Entity realParent;

    @XmlTransient
    private Grouping grouping;

    public boolean hasParent() {
        return parent != null && !parent.trim().isEmpty();
    }

    public boolean hasNoParent() {
        return !hasParent();
    }

    public boolean isValid() {
        return validateRequired(baseName) && models != null && validateNonRequired(description)
                && validateNonRequired(identificationPrefix) && validateNonRequired(parent)
                && (fields == null || fields.stream().allMatch(Field::isValid))
                && (references == null || (references.stream().allMatch(Reference::isValid) && Reference.isFilterFieldValid(references)));
    }

    public Models getModels() {
        return models != null ? models : Models.DOMAIN_DAO_DTO;
    }
}
