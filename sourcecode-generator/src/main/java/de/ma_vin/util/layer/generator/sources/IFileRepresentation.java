package de.ma_vin.util.layer.generator.sources;

import java.util.List;

/**
 * Interface for root objects which are written to files
 */
public interface IFileRepresentation {

    /**
     * @return the filename of this object
     */
    String getFilename();

    /**
     * @return the name of this object
     */
    String getObjectName();

    /**
     * @return the name of the package for this object
     */
    String getPackageName();

    /**
     * @return The lines for file content
     */
    List<String> generate();
}
