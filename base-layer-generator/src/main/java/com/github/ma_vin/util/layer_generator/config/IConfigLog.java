package com.github.ma_vin.util.layer_generator.config;

import java.util.Collections;
import java.util.List;


/**
 * Interface to log config elements
 */
public interface IConfigLog {

    /**
     * @return a simple name to represent this class at config log
     */
    default String getSimpleLogName() {
        return ConfigElementsUtil.getSimpleLogName(this);
    }

    /**
     * @return list of field names whose log method is to call instead of reduced by a simple name
     */
    default List<String> getFieldNamesToLogComplete() {
        return Collections.emptyList();
    }
}
