![Maven Workflow Action](https://github.com/Ma-Vin/de.mv.ape.layerGenerator/actions/workflows/maven.yml/badge.svg)

# APE Layer Generator
A maven plugin generator to create domain model, data transport or data access objects.

## Sonarcloud analysis

* [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.mv.ape.layerGenerator&metric=alert_status)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.mv.ape.layerGenerator)
* [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.mv.ape.layerGenerator&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.mv.ape.layerGenerator)  [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.mv.ape.layerGenerator&metric=bugs)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.mv.ape.layerGenerator) 
* [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.mv.ape.layerGenerator&metric=security_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.mv.ape.layerGenerator)  [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.mv.ape.layerGenerator&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.mv.ape.layerGenerator)
* [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.mv.ape.layerGenerator&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.mv.ape.layerGenerator)  [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.mv.ape.layerGenerator&metric=sqale_index)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.mv.ape.layerGenerator)  [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.mv.ape.layerGenerator&metric=code_smells)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.mv.ape.layerGenerator)
* [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.mv.ape.layerGenerator&metric=coverage)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.mv.ape.layerGenerator)
* [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.mv.ape.layerGenerator&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.mv.ape.layerGenerator)  [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.mv.ape.layerGenerator&metric=ncloc)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.mv.ape.layerGenerator)

## Development status
:hourglass: not finished
- [x] dao generator
- [x] dto generator
- [x] domain generator
- [ ] dao to domain mapper
- [ ] domain to dto mapper
- [ ] extensibility of generated objects

## Usage
For basic usage add a plugin entry like
```
<groupId>de.mv.ape.layer.generator</groupId>
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
generate-target | src/main/generated/ | relative project directory where to generate source files at
generate-dto | true | Indicator to generate data transport objects 
generate-domain | true | Indicator to generate domain objects
generate-dao | true | Indicator to generate data access objects
model-dir | src/main/resources/ | relative project directory where to get model file from
model-file | model.xml | xml which provides the model definition. Has to satisfy the config.xsd
