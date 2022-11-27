![Maven Workflow Action](https://github.com/Ma-Vin/de.ma_vin.util.layerGenerator/actions/workflows/maven.yml/badge.svg?branch=release%2Fv1.4)

# Util Layer Generator
A maven plugin generator and annotations to create domain model, data transport or data access objects and their mappers.

## Sonarcloud analysis

* [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.4&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=alert_status)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)
* [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.4&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)  [![Bugs](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.4&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=bugs)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)
* [![Security Rating](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.4&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=security_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)  [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.4&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)
* [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.4&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)  [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.4&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=sqale_index)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)  [![Code Smells](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.4&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=code_smells)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)
* [![Coverage](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.4&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=coverage)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)
* [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.4&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)  [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?branch=release%2Fv1.4&project=Ma-Vin_de.ma_vin.util.layerGenerator&metric=ncloc)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.layerGenerator)

## Usage
For usage the artifact ***de.ma_vin.util.layer.generator:layer-generator-annotation*** is needed as dependency 
and ***de.ma_vin.util.layer.generator:layer-generator-plugin*** should be included as maven plugin. See details at their readme markdowns.

### Maven annotation
The implementation of the annotations is located at [layer-generator-annotation](/layer-generator-annotation)
### Maven plugin
The implementation of the maven plugin is located at [layer-generator-plugin](/layer-generator-plugin)
### Example
An example of usage is shown at [layer-generator-sample](/layer-generator-sample)