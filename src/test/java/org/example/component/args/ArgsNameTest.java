package org.example.component.args;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ArgsNameTest {
    @Test
    void whenGet() {
        ArgsName jvm = ArgsName.of(new String[] {"-Xmx=512", "-encoding=UTF-8"});
        assertThat(jvm.get("Xmx")).isEqualTo("512");
    }

    @Test
    void whenMultipleEqualsSymbol() {
        ArgsName jvm = ArgsName.of(new String[] {"-request=?msg=Exit="});
        assertThat(jvm.get("request")).isEqualTo("?msg=Exit=");
    }

    @Test
    void whenKeyNotExist() {
        ArgsName jvm = ArgsName.of(new String[] {"- Xmx= 512"});
        assertThatThrownBy(() -> jvm.get("Xms")).isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining("This key: 'Xms' is missing");
    }

    @Test
    void whenNoArguments() {
        assertThatThrownBy(() -> ArgsName.of(new String[]{}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining("Arguments not passed to program");
    }

    @Test
    void whenStringDoesNotContainKeyThenExceptionThrown() {
        assertThatThrownBy(() -> ArgsName.of(new String[]{"-Xmx =512", "-=?msg=Exit="}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining("Error: This argument '-=?msg=Exit=' does not contain a key");
    }

    @Test
    void whenStringDoesNotContainValueThenExceptionThrown() {
        assertThatThrownBy(() -> ArgsName.of(new String[]{"-Xmx=512", "-request="}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining("Error: This argument '-request=' does not contain a value");
    }

    @Test
    void whenThereNoEqualSignThenExceptionThrown() {
        assertThatThrownBy(() -> ArgsName.of(new String[]{"-Xmx=512", "-request?msgHello"}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(
                        "Error: This argument '-request?msgHello' does not contain an equal sign");
    }

    @Test
    void whenNoHyphenPrefixThenExceptionThrown() {
        assertThatThrownBy(() -> ArgsName.of(new String[]{"-Xmx=512", "request=?msg=Exit="}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(
                        "Error: This argument 'request=?msg=Exit=' does not start with a '-' character");
    }
}
