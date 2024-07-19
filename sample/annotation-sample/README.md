# Util Layer Generator Annotation Sample

An example of using the generator annotation of the ***Util Layer Generator***

## Annotation

The configuration of the generator is added
at [Configuration.java](./src/main/java/com/github/ma_vin/util/layer_generator/sample/Configuration.java).
````java
@LayerGenerator(modelDefinitionDirectory = "config", modelDefinitionFilename = "smallExampleModel.yaml", logLevel = Diagnostic.Kind.OTHER)
public class Configuration {
}
````

For illustration the generated code, based
on [smallExampleModel.yaml](./src/main/resources/config/smallExampleModel.yaml), is also committed at package
`com.github.ma_vin.util.layer_generator.sample.content`
at [src/main/generated](./src/main/generated) folder.

## Dependencies

The following dependency is needed to provide the annotation configuration

````xml

<dependencies>
    <dependency>
        <groupId>com.github.ma_vin.util.layer_generator</groupId>
        <artifactId>generator-annotation</artifactId>
    </dependency>
</dependencies>
````

All other dependencies are also referenced by `generator-annotation` like `extension-annotation`
or `jakarta.persistence-api` and imported indirectly (contrary to plugin, which has to declare them explicitly)