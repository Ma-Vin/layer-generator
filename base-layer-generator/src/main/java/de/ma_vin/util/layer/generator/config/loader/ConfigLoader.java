package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.logging.ILogWrapper;
import lombok.Data;


import java.io.*;
import java.util.*;

@Data
public class ConfigLoader {

    private ILogWrapper logger;
    private ConfigFileLoader fileLoader;

    private SortedSet<AbstractCompleter> completer = new TreeSet<>(Comparator.comparingInt(AbstractCompleter::getExecutionPriority));

    private Config config;

    public ConfigLoader(File configFile, ILogWrapper logger) {
        this(new ConfigFileLoader(configFile, logger), logger);
    }

    public ConfigLoader(File configFile, ILogWrapper logger, File schemaFile) {
        this(new ConfigFileLoader(configFile, schemaFile, logger), logger);
    }

    public ConfigLoader(ConfigFileLoader configLoader, ILogWrapper logger) {
        this.logger = logger;
        this.fileLoader = configLoader;

        completer.add(new ListCompleter(logger));
        completer.add(new IdGeneratorCompleter(logger));
        completer.add(new FieldCompleter(logger));
        completer.add(new ReferenceCompleter(logger));
        completer.add(new EntityCompleter(logger));
        completer.add(new VersionCompleter(logger));
        completer.add(new IndicesCompleter(logger));
    }

    /**
     * Loads the configuration from {@code configFile} with formats {@link ConfigFileLoader#XML_FILE_ENDING}
     * , {@link ConfigFileLoader#YAML_FILE_ENDING}, {@link ConfigFileLoader#YML_FILE_ENDING} or {@link ConfigFileLoader#JSON_FILE_ENDING}
     *
     * @return {@code true} the file could be parsed to {@link Config}, validated and completed. Otherwise {@code false}
     */
    public boolean load() {
        return loadFile() && validate() && complete();
    }

    private boolean loadFile() {
        Optional<Config> loadedConfig = fileLoader.load();
        if (loadedConfig.isEmpty()) {
            return false;
        }
        config = loadedConfig.get();
        logConfig("file loading");
        return true;
    }

    private boolean validate() {
        List<String> messages = new ArrayList<>();
        if (config.isValid(messages)) {
            messages.forEach(logger::warn);
            logConfig("validation");
            return true;
        }
        messages.forEach(logger::error);
        logConfig("validation");
        return false;
    }

    private boolean complete() {
        for (AbstractCompleter c : completer) {
            if (!c.complete(config)) {
                logger.error(c.getFailMessage());
                logConfig("completion");
                return false;
            }
        }
        logConfig("completion");
        return true;
    }

    private void logConfig(String phase){
        logger.debug("Config after " + phase);
        config.logConfig(logger);
    }
}
