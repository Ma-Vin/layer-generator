package com.github.ma_vin.util.layer_generator.config.elements;

import static com.github.ma_vin.util.layer_generator.config.ConfigElementsUtil.*;

import com.github.ma_vin.util.layer_generator.config.IConfigLog;
import lombok.Data;
import lombok.ToString;

import jakarta.xml.bind.annotation.*;

import java.util.List;

/**
 * Grouping of entities in their own package
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "com.github.ma_vin.util.layer_generator.gen.model")
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSimpleLogName() {
        return groupingPackage;
    }
}
