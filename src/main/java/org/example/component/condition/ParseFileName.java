package org.example.component.condition;

import java.nio.file.Path;

public class ParseFileName implements ParseCondition {

    private final String fileName;

    public ParseFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean isSearchFileName(Path path) {
        return path.toFile().getName().equals(fileName);
    }
}
