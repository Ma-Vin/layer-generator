package de.ma_vin.util.layer.generator.config.elements.fields;

import de.ma_vin.util.layer.generator.config.elements.Models;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
@Data
@EqualsAndHashCode(callSuper = true)
public class VersionField extends AbstractBasicField {

    public Field getAsField() {
        Field result = new Field();

        result.copyValues(this);
        result.setModels(Models.DTO);

        return result;
    }
}
