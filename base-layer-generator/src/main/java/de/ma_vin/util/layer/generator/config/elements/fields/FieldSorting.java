package de.ma_vin.util.layer.generator.config.elements.fields;


import de.ma_vin.util.layer.generator.config.IConfigLog;
import de.ma_vin.util.layer.generator.config.elements.Index;
import lombok.Data;

import jakarta.xml.bind.annotation.XmlTransient;

/**
 * Class to combine a field with som sorting direction for {@link Index}
 * <br>
 * SuppressWarnings("java:S1068"): getter and Setter are provided by lombok
 */
@XmlTransient
@Data
@SuppressWarnings("java:S1068")
public class FieldSorting implements IConfigLog {
    private Field field;
    private boolean isAscending = true;
}
