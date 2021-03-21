package de.mv.ape.layer.generator.sources;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Parameter implements IComparableWithText<Parameter> {
    private String parameterType;
    private String parameterName;

    public String getText() {
        return String.format("%s %s", parameterType, parameterName);
    }

    @Override
    @SuppressWarnings("java:S1210")
    public int compareTo(Parameter o) {
        return parameterName.compareTo(o.parameterName);
    }
}
