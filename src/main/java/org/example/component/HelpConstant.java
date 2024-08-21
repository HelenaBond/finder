package org.example.component;

public enum HelpConstant {
    PAIR_KEY_VALUE(
            "Please review each pair in the arguments to the template format of key=value."),
    VALID_SEARCH_TYPE(
            "Valid type of search is 'mask', 'name', 'regex'."),
    REQUIRED_EXTENSION(
            "Indicate the file extension after the name, with a dot.");

    private final String instruction;

    HelpConstant(String instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }
}
