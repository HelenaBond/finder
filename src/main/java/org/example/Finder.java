package org.example;

import org.example.component.args.Arguments;
import org.example.component.args.ParseArgs;
import org.example.component.condition.ParseCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public class Finder {

    private static final Logger LOG = LoggerFactory.getLogger(Finder.class.getName());

    public static void main(String[] args) throws IOException {
        LOG.info("Start finder.");
        Finder finder = new Finder();
        finder.simpleSearch(args);
        LOG.info("Finish finder.");
    }

    public void simpleSearch(String[] args) throws IOException {
        ParseArgs parseArgs = new ParseArgs();
        SearchService searchService = new SearchService(parseArgs);

        Arguments arguments = searchService.valid(args);
        ParseCondition condition = arguments.condition();
        Set<Path> filteredFiles = searchService.search(arguments.dir(), condition);
        searchService.saveResults(filteredFiles, arguments.outFileName());
    }
}
