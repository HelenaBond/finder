package org.example.component.args;

import org.example.component.condition.ParseCondition;

import java.nio.file.Path;

public record Arguments(Path dir, ParseCondition condition, Path outFileName) {
}
