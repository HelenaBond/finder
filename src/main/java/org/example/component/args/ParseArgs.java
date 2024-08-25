package org.example.component.args;

import org.example.component.TypeOfSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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

    public Predicate<Path> parseCondition(String type, String fileName) {
        TypeOfSearch searchType = TypeOfSearch.fromString(type);
        return switch (searchType) {
            case MASK -> {
                String string = fileName.replaceAll("[.]", "[.]");
                string = string.replaceAll("[*]", ".*");
                string = string.replaceAll("[?]", ".?");
                try {
                    Pattern pattern = Pattern.compile(string);
                    LOG.info("The mask was converted to a regular expression once.");
                    yield (path) -> pattern.matcher(path.toFile().getName()).find();
                } catch (PatternSyntaxException e) {
                    String message = "Failed to convert the mask into regular expression. Please check the mask value.";
                    LOG.error(message, e);
                    throw new IllegalArgumentException(message, e);
                }

            }
            case REGEX -> {
                Pattern pattern = Pattern.compile(fileName);
                yield path -> pattern.matcher(path.toFile().getName()).find();
            }
            case FILE_NAME -> path -> fileName.equals(path.toFile().getName());
        };
    }
}
