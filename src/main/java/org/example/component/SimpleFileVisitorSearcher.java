package org.example.component;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

public class SimpleFileVisitorSearcher extends SimpleFileVisitor<Path> {

    private final Set<Path> allPaths = new LinkedHashSet<>();
    private final Predicate<Path> predicate;

    public SimpleFileVisitorSearcher(Predicate<Path> predicate) {
        this.predicate = predicate;
    }

    public Set<Path> getAllPaths() {
        return allPaths;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (predicate.test(file)) {
            allPaths.add(file);
        }
        return super.visitFile(file, attrs);
    }
}
