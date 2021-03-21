package de.mv.ape.layer.generator.generator;

import de.mv.ape.layer.generator.config.elements.Config;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.maven.plugin.logging.Log;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractMapperCreator extends AbstractCreator {

    AbstractMapperCreator(Config config, Log logger) {
        super(config, logger);
    }
}
