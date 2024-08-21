package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class FinderTest {

    @Test
    public void whenSimpleSearch(@TempDir Path temple) throws IOException {
        Path path1 = Files.createDirectories(temple.resolve("one"));
        Path path2 = Files.createDirectories(temple.resolve("two"));
        Files.createFile(path1.resolve("test.txt"));
        Files.createFile(path2.resolve("test.txt"));
        Finder finder = new Finder();
        finder.simpleSearch(new String[] {"-d=" + temple, " -n=*.?xt", " -t=mask", " -o=test.txt"});
        Path resultDirectory = Path.of("data");
        Path resultPath = resultDirectory.resolve("test.txt");
        assertThat(Files.readAllLines(resultPath)).hasSize(2);
        Files.delete(resultPath);
        Files.delete(resultDirectory);
    }
}
