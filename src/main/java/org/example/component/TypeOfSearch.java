package org.example.component;

import org.example.component.args.ArgsName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.component.HelpConstant.VALID_SEARCH_TYPE;

public enum TypeOfSearch {

    MASK("mask"),
    FILE_NAME("name"),
    REGEX("regex");

    private static final Logger LOG = LoggerFactory.getLogger(ArgsName.class.getName());

    private final String text;

    TypeOfSearch(String text) {
        this.text = text;
    }

    public static TypeOfSearch fromString(String text) {
        for (TypeOfSearch type : TypeOfSearch.values()) {
            if (type.text.equalsIgnoreCase(text)) {
                return type;
            }
        }
            String message = String.format("'%s' not valid type of search. %s",
                    text,
                    VALID_SEARCH_TYPE.getInstruction());
            LOG.error(message);
            throw new IllegalArgumentException(message);
    }
}
