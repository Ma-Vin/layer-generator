package com.github.ma_vin.util.layer_generator.sources;

import lombok.Getter;

/**
 * Visibility qualifier
 */
@Getter
public enum Qualifier {
    /**
     * Private qualifier
     */
    PRIVATE("private"),
    /**
     * protected qualifier
     */
    PROTECTED("protected"),
    /**
     * public qualifier
     */
    PUBLIC("public"),
    /**
     * default empty qualifier
     */
    DEFAULT("");
    private String text;

    Qualifier(String text) {
        this.text = text;
    }
}
