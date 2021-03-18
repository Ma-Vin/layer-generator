package de.mv.ape.layer.generator.sources;

import java.util.Collections;
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

    public static String getParametersText(List<? extends IComparableWithText> parameters) {
        if (parameters.isEmpty()) {
            return "";
        }
        Collections.sort(parameters);
        StringBuilder sb = new StringBuilder();
        sb.append(parameters.get(0).getText());
        for (int i = 1; i < parameters.size(); i++) {
            sb.append(", ");
            sb.append(parameters.get(i).getText());
        }
        return sb.toString();
    }
}
