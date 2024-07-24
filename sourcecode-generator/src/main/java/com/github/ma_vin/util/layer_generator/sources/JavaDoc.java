package com.github.ma_vin.util.layer_generator.sources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false)
public class JavaDoc extends AbstractGenerateLines {
    /**
     * Maximal number of entries for each line
     */
    public static final int JAVA_DOC_MAX_LENGTH = MAX_LENGTH - 3;
    /**
     * the parameter signal
     */
    public static final String PARAM = "@param";
    /**
     * the return signal
     */
    public static final String RETURN = "@return";
    /**
     * the throw signal
     */
    public static final String THROWS = "@throws";
    /**
     * template of a line with signal element
     */
    public static final String TAG_OR_FILLER_BEFORE_DESCRIPTION = " * %s %s";

    List<String> commentLines = new ArrayList<>();
    List<PropertyDescription> parameters = new ArrayList<>();
    String returnDescription = null;
    List<PropertyDescription> throwDescriptions = new ArrayList<>();

    /**
     * Constructor
     *
     * @param comment the text of the java doc
     */
    public JavaDoc(String comment) {
        addLine(comment);
    }

    /**
     * Adds a line to the text of the java doc
     *
     * @param commentLine line to add
     */
    public void addLine(String commentLine) {
        if (commentLine == null) {
            return;
        }
        commentLines.addAll(splitLine(commentLine, " ", JAVA_DOC_MAX_LENGTH));
    }

    /**
     * Adds a line to the text of the java doc
     *
     * @param commentLine template of a line to add
     * @param args        arguments to set at template
     */
    public void addLine(String commentLine, Object... args) {
        addLine(String.format(commentLine, args));
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * adds a parameter description to this java doc
     *
     * @param parameter   name of the parameter
     * @param description the description template
     * @param args        arguments to set at template
     */
    public void addParams(String parameter, String description, Object... args) {
        addParams(parameter, String.format(description, args));
    }

    /**
     * adds a parameter description to this java doc
     *
     * @param parameter   name of the parameter
     * @param description the description
     */
    public void addParams(String parameter, String description) {
        parameters.add(new PropertyDescription(parameter, description));
    }

    /**
     * Adds a throw description to this java doc
     *
     * @param exceptionClass the name of the class which might be thrown
     * @param description    the description
     */
    public void addThrows(String exceptionClass, String description) {
        throwDescriptions.add(new PropertyDescription(exceptionClass, description));
    }

    /**
     * Sets the description of the return
     *
     * @param returnDescription description of the return
     */
    public void setReturnDescription(String returnDescription) {
        this.returnDescription = returnDescription;
    }

    /**
     * Sets the description of the return
     *
     * @param returnDescription template of description of the return
     * @param args              arguments to set at template
     */
    public void setReturnDescription(String returnDescription, Object... args) {
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
