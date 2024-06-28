package com.github.ma_vin.util.layer_generator.config.elements;

import static com.github.ma_vin.util.layer_generator.config.ConfigElementsUtil.*;

import com.github.ma_vin.util.layer_generator.config.IConfigLog;
import com.github.ma_vin.util.layer_generator.logging.ILogWrapper;
import lombok.Data;
import lombok.ToString;

import jakarta.xml.bind.annotation.*;
import java.util.List;


/**
 * Configuration of the model generator
 */
@XmlRootElement(namespace = "de.ma_vin.util.gen.model")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
@Data
@ToString(exclude = {"groupings", "entities"})
public class Config implements IConfigLog {

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
    @XmlTransient
    private boolean useIdGenerator;

    /**
     * The package of an id generator
     */
    @XmlElement(required = true)
    private String idGeneratorPackage;

    /**
     * The class of an id generator, which is used to transform the database id to an identification with some prefix.
     * The prefix makes it easier to classy the identification to a concrete type of model object
     * <br>
     * Two static function are to provide:
     * <ul>
     *     <li>public static String generateIdentification(Long id, String prefix)</li>
     *     <li> public static Long generateId(String identification, String prefix)</li>
     * </ul>
     */
    @XmlElement(required = true)
    private String idGeneratorClass;

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

    public boolean isValid(List<String> messages) {
        return validateRequired(basePackage, messages, "basePackage")
                && validateRequired(dtoPackage, messages, "dtoPackage")
                && validateRequired(domainPackage, messages, "domainPackage")
                && validateRequired(daoPackage, messages, "daoPackage")
                && (groupings == null || groupings.stream().allMatch(g -> g.isValid(messages))
                && (entities == null || entities.stream().allMatch(e -> e.isValid(messages))))
                && ((idGeneratorPackage == null && idGeneratorClass == null)
                || (validateRequired(idGeneratorPackage, messages, "idGeneratorPackage") && validateRequired(idGeneratorClass, messages, "idGeneratorClass")));
    }

    /**
     * Logs the actual state of the configuration
     *
     * @param logger the logger to use
     */
    public void logConfig(ILogWrapper logger) {
        if (!logger.isDebugEnabled()) {
            return;
        }
        logConfigElement(this, logger, 0);
    }
}
