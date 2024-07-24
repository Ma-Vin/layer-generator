package com.github.ma_vin.util.layer_generator.sources;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract class which provides method to generate a list of source code line
 */
public abstract class AbstractGenerateLines {
    /**
     * tab sign to use for tabbing
     */
    public static final String TAB = "\t";
    /**
     * Maximal length of generated lines
     */
    public static final int MAX_LENGTH = 150;

    /**
     * generates source code lines which represent this class
     *
     * @return source code lines
     */
    public abstract List<String> generate();

    /**
     * generates source code lines which represent this class
     *
     * @param numTabs number of tabs to put in front of lines
     * @return source code lines
     */
    public List<String> generate(int numTabs) {
        String tabs = getTabs(numTabs);
        return generate().stream().map(s -> s.trim().isEmpty() ? s : tabs + s).collect(Collectors.toList());
    }

    /**
     * Determines a string with a given number of tabs
     *
     * @param numTabs the number of tabs
     * @return string with tabs
     */
    public static String getTabs(int numTabs) {
        return TAB.repeat(Math.max(0, numTabs));
    }

    /**
     * Generates a comma separated list of parameters
     *
     * @param parameters the parameters to cognate
     * @return comma separated list of parameters
     */
    public static String getParametersText(List<? extends IComparableWithText> parameters) {
        if (parameters.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(parameters.get(0).getText());
        for (int i = 1; i < parameters.size(); i++) {
            sb.append(", ");
            sb.append(parameters.get(i).getText());
        }
        return sb.toString();
    }

    /**
     * Generates a comma separated list of parameters. If the {@code MAX_LENGTH} a new line is started with indenting tabs in front
     *
     * @param startIndex index where to start at {@code parameters}
     * @param parameters the parameters to cognate
     * @return comma separated list of parameters
     */
    public static List<String> getParameterTexts(int startIndex, List<? extends IComparableWithText> parameters) {
        List<String> result = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        sb.append(parameters.get(0).getText());
        for (int i = 1; i < parameters.size(); i++) {
            if (MAX_LENGTH < startIndex + sb.length() + parameters.get(i).getText().length() + 2) {
                result.add(sb.toString());
                startIndex = 0;
                sb = new StringBuilder();
                sb.append(TAB);
                sb.append(TAB);
            }
            sb.append(", ");
            sb.append(parameters.get(i).getText());
        }
        if (sb.length() > 0) {
            result.add(sb.toString());
        }
        return result;
    }

    /**
     * determines a given text with upper case at first letter
     *
     * @param text the text
     * @return text with upper case at first letter
     */
    protected static String getUpperFirst(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    /**
     * splits a line in to multiple ones by a given delimiter in that way
     * that the delimiter marks the positions of possible new lines not to exceed the {@code maxLength}
     * If curl bracket pairs would split into two lines the split of these two lines will be undone.
     *
     * @param line      the line to split
     * @param delimiter the delimiter
     * @param maxLength the maximal length at split lines
     * @return List of the split representation
     */
    @SuppressWarnings("java:S127")
    protected static List<String> splitLine(String line, String delimiter, int maxLength) {
        ArrayList<String> result = new ArrayList<>();
        if (line.length() <= maxLength) {
            result.add(line);
            return result;
        }
        String[] split = line.split(delimiter);
        ArrayList<String> adjustSplit = new ArrayList<>();
        int lastAdjustIndex = -1;
        for (int i = 0; i < split.length - 1; i++) {
            int lastOpeningCurlBracket = split[i].lastIndexOf("{");
            int lastClosingCurlBracket = split[i].lastIndexOf("}");
            if (lastOpeningCurlBracket > -1 && lastClosingCurlBracket < lastOpeningCurlBracket && split[i + 1].contains("}")) {
                adjustSplit.add(split[i] + delimiter + split[i + 1]);
                i++;
                lastAdjustIndex = i;
                continue;
            }
            lastAdjustIndex = i;
            adjustSplit.add(split[i]);
        }
        if (lastAdjustIndex != split.length - 1) {
            adjustSplit.add(split[split.length - 1]);
        }
        StringBuilder sb = new StringBuilder();
        for (String s : adjustSplit) {
            if (sb.length() + s.length() <= maxLength) {
                sb.append(s);
                sb.append(delimiter);
                continue;
            }
            String toAdd = sb.toString();
            result.add(toAdd.substring(0, toAdd.length() - delimiter.length()).trim());
            sb = new StringBuilder();
            sb.append(s);
            sb.append(delimiter);
        }

        if (sb.length() > 0) {
            String toAdd = sb.toString();
            result.add(toAdd.substring(0, toAdd.length() - delimiter.length()).trim());
        }
        return result;
    }
}
