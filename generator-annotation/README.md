![Maven Workflow Action](https://github.com/Ma-Vin/de.ma_vin.util.layerGenerator/actions/workflows/maven.yml/badge.svg)

# Util Layer Generator Plugin

An annotation generator to create domain model, data transport or data access objects.

## Usage

For basic usage add the annotation ***LayerGenerator*** to some class

Following configuration properties are provided and set by default

| Property                 | Default Value   | Description                                                                                                                             |
|--------------------------|-----------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| generateDto              | true            | Indicator to generate data transport objects                                                                                            |
| generateDomain           | true            | Indicator to generate domain objects                                                                                                    |
| generateDao              | true            | Indicator to generate data access objects                                                                                               |
| modelDefinitionDirectory | an empty string | relative project directory,with respect to *target/classes* / *javax.tools.StandardLocation#CLASS_OUTPUT*, where to get model file from |
| modelDefinitionFilename  | model.yaml      | yaml which provides the model definition. Has to satisfy the config.xsd. Or equivalent json and xml format                              |
| logLevel                 | Kind.WARNING    | log level of processor. Allowed values are *ERROR, WARNING, NOTE*(info) or *OTHER*(debug), see also *javax.tools.Diagnostic.Kind*       |

### Model definition

See [Code Generator](../base-layer-generator/README.md) description

## Example

An example of usage is shown at [annotation-sample](../sample/annotation-sample)