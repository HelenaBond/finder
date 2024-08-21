package org.example;

import org.example.component.args.Arguments;
import org.example.component.args.ParseArgs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;

public class Finder {

    public static void main(String[] args) throws IOException {
        Finder finder = new Finder();
        finder.simpleSearch(args);
    }

    public void simpleSearch(String[] args) throws IOException {
        ParseArgs parseArgs = new ParseArgs();
        SearchService searchService = new SearchService(parseArgs);

        Arguments arguments = searchService.valid(args);
        Predicate<Path> condition = arguments.condition();
        Set<Path> filteredFiles = searchService.search(arguments.dir(), condition);
        searchService.saveResults(filteredFiles, arguments.outFileName());
    }
}
