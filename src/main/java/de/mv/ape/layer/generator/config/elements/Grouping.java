package de.mv.ape.layer.generator.config.elements;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

import static de.mv.ape.layer.generator.config.ValidationUtil.validateRequired;

/**
 * Grouping of entities in their own package
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.mv.ape.gen.model")
@Data
public class Grouping {

    /**
     * Extension of the grouping package
     */
    @XmlAttribute(required = true)
    private String groupingPackage;

    /**
     * Entities which will be used to generate domain objects, dto or dao.
     */
    @XmlElementWrapper
    @XmlElement(name = "entity")
    private List<Entity> entities;

    public boolean isValid() {
        return validateRequired(groupingPackage) && (entities == null || entities.stream().allMatch(Entity::isValid));
    }
}
