package org.example.component.args;

import java.nio.file.Path;
import java.util.function.Predicate;

public record Arguments(Path dir, Predicate<Path> condition, Path outFileName) {
}
