package de.ma_vin.util.layer.generator.config.elements;

import static de.ma_vin.util.layer.generator.config.ConfigElementsUtil.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.List;


/**
 * Configuration of the model generator
 */
@XmlRootElement(namespace = "de.ma_vin.util.gen.model")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
@Data
@EqualsAndHashCode(exclude = {"groupings", "entities"})
@ToString(exclude = {"groupings", "entities"})
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
     * Entities which will be used to generate domain objects, dto or dao.
     */
    @XmlElementWrapper
    @XmlElement(name = "entity")
    private List<Entity> entities;

    /**
     * Groupings of entities
     */
    @XmlElementWrapper
    @XmlElement(name = "grouping")
    private List<Grouping> groupings;

    public boolean isValid() {
        return validateRequired(basePackage) && validateRequired(dtoPackage) && validateRequired(domainPackage) && validateRequired(daoPackage)
                && (groupings == null || groupings.stream().allMatch(Grouping::isValid))
                && (entities == null || entities.stream().allMatch(Entity::isValid));
    }
}
