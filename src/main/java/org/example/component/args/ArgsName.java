package org.example.component.args;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.example.component.HelpConstant.PAIR_KEY_VALUE;

public class ArgsName {

    private static final Logger LOG = LoggerFactory.getLogger(ArgsName.class.getName());

    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        String value = values.get(key);
        if (value == null) {
            String message = String.format("This key: '%s' is missing", key);
            LOG.error(message);
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    private void parse(String[] args) {
        for (String pair : args) {
            pair = pair.strip();
            int delimiter = pair.indexOf("=");
            validArg(pair, delimiter);
            values.put(pair.substring(1, delimiter).stripTrailing(),
                    pair.substring(delimiter + 1).stripLeading());
        }
    }

    private void validArg(String arg, int delimiter) {
        if (arg.charAt(0) != '-') {
            String message = String.format("Error: This argument '%s' does not start with a '-' character. %s",
                    arg,
                    PAIR_KEY_VALUE);
            LOG.error(message);
            throw new IllegalArgumentException(message);
        }
        if (delimiter == 1) {
            String message = String.format("Error: This argument '%s' does not contain a key. %s",
                    arg,
                    PAIR_KEY_VALUE);
            LOG.error(message);
            throw new IllegalArgumentException(message);
        }
        if (delimiter == arg.length() - 1) {
            String message = String.format("Error: This argument '%s' does not contain a value. %s",
                    arg,
                    PAIR_KEY_VALUE);
            LOG.error(message);
            throw new IllegalArgumentException(message);
        }
        if (delimiter == -1) {
            String message = String.format("Error: This argument '%s' does not contain an equal sign. %s",
                    arg,
                    PAIR_KEY_VALUE.getInstruction());
            LOG.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    public static ArgsName of(String[] args) {
        if (args.length == 0) {
            String message = "Arguments not passed to program";
            LOG.error(message);
            throw new IllegalArgumentException(message);
        }
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
