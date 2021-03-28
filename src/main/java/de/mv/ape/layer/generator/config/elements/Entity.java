package de.mv.ape.layer.generator.config.elements;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.List;

import static de.mv.ape.layer.generator.config.ValidationUtil.validateNonRequired;
import static de.mv.ape.layer.generator.config.ValidationUtil.validateRequired;

/**
 * Description of an entity which will be used to generate domain object, dto or dao
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.mv.ape.gen.model")
@Data
@EqualsAndHashCode(exclude = {"references", "parentRefs"})
@ToString(exclude = {"references", "parentRefs"})
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
    private boolean isAbstract;

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
    private Grouping grouping;

    public boolean isValid() {
        return validateRequired(baseName) && models != null && validateNonRequired(description)
                && validateNonRequired(identificationPrefix) && validateNonRequired(parent)
                && (fields == null || fields.stream().allMatch(Field::isValid))
                && (references == null || references.stream().allMatch(Reference::isValid));
    }
}
