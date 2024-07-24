package com.github.ma_vin.util.layer_generator.sources;

/**
 * Interface to provide a textual source code representation
 *
 * @param <T> the type of objects that this object may be compared to
 */
public interface IComparableWithText<T> extends Comparable<T> {
    /**
     * @return textual source code representation
     */
    String getText();
}
