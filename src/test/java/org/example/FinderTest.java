package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FinderTest {

    private static Finder finder;

    @BeforeAll
    public static void init() {
        finder = new Finder();
    }

    @Test
    public void whenSimpleSearch(@TempDir Path temp) throws IOException {
        Path path1 = Files.createDirectories(temp.resolve("one"));
        Path path2 = Files.createDirectories(temp.resolve("two"));
        Files.createFile(path1.resolve("test.txt"));
        Files.createFile(path2.resolve("test.txt"));
        finder.simpleSearch(new String[] {"-d=" + temp, "-n=*.?xt", " -t=mask", " -o=test.txt"});
        Path resultDirectory = Path.of("data");
        Path resultPath = resultDirectory.resolve("test.txt");
        assertThat(Files.readAllLines(resultPath)).hasSize(2);
        Files.delete(resultPath);
        Files.delete(resultDirectory);
    }

    @Test
    public void whenSimpleSearchInvalidMask(@TempDir Path temp) throws IOException {
        Path path1 = Files.createDirectories(temp.resolve("one"));
        Path path2 = Files.createDirectories(temp.resolve("two"));
        Files.createFile(path1.resolve("test.txt"));
        Files.createFile(path2.resolve("test.txt"));
        assertThatThrownBy(() ->
                finder.simpleSearch(new String[] {"-d=" + temp, " -t=mask",  "-n=(*.?xt", " -o=test.txt"}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(
                        "Failed to convert the mask into regular expression. Please check the mask value. ");
    }
}
