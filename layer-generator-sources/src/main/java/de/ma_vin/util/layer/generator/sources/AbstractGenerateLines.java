package de.ma_vin.util.layer.generator.sources;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractGenerateLines {
    public static final String TAB = "\t";
    public static final int MAX_LENGTH = 150;

    public abstract List<String> generate();

    public List<String> generate(int numTabs) {
        String tabs = getTabs(numTabs);
        return generate().stream().map(s -> s.trim().isEmpty() ? s : tabs + s).collect(Collectors.toList());
    }

    public static String getTabs(int numTabs) {
        return TAB.repeat(Math.max(0, numTabs));
    }

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

    public static List<String> getParameterTexts(int startIndex, List<? extends IComparableWithText> parameters) {
        List<String> result = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        sb.append(parameters.get(0).getText());
        for (int i = 1; i < parameters.size(); i++) {
            if (MAX_LENGTH < startIndex + sb.length() + parameters.get(i).getText().length() +2) {
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

    protected static String getUpperFirst(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

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
