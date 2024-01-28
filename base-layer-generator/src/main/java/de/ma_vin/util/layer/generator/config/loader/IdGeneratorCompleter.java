package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.logging.ILogWrapper;

/**
 * Completer which sets the indicator to use an id generator or not.
 */
public class IdGeneratorCompleter extends AbstractCompleter {

    public IdGeneratorCompleter(ILogWrapper logger) {
        super("id generator", 1, logger);
    }

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
