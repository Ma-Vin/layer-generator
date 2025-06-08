# Util Layer Generator Plugin Sample

An example of using the maven plugin of the ***Util Layer Generator***

For illustration the generated code, based on [exampleModel.xml](./src/main/resources/exampleModel.xml), is also
committed at package `com.github.ma_vin.util.layer_generator.sample.content` at
directory [generated](./src/main/generated) with plugin and annotation generated classes.

## Plugin

The plugin is configured as follows (derived from effective pom)

````xml

<plugin>
    <groupId>com.github.ma_vin.util.layer_generator</groupId>
    <artifactId>generator-plugin</artifactId>
    <version>2.1.0</version>
    <executions>
        <execution>
            <goals>
                <goal>generate-model</goal>
            </goals>
            <configuration>
                <generateTargetDirectory>src/main/generated/plugin/java/</generateTargetDirectory>
                <generateDto>true</generateDto>
                <generateDomain>true</generateDomain>
                <generateDao>true</generateDao>
                <modelDefinitionDirectory>src/main/resources/</modelDefinitionDirectory>
                <modelDefinitionFilename>exampleModel.xml</modelDefinitionFilename>
                <cleanTargetDirectory>false</cleanTargetDirectory>
                <cleanBasePackage>true</cleanBasePackage>
            </configuration>
        </execution>
    </executions>
</plugin>
````
### Compiler plugin
For this the configuration properties `generatedSourcesDirectory` and `compileSourceRoots` are set at
`maven-compiler-plugin` at parent [pom.xml](../pom.xml).
## Dependencies
The following dependencies are needed also:

````xml

<dependencies>
    <dependency>
        <groupId>com.github.ma_vin.util.layer_generator</groupId>
        <artifactId>util-sample</artifactId>
        <version>${project.version}</version>
    </dependency>
    <dependency>
        <groupId>com.github.ma_vin.util.layer_generator</groupId>
        <artifactId>extension-annotation</artifactId>
        <version>${project.version}</version>
    </dependency>
    <dependency>
        <groupId>jakarta.persistence</groupId>
        <artifactId>jakarta.persistence-api</artifactId>
    </dependency>
</dependencies>
````

* The `util-sample` provides for example the referenced `IdGenerator`.
* The `extension-annotation` provides annotations which are generated at classes and used to determine the classes to
create by ObjectFactories
at [generated/annotation/..](./src/main/generated/annotation/java/com/github/ma_vin/util/layer_generator/sample/content).
* The `jakarta.persistence-api` provides the annotation at data access objects.
