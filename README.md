![Maven Workflow Action](https://github.com/Ma-Vin/de.mv.ape.layerGenerator/actions/workflows/maven.yml/badge.svg)

# APE Layer Generator
A maven plugin generator to create domain model, data transport or data access objects.

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
