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

    public static TypeOfSearch fromString(String type) {
        for (TypeOfSearch searchType : TypeOfSearch.values()) {
            if (searchType.text.equalsIgnoreCase(type)) {
                return searchType;
            }
        }
            String message = String.format("'%s' not valid type of search. %s",
                    type,
                    VALID_SEARCH_TYPE.getInstruction());
            LOG.error(message);
            throw new IllegalArgumentException(message);
    }
}
