package de.mv.ape.layer.generator.config.elements;

import de.mv.ape.layer.generator.config.ValidationUtil;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;


/**
 * Configuration of the model generator
 */
@XmlRootElement(namespace = "de.mv.ape.gen.model")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.mv.ape.gen.model")
@Data
public class Config {

    /**
     * Basic package which will be used for generated java classes
     */
    @XmlElement(required = true)
    private String basePackage;

    /**
     * Extension of the basic package for dto objects
     */
    @XmlElement(required = true)
    private String dtoPackage;

    /**
     * Extension of the basic package for domain objects
     */
    @XmlElement(required = true)
    private String domainPackage;

    /**
     * Extension of the basic package for dao objects
     */
    @XmlElement(required = true)
    private String daoPackage;

    /**
     * if true the dao id will be transformed by the Id generator
     */
    @XmlElement(required = true)
    private boolean useIdGenerator;

    /**
     * Groupings of entities
     */
    @XmlElementWrapper
    @XmlElement(name = "grouping")
    private List<Grouping> groupings;

    /**
     * Entities which will be used to generate domain objects, dto or dao.
     */
    @XmlElementWrapper
    @XmlElement(name = "entity")
    private List<Entity> entities;

    public boolean isValid() {
        return ValidationUtil.validateRequired(basePackage) && ValidationUtil.validateRequired(dtoPackage) && ValidationUtil.validateRequired(domainPackage) && ValidationUtil.validateRequired(daoPackage)
                && (groupings == null || groupings.stream().allMatch(Grouping::isValid))
                && (entities == null || entities.stream().allMatch(Entity::isValid));
    }
}
