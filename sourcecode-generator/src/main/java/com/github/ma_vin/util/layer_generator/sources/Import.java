package com.github.ma_vin.util.layer_generator.sources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * A representation of an import at a {@link Clazz}
 */
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Import extends AbstractGenerateLines implements Comparable<Import> {

    String importedClass;
    boolean isStatic;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> generate() {
        List<String> result = new ArrayList<>();

        result.add(String.format("import%s %s;", isStatic ? " static" : "", importedClass));

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("java:S1210")
    public int compareTo(Import o) {
        if (isStatic && !o.isStatic) {
            return -1;
        }
        if (!isStatic && o.isStatic) {
            return 1;
        }
        return importedClass.compareTo(o.importedClass);
    }
}
