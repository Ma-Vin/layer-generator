![Maven Workflow Action](https://github.com/Ma-Vin/de.ma_vin.util.layerGenerator/actions/workflows/maven.yml/badge.svg?branch=release%2Fv1.1)

# Util Layer Generator
A maven plugin generator and annotations to create domain model, data transport or data access objects and their mappers.

## Sonarcloud analysis

* [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.1&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=alert_status)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator&branch=release%2Fv1.1)
* [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.1&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator&branch=release%2Fv1.1)  [![Bugs](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.1&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=bugs)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator&branch=release%2Fv1.1)
* [![Security Rating](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.1&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=security_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator&branch=release%2Fv1.1)  [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.1&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator&branch=release%2Fv1.1)
* [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.1&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator&branch=release%2Fv1.1)  [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.1&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=sqale_index)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator&branch=release%2Fv1.1)  [![Code Smells](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.1&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=code_smells)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator&branch=release%2Fv1.1)
* [![Coverage](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.1&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=coverage)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator&branch=release%2Fv1.1)
* [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.1&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator&branch=release%2Fv1.1)  [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.1&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=ncloc)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator&branch=release%2Fv1.1)

## Usage
For usage the artifact ***de.ma_vin.util.layer.generator:layer-generator-annotation*** is needed as dependency 
and ***de.ma_vin.util.layer.generator:layer-generator-plugin*** should be included as maven plugin. See details at their readme markdowns.

### Maven annotation
The implementation of the annotations is located at [layer-generator-annotation](/layer-generator-annotation)
### Maven plugin
The implementation of the maven plugin is located at [layer-generator-plugin](/layer-generator-plugin)
### Example
An example of usage is shown at [layer-generator-sample](/layer-generator-sample)