package de.ma_vin.util.layer.generator.config.elements.references;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VersionReference extends AbstractBasicReference {

    public Reference getAsReference() {
        Reference result = new Reference();

        result.copyValues(this);
        result.setIsList(false);

        return result;
    }
}
