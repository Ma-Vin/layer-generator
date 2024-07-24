package com.github.ma_vin.util.layer_generator.sources;

import lombok.Data;

import java.util.Set;

/**
 * A representation of a generic
 */
@Data
public class Generic implements IComparableWithText<Generic> {
    private String genericName;
    private String extension;

    /**
     * Constructor
     *
     * @param genericName name of the generic
     */
    public Generic(String genericName) {
        this(genericName, null);
    }

    /**
     * Constructor
     *
     * @param genericName name of the generic
     * @param extension   class which is extended by this generic
     */
    public Generic(String genericName, String extension) {
        this.genericName = genericName;
        this.extension = extension;
    }

    /**
     * Determines the source code representation of this object
     *
     * @return the textual representation
     */
    public String getText() {
        return extension != null ? String.format("%s extends %s", genericName, extension) : genericName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("java:S1210")
    public int compareTo(Generic o) {
        return getText().compareTo(o.getText());
    }

    /**
     * Determines the source code representation of a set of {@link Generic}s
     *
     * @param generics set of generics whose text is asked for
     * @return the textual representation
     */
    public static String getText(Set<Generic> generics) {
        if (generics.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        generics.forEach(g -> {
            sb.append(g.getText());
            sb.append(", ");
        });
        return sb.substring(0, sb.length() - 2) + ">";
    }
}
