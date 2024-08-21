package org.example.component.condition;

import org.example.component.TypeOfSearch;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ParseMask implements ParseCondition {
    @Override
    public Predicate<Path> parseSearchFileName(TypeOfSearch searchType, String file) {
        String string = file.replaceAll("[.]", "[.]");
        string = string.replaceAll("[*]", ".*");
        string = string.replaceAll("[?]", ".?");
        try {
            Pattern pattern = Pattern.compile(string);
            return path -> pattern.matcher(path.toFile().getName()).find();
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException(String.format(
                    "Failed to convert the mask into regular expression. Please check the mask value. %s",
                    e.getMessage()));
        }
    }
}
