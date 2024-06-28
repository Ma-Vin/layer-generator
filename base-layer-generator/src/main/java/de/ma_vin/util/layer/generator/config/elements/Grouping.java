package de.ma_vin.util.layer.generator.config.elements;

import static de.ma_vin.util.layer.generator.config.ConfigElementsUtil.*;

import de.ma_vin.util.layer.generator.config.IConfigLog;
import lombok.Data;
import lombok.ToString;

import jakarta.xml.bind.annotation.*;

import java.util.List;

/**
 * Grouping of entities in their own package
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
@Data
@ToString(exclude = {"entities"})
public class Grouping implements IConfigLog {

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

    public boolean isValid(List<String> messages) {
        return validateRequired(groupingPackage, messages, "groupingPackage")
                && (entities == null || entities.stream().allMatch(e -> e.isValid(messages)));
    }

    @Override
    public String getSimpleLogName() {
        return groupingPackage;
    }
}
