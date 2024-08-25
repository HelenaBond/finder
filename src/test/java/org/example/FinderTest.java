package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static org.assertj.core.api.Assertions.assertThat;

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
        if ((Long) Files.getAttribute(resultDirectory, "size", NOFOLLOW_LINKS) == 0) {
            Files.delete(resultDirectory);
        }
    }
}
