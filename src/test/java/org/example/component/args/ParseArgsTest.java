package org.example.component.args;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.example.component.HelpConstant.VALID_SEARCH_TYPE;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParseArgsTest {

    private static ParseArgs parseArgs;

    @BeforeAll
    public static void initData() {
        parseArgs = new ParseArgs();
    }

    @Test
    public void whenParseOutFileNameWithDirectory() {
        String separator = FileSystems.getDefault().getSeparator();
        String outFileName = String.join(separator, "one", "test.txt");
        String expected = String.join(separator, "data", "one", "test.txt");
        assertThat(parseArgs.parseOutFileName(outFileName).toString()).isEqualTo(expected);
    }

    @Test
    public void whenParseOutFileNameWithoutDotThenExceptionThrown() {
        assertThatThrownBy(() -> parseArgs.parseOutFileName("test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("Indicate the file extension after the name, with a dot.");
    }

    @Test
    public void whenParseConditionWithMask() {
        Predicate<Path> maskCondition = parseArgs.parseCondition("mask", "*.?xt");
        assertTrue(maskCondition.test(Path.of("test.txt")));
    }

    @Test
    public void whenParseConditionWithName() {
        Predicate<Path> nameCondition = parseArgs.parseCondition("name", "test.docx");
        assertTrue(nameCondition.test(Path.of("test.docx")));
    }

    @Test
    public void whenParseConditionWithRegex() {
        Predicate<Path> regexCondition = parseArgs.parseCondition("regex", "^test\\..+$");
        assertTrue(regexCondition.test(Path.of("test.docx")));
    }

    @Test
    public void whenParseConditionWithInvalidTypeOfSearchThenExceptionThrown() {
        assertThatThrownBy(() -> parseArgs.parseCondition("test", "test.txt"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching(String.format(
                        "'test' not valid type of search. %s", VALID_SEARCH_TYPE.getInstruction()));
    }
}
