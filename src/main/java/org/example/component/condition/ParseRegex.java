package org.example.component.condition;

import org.example.component.TypeOfSearch;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ParseRegex implements ParseCondition {
    @Override
    public Predicate<Path> parseSearchFileName(TypeOfSearch searchType, String file) {
        Pattern pattern = Pattern.compile(file);
        return path -> pattern.matcher(path.toFile().getName()).find();
    }
}
