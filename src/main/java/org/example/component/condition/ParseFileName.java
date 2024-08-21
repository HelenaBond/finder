package org.example.component.condition;

import org.example.component.TypeOfSearch;

import java.nio.file.Path;
import java.util.function.Predicate;

public class ParseFileName implements ParseCondition {
    @Override
    public Predicate<Path> parseSearchFileName(TypeOfSearch searchType, String file) {
        return path -> path.toFile().getName().equals(file);
    }
}
