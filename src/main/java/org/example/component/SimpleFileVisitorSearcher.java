package org.example.component;

import org.example.component.condition.ParseCondition;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedHashSet;
import java.util.Set;

public class SimpleFileVisitorSearcher extends SimpleFileVisitor<Path> {

    private final Set<Path> allPaths = new LinkedHashSet<>();
    private final ParseCondition condition;

    public SimpleFileVisitorSearcher(ParseCondition condition) {
        this.condition = condition;
    }

    public Set<Path> getAllPaths() {
        return allPaths;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (condition.isSearchFileName(file)) {
            allPaths.add(file);
        }
        return super.visitFile(file, attrs);
    }
}
