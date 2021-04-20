![Maven Workflow Action](https://github.com/Ma-Vin/de.ma_vin.util.layerGenerator/actions/workflows/maven.yml/badge.svg)

# Util Layer Generator
A maven plugin generator to create domain model, data transport or data access objects.
It is the third module at ***Util Layer Generator***

## Usage
For basic usage add a plugin entry like
```
<groupId>de.ma_vin.util.layer.generator</groupId>
<artifactId>layer-generator</artifactId>
<version>1.0-SNAPSHOT</version>
<executions>
    <execution>
        <goals>
            <goal>generate-model</goal>
        </goals>
    </execution>
</executions>
````
Following configuration properties are provided and set by default
Property | Default Value | Description
-------- | ------------- | -----------
generateTargetDirectory | src/main/generated/ | relative project directory where to generate source files at
generateDto | true | Indicator to generate data transport objects
generateDomain | true | Indicator to generate domain objects
generateDao | true | Indicator to generate data access objects
modelDefinitionDirectory | src/main/resources/ | relative project directory where to get model file from
modelDefinitionFilename | model.xml | xml which provides the model definition. Has to satisfy the config.xsd
cleanTargetDirectory | false | if true all files and directories within the *generate-target*
cleanBasePackage | true | if true all files and directories within the corresponding directory of *basePackage* (defined in the *model-file*)

## Example
An example of usage is shown at [Sample](../layer-generator-sample/README.md)