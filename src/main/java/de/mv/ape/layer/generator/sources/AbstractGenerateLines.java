package de.mv.ape.layer.generator.sources;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractGenerateLines {
    public static final String TAB = "\t";

    public abstract List<String> generate();

    public List<String> generate(int numTabs) {
        String tabs = getTabs(numTabs);
        return generate().stream().map(s -> s.trim().isEmpty() ? s : tabs + s).collect(Collectors.toList());
    }

    protected String getTabs(int numTabs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numTabs; i++) {
            sb.append(TAB);
        }
        return sb.toString();
    }
}
