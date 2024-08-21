package org.example.component;

import org.example.component.args.ArgsName;
import org.example.component.condition.ParseCondition;
import org.example.component.condition.ParseFileName;
import org.example.component.condition.ParseMask;
import org.example.component.condition.ParseRegex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.component.HelpConstant.VALID_SEARCH_TYPE;

public enum TypeOfSearch {

    MASK("mask", new ParseMask()),
    FILE_NAME("name", new ParseFileName()),
    REGEX("regex", new ParseRegex());

    private static final Logger LOG = LoggerFactory.getLogger(ArgsName.class.getName());

    private final String text;
    private final ParseCondition parseCondition;

    TypeOfSearch(String text, ParseCondition parseCondition) {
        this.text = text;
        this.parseCondition = parseCondition;
    }

    public ParseCondition getParseCondition() {
        return parseCondition;
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
