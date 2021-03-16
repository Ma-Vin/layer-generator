package de.mv.ape.layer.generator.sources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to generate sources for java docs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JavaDoc extends AbstractGenerateLines {
    List<String> commentLines = new ArrayList<>();

    public JavaDoc(String comment) {
        commentLines.add(comment);
    }

    public void addLine(String commentLine) {
        commentLines.add(commentLine);
    }

    @Override
    public List<String> generate() {
        List<String> result = new ArrayList<>();
        if (commentLines.isEmpty()) {
            return result;
        }
        result.add("/**");
        commentLines.forEach(c -> result.add(" * " + c.trim()));
        result.add(" */");
        return result;
    }
}
