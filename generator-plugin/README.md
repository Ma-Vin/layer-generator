# Util Layer Generator Plugin

A maven plugin generator to create domain model, data transport or data access objects.

## Usage

For basic usage add a plugin entry like

```
<groupId>com.github.ma_vin.util.layer_generator</groupId>
<artifactId>generator-plugin</artifactId>
<version>2.2.0-SNAPSHOT</version>
<executions>
    <execution>
        <goals>
            <goal>generate-model</goal>
        </goals>
    </execution>
</executions>
````

Following configuration properties are provided and set by default

| Property                 | Default Value       | Description                                                                                                         |
|--------------------------|---------------------|---------------------------------------------------------------------------------------------------------------------|
| generateTargetDirectory  | src/main/generated/ | relative project directory where to generate source files at                                                        |
| generateDto              | true                | Indicator to generate data transport objects                                                                        |
| generateDomain           | true                | Indicator to generate domain objects                                                                                |
| generateDao              | true                | Indicator to generate data access objects                                                                           |
| modelDefinitionDirectory | src/main/resources/ | relative project directory where to get model file from                                                             |
| modelDefinitionFilename  | model.xml           | xml which provides the model definition. Has to satisfy the config.xsd. Or equivalent json and yaml format          |
| cleanTargetDirectory     | false               | if true all files and directories within the *generate-target*                                                      |
| cleanBasePackage         | true                | if true all files and directories within the corresponding directory of *basePackage* (defined in the *model-file*) |
| skip                     | false               | if true the entire generating will be skipped                                                                       |


### Model definition

See [Code Generator](../base-layer-generator/README.md) description

## Example

An example of usage is shown at [plugin-sample](../sample/plugin-sample)