package de.mv.ape.layer.generator.generator;

import de.mv.ape.layer.generator.config.elements.Config;
import de.mv.ape.layer.generator.config.elements.Entity;
import lombok.Data;
import org.apache.maven.plugin.logging.Log;

import java.io.File;

/**
 * Class to create sources of domain objects
 */
@Data
public class DomainCreator extends AbstractCreator {

    public DomainCreator(Config config, Log logger) {
        super(config, logger);
    }

    /**
     * Creates the domain objects
     *
     * @param entity      Entity to generate
     * @param packageName name of the package to use
     * @param packageDir  directory where to write at
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    public boolean createDomainObject(Entity entity, String packageName, File packageDir) {
        return true;
    }
}
