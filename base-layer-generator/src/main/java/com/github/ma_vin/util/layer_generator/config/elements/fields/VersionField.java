package com.github.ma_vin.util.layer_generator.config.elements.fields;

import com.github.ma_vin.util.layer_generator.config.elements.Models;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "com.github.ma_vin.util.layer_generator.gen.model")
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
