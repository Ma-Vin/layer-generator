package com.github.ma_vin.util.layer_generator.sample;

import com.github.ma_vin.util.layer_generator.annotations.LayerGenerator;

import javax.tools.Diagnostic;

@LayerGenerator(modelDefinitionDirectory = "config", modelDefinitionFilename = "smallExampleModel.yaml", logLevel = Diagnostic.Kind.OTHER)
public class Configuration {
}
