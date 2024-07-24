package com.github.ma_vin.util.layer_generator.sources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Parameter at {@link Method}s or {@link com.github.ma_vin.util.layer_generator.sources.Interface.MethodDeclaration}s
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Parameter implements IComparableWithText<Parameter> {
    private String parameterType;
    private String parameterName;

    /**
     * textual representation of this parameter
     *
     * @return textual representation
     */
    public String getText() {
        return String.format("%s %s", parameterType, parameterName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("java:S1210")
    public int compareTo(Parameter o) {
        int res = parameterName.compareTo(o.parameterName);
        if (res == 0) {
            res = parameterType.compareTo(o.parameterType);
        }
        return res;
    }
}
