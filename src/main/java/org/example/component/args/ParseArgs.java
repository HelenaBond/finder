package org.example.component.args;

import org.example.component.TypeOfSearch;
import org.example.component.condition.ParseCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;

import static org.example.component.HelpConstant.REQUIRED_EXTENSION;

public class ParseArgs {

    private static final String RESULT_FOLDER = "data";

    private static final Logger LOG = LoggerFactory.getLogger(ParseArgs.class.getName());

    public Path parseDir(String dir) {
        Path startDir = Path.of(dir);
        if (!Files.exists(startDir)) {
            String message = String.format("The path \"%s\" does not exist.", startDir);
            LOG.error(message);
            throw new IllegalArgumentException(message);
        }
        if (!Files.isDirectory(startDir)) {
            String message = String.format("The path \"%s\" is not a directory.", startDir);
            LOG.error(message);
            throw new IllegalArgumentException(message);
        }
        return startDir;
    }

    public Path parseOutFileName(String outFileName) {
        if (!outFileName.contains(".")) {
            String message = REQUIRED_EXTENSION.getInstruction();
            LOG.error(message);
            throw new IllegalArgumentException(message);
        }
        return Path.of(RESULT_FOLDER, outFileName);
    }

    public Predicate<Path> parseCondition(String type, String file) {
        TypeOfSearch searchType = TypeOfSearch.fromString(type);
        ParseCondition condition = searchType.getParseCondition();
        return condition.parseSearchFileName(searchType, file);
    }
}
