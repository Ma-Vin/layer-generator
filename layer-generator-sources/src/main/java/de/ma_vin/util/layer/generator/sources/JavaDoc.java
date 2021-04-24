package de.ma_vin.util.layer.generator.sources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class to generate sources for java docs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JavaDoc extends AbstractGenerateLines {
    public static final int JAVA_DOC_MAX_LENGTH = MAX_LENGTH - 3;
    public static final String PARAM = "@param";
    public static final String RETURN = "@return";
    public static final String THROWS = "@throws";
    public static final String TAG_OR_FILLER_BEFORE_DESCRIPTION = " * %s %s";

    List<String> commentLines = new ArrayList<>();
    List<PropertyDescription> parameters = new ArrayList<>();
    String returnDescription = null;
    List<PropertyDescription> throwDescriptions = new ArrayList<>();

    public JavaDoc(String comment) {
        addLine(comment);
    }

    public void addLine(String commentLine) {
        commentLines.addAll(splitLine(commentLine, " ", JAVA_DOC_MAX_LENGTH));
    }

    public void addLine(String commentLine, String... args) {
        addLine(String.format(commentLine, args));
    }

    @Override
    public List<String> generate() {
        List<String> result = new ArrayList<>();
        if (commentLines.isEmpty() && parameters.isEmpty() && returnDescription == null && throwDescriptions.isEmpty()) {
            return result;
        }
        result.add("/**");
        commentLines.forEach(c -> result.add(" * " + c.trim()));
        if (!commentLines.isEmpty() && (!parameters.isEmpty() || returnDescription != null || !throwDescriptions.isEmpty())) {
            result.add(" *");
        }
        result.addAll(getParamLines());
        result.addAll(getReturnLines());
        result.addAll(getThrowsLines());
        result.add(" */");
        return result;
    }

    public void addParams(String parameter, String description, String... args) {
        addParams(parameter, String.format(description, args));
    }

    public void addParams(String parameter, String description) {
        parameters.add(new PropertyDescription(parameter, description));
    }

    public void addThrows(String exceptionClass, String description) {
        throwDescriptions.add(new PropertyDescription(exceptionClass, description));
    }

    public void setReturnDescription(String returnDescription) {
        this.returnDescription = returnDescription;
    }

    public void setReturnDescription(String returnDescription, String... args) {
        setReturnDescription(String.format(returnDescription, args));
    }

    private int getStartIndex(List<PropertyDescription> parameters) {
        return parameters.stream().map(p -> p.property.length()).max(Comparator.comparingInt(l -> l)).orElse(0);
    }

    private List<String> getParamLines() {
        return getPropertyDescription(parameters, PARAM);
    }

    private List<String> getThrowsLines() {
        return getPropertyDescription(throwDescriptions, THROWS);
    }

    List<String> getPropertyDescription(List<PropertyDescription> propertyDescriptions, String tag) {
        List<String> result = new ArrayList<>();
        int descriptionStartIndex = getStartIndex(propertyDescriptions);
        int maxLength = JAVA_DOC_MAX_LENGTH - tag.length() - descriptionStartIndex - 2;

        propertyDescriptions.forEach(p -> {
            List<String> splitDescription = splitLine(p.description, " ", maxLength);
            result.add(String.format(" * %s %s%s %s", tag, p.property, getFiller(descriptionStartIndex - p.property.length()), splitDescription.get(0)));
            for (int i = 1; i < splitDescription.size(); i++) {
                result.add(String.format(TAG_OR_FILLER_BEFORE_DESCRIPTION, getFiller(descriptionStartIndex + tag.length() + 1), splitDescription.get(i)));
            }
        });

        return result;
    }

    private List<String> getReturnLines() {
        List<String> result = new ArrayList<>();
        if (returnDescription == null) {
            return result;
        }

        int maxLength = JAVA_DOC_MAX_LENGTH - RETURN.length() - 1;
        List<String> splitDescription = splitLine(returnDescription, " ", maxLength);
        result.add(String.format(TAG_OR_FILLER_BEFORE_DESCRIPTION, RETURN, splitDescription.get(0)));
        for (int i = 1; i < splitDescription.size(); i++) {
            result.add(String.format(TAG_OR_FILLER_BEFORE_DESCRIPTION, getFiller(RETURN.length()), splitDescription.get(i)));
        }
        return result;
    }

    private String getFiller(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    @AllArgsConstructor
    private static class PropertyDescription {
        private String property;
        private String description;
    }
}
