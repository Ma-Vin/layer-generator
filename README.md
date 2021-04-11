![Maven Workflow Action](https://github.com/Ma-Vin/de.ma_vin.util.layerGenerator/actions/workflows/maven.yml/badge.svg)

# Util Layer Generator
A maven plugin generator to create domain model, data transport or data access objects.

## Sonarcloud analysis

* [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=alert_status)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)
* [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)  [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=bugs)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator) 
* [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=security_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)  [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)
* [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)  [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=sqale_index)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)  [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=code_smells)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)
* [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=coverage)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)
* [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)  [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=ncloc)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)

## Development status
:hourglass: not finished
- [x] dao generator
- [x] dto generator
- [x] domain generator
- [x] dao to domain mapper
- [x] domain to dto mapper
- [ ] extensibility of generated objects

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
An example of usage is shown at [Sample README.md](/sample/README.md)