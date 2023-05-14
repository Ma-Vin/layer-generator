package de.ma_vin.util.sample;

import de.ma_vin.util.layer.generator.annotations.LayerGenerator;

import javax.tools.Diagnostic;

@LayerGenerator(modelDefinitionDirectory = "config", modelDefinitionFilename = "smallExampleModel.yaml", logLevel = Diagnostic.Kind.OTHER)
public class Configuration {
}
