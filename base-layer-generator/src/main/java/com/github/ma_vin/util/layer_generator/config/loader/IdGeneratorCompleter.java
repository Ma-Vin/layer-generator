package com.github.ma_vin.util.layer_generator.config.loader;

import com.github.ma_vin.util.layer_generator.logging.ILogWrapper;

/**
 * Completer which sets the indicator to use an id generator or not.
 */
public class IdGeneratorCompleter extends AbstractCompleter {

    public IdGeneratorCompleter(ILogWrapper logger) {
        super("id generator", 2, logger);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean complete() {
        if ((config.getIdGeneratorPackage() != null && config.getIdGeneratorClass() == null)
                || (config.getIdGeneratorPackage() == null && config.getIdGeneratorClass() != null)) {

            logger.error(String.format("Invalid id generator. Either both or non are null: package\"%s\", class\"%s\""
                    , config.getIdGeneratorPackage(), config.getIdGeneratorClass()));
            config.setUseIdGenerator(false);
            return false;
        }
        config.setUseIdGenerator(config.getIdGeneratorClass() != null);
        return true;
    }
}
