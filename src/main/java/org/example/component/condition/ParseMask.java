package org.example.component.condition;

import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ParseMask implements ParseCondition {

    private final String fileName;

    public ParseMask(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean isSearchFileName(Path path) {
        String string = fileName.replaceAll("[.]", "[.]");
        string = string.replaceAll("[*]", ".*");
        string = string.replaceAll("[?]", ".?");
        try {
            Pattern pattern = Pattern.compile(string);
            return pattern.matcher(path.toFile().getName()).find();
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException(String.format(
                    "Failed to convert the mask into regular expression. Please check the mask value. %s",
                    e.getMessage()));
        }
    }
}
