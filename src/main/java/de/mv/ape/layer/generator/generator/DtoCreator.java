package de.mv.ape.layer.generator.generator;

import de.mv.ape.layer.generator.config.elements.Config;
import de.mv.ape.layer.generator.config.elements.Entity;
import lombok.Data;
import org.apache.maven.plugin.logging.Log;

import java.io.File;

/**
 * Class to create sources of data transport objects
 */
@Data
public class DtoCreator extends AbstractCreator {

    public DtoCreator(Config config, Log logger) {
        super(config, logger);
    }

    /**
     * Creates the data transport objects
     *
     * @param entity      Entity to generate
     * @param packageName name of the package to use
     * @param packageDir  directory where to write at
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    public boolean createDataTransportObject(Entity entity, String packageName, File packageDir) {
        return true;
    }
}
