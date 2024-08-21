package org.example.component.condition;

import org.example.component.TypeOfSearch;

import java.nio.file.Path;
import java.util.function.Predicate;

public interface ParseCondition {
    Predicate<Path> parseSearchFileName(TypeOfSearch searchType, String file);
}
