package com.github.ma_vin.util.layer_generator.config.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ma_vin.util.layer_generator.config.elements.Config;
import com.github.ma_vin.util.layer_generator.logging.ILogWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.Data;
import org.xml.sax.SAXException;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.util.Optional;

/**
 * Loads a configuration file of type json, yaml or xml.
 */
@Data
public class ConfigFileLoader {

    public static final String XML_FILE_ENDING = ".xml";
    public static final String YAML_FILE_ENDING = ".yaml";
    public static final String YML_FILE_ENDING = ".yml";
    public static final String JSON_FILE_ENDING = ".json";

    private final File configFile;
    private final File schemaFile;
    private final ILogWrapper logger;
    private Config config;

    public ConfigFileLoader(File configFile, File schemaFile, ILogWrapper logger) {
        this.configFile = configFile;
        this.schemaFile = schemaFile;
        this.logger = logger;
    }

    public ConfigFileLoader(File configFile, ILogWrapper logger) {
        this(configFile, null, logger);
    }

    /**
     * Loads the configuration from {@code configFile} with formats {@link ConfigFileLoader#XML_FILE_ENDING}
     * , {@link ConfigFileLoader#YAML_FILE_ENDING}, {@link ConfigFileLoader#YML_FILE_ENDING} or {@link ConfigFileLoader#JSON_FILE_ENDING}
     *
     * @return An {@link Optional} of the parsed {@link Config}. {@link Optional#empty()} at any failure.
     */
    public Optional<Config> load() {
        if (!configFile.getName().contains(".")) {
            logger.error(String.format("Could not identify file ending of config file \"%s\"", configFile.getName()));
            return Optional.empty();
        }

        String fileEnding = configFile.getName().substring(configFile.getName().lastIndexOf("."));

        return switch (fileEnding) {
            case JSON_FILE_ENDING -> loadJson();
            case XML_FILE_ENDING -> loadXml();
            case YAML_FILE_ENDING, YML_FILE_ENDING -> loadYaml();
            default -> handleUnknownConfigFileFormat(fileEnding);
        };
    }

    /**
     * Parse the config file with a yaml format to {@link Config} property {@code configFile}
     *
     * @return An {@link Optional} of the parsed {@link Config}. {@link Optional#empty()} at any failure.
     */
    private Optional<Config> loadYaml() {
        Yaml yaml = createYaml();
        try {
            config = yaml.load(createFileInputStream(configFile));
            return Optional.of(config);
        } catch (FileNotFoundException e) {
            return handleConfigReadException(e);
        }
    }

    /**
     * Create a new instance of a {@link Yaml}.
     * <br/>
     * this method exists for mocking reasons
     *
     * @return a new {@link Yaml}
     */
    protected Yaml createYaml() {
        return new Yaml(new Constructor(Config.class, new LoaderOptions()));
    }

    /**
     * Create a new instance of a {@link FileInputStream}.
     * <br/>
     * this method exists for mocking reasons
     *
     * @param file the file to read
     * @return a new {@link FileInputStream}
     */
    protected FileInputStream createFileInputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    /**
     * Parse the config file with a json format to {@link Config} property {@code configFile}
     *
     * @return An {@link Optional} of the parsed {@link Config}. {@link Optional#empty()} at any failure.
     */
    private Optional<Config> loadJson() {
        ObjectMapper objectMapper = createObjectMapper();
        try {
            config = objectMapper.readValue(configFile, Config.class);
            return Optional.of(config);
        } catch (IOException e) {
            return handleConfigReadException(e);
        }
    }

    /**
     * Create a new instance of an object mapper.
     * <br/>
     * this method exists for mocking reasons
     *
     * @return a new object mapper
     */
    protected ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }

    /**
     * Parse the config file with a xml format to the {@link Config} property {@code configFile}
     *
     * @return An {@link Optional} of the parsed {@link Config}. {@link Optional#empty()} at any failure.
     */
    private Optional<Config> loadXml() {
        logger.warn("xml format is deprecated and will be removed as accepted format at future releases. Use preferred yaml instead");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            if (schemaFile != null) {
                SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema configSchema = sf.newSchema(schemaFile);
                unmarshaller.setSchema(configSchema);
            }

            FileReader reader = createFileReader(configFile);
            config = (Config) unmarshaller.unmarshal(reader);
            return Optional.of(config);
        } catch (JAXBException | FileNotFoundException | SAXException e) {
            return handleConfigReadException(e);
        }
    }

    /**
     * Create a new instance of a file reader.
     * <br/>
     * this method exists for mocking reasons
     *
     * @param file the file to read
     * @return a new object mapper
     */
    protected FileReader createFileReader(File file) throws FileNotFoundException {
        return new FileReader(file);
    }

    /**
     * Handles an unknown file
     *
     * @param fileEnding the unknown file ending
     * @return {@link Optional#empty()}
     */
    private Optional<Config> handleUnknownConfigFileFormat(String fileEnding) {
        logger.error(String.format("Not supported file format \"%s\". Only \"%s\", \"%s\", \"%s\" and \"%s\" are provided"
                , fileEnding, XML_FILE_ENDING, YAML_FILE_ENDING, YML_FILE_ENDING, JSON_FILE_ENDING));
        return Optional.empty();
    }

    /**
     * Handles an exception while reading a config file
     *
     * @param exception the exception which was thrown
     * @return {@link Optional#empty()}
     */
    private Optional<Config> handleConfigReadException(Exception exception) {
        logger.error("Could not load config file:");
        logger.error(exception);
        return Optional.empty();
    }
}
