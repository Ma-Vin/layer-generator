![Maven Workflow Action](https://github.com/Ma-Vin/de.ma_vin.util.layerGenerator/actions/workflows/maven.yml/badge.svg)

# Util Layer Generator

A maven plugin generator and annotations to create domain model, data transport or data access objects and their
mappers.

## Sonarcloud analysis

* [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=alert_status)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)
* [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)  [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=bugs)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)
* [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=security_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)  [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)
* [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)  [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=sqale_index)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)  [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=code_smells)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)
* [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=coverage)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)
* [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)  [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=ncloc)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)

## Usage

For usage the artifact ***de.ma_vin.util.layer.generator:extension-annotation*** is needed as dependency
and ***de.ma_vin.util.layer.generator:generator-plugin*** should be included as maven plugin. See details at their
readme markdowns.

It is possible to use the artifact ***de.ma_vin.util.layer.generator:generator-annotation*** instead of the maven
plugin. See details at its readme markdowns.

### Annotation for Extension

The implementation of the annotations to extend generated classes by custom code is located
at [extension-annotation](/extension-annotation)

### Maven plugin generator

The implementation of the maven plugin is located at [generator-plugin](/generator-plugin)

### Annotation generator

The implementation of the annotation generator is located at [generator-annotation](/generator-annotation)

### Example

An example of plugin and extension annotations usage is shown at [plugin-sample](/sample/plugin-sample)

An example of generator annotation usage is shown at [annotation-sample](/sample/annotation-sample)

An example of versioning usage is shown at [version-sample](/sample/version-sample)