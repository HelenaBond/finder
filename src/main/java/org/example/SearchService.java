package org.example;

import org.example.component.SimpleFileVisitorSearcher;
import org.example.component.args.ArgsName;
import org.example.component.args.Arguments;
import org.example.component.args.ParseArgs;
import org.example.component.condition.ParseCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class SearchService {

    private static final Logger LOG = LoggerFactory.getLogger(ArgsName.class.getName());

    private final ParseArgs parseArgs;

    public SearchService(ParseArgs parseArgs) {
        this.parseArgs = parseArgs;
    }

    public Arguments valid(String[] args) {
        ArgsName argsName = ArgsName.of(args);
        LOG.trace("User entered: {}", argsName);
        return new Arguments(
                parseArgs.parseDir(argsName.get("d")),
                parseArgs.parseCondition(argsName.get("t"), argsName.get("n")),
                parseArgs.parseOutFileName(argsName.get("o")));
    }

    public Set<Path> search(Path root, ParseCondition condition) throws IOException {
        SimpleFileVisitorSearcher searcher = new SimpleFileVisitorSearcher(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getAllPaths();
    }

    public void saveResults(Set<Path> filteredFiles, Path outDir) throws IOException {
        if (!Files.exists(outDir)) {
            Path dirParent = outDir.getParent();
            if (dirParent != null && !Files.exists(dirParent)) {
                Files.createDirectories(dirParent);
            }
        }
        try (PrintStream stream = new PrintStream(new FileOutputStream(outDir.toFile()))) {
            filteredFiles.forEach(stream::println);
            LOG.trace("Write result in file '{}'. It is {} fined files.", outDir, filteredFiles.size());
        } catch (IOException e) {
            LOG.error("Failed to write result.", e);
            filteredFiles.forEach(System.out::println);
        }
    }
}
