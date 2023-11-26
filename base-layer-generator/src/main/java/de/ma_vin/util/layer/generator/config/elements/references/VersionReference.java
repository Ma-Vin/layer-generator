package de.ma_vin.util.layer.generator.config.elements.references;

import de.ma_vin.util.layer.generator.config.elements.Version;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

import static de.ma_vin.util.layer.generator.config.ConfigElementsUtil.validateNonRequired;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "de.ma_vin.util.gen.model")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VersionReference extends AbstractBasicReference {

    /**
     * Possibility to references another version than the owner {@link Version#getVersionId()} (if some target entity exists with a version with the same id)
     * or the non version entity (if not any target entity exists with a version)
     */
    private String divergentTargetVersion;

    public Reference getAsReference() {
        Reference result = new Reference();

        result.copyValues(this);
        result.setIsList(false);

        return result;
    }

    @Override
    public boolean isValid(List<String> messages) {
        return super.isValid(messages)
                && validateNonRequired(divergentTargetVersion, messages, "divergentTargetVersion");
    }

    // needed by jaxb2-maven-plugin:schemagen generated classes - it is not compatible with lombok
    public String getDivergentTargetVersion() {
        return divergentTargetVersion;
    }
}
