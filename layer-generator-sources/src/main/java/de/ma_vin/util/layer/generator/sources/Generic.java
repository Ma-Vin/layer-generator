package de.ma_vin.util.layer.generator.sources;

import lombok.Data;

import java.util.Set;

@Data
public class Generic implements IComparableWithText<Generic> {
    private String genericName;
    private String extension = null;

    public Generic(String genericName) {
        this.genericName = genericName;
    }

    public String getText() {
        return extension != null ? String.format("%s extends %s", genericName, extension) : genericName;
    }

    @Override
    @SuppressWarnings("java:S1210")
    public int compareTo(Generic o) {
        return getText().compareTo(o.getText());
    }

    public static String getText(Set<Generic> generics){
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
