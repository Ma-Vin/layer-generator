package de.ma_vin.util.layer.generator.sources;

import lombok.Data;

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
}
