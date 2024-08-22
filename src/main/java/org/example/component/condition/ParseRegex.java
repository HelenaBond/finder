package org.example.component.condition;

import java.nio.file.Path;
import java.util.regex.Pattern;

public class ParseRegex implements ParseCondition {

    private final String fileName;

    public ParseRegex(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean isSearchFileName(Path path) {
        Pattern pattern = Pattern.compile(fileName);
        return pattern.matcher(path.toFile().getName()).find();
    }
}
