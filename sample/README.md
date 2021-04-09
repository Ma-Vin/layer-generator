![Maven Workflow Action](https://github.com/Ma-Vin/de.ma_vin.util.sampleLayerGenerator/actions/workflows/sampleMaven.yml/badge.svg)

# APE Sample Layer Generator

An example of using the maven plugin ***Util Layer Generator*** plugin

## Sonarcloud analysis

Please note that the test classes have not been completely created yet

* [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.sampleLayerGenerator&metric=alert_status)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.sampleLayerGenerator)
* [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.sampleLayerGenerator&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.sampleLayerGenerator)  [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.sampleLayerGenerator&metric=bugs)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.sampleLayerGenerator)
* [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.sampleLayerGenerator&metric=security_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.sampleLayerGenerator)  [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.sampleLayerGenerator&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.sampleLayerGenerator)
* [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.sampleLayerGenerator&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.sampleLayerGenerator)  [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.sampleLayerGenerator&metric=sqale_index)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.sampleLayerGenerator)  [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.sampleLayerGenerator&metric=code_smells)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.sampleLayerGenerator)
* [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.sampleLayerGenerator&metric=coverage)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.sampleLayerGenerator)
* [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.sampleLayerGenerator&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.sampleLayerGenerator)  [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Ma-Vin_de.ma_vin.util.sampleLayerGenerator&metric=ncloc)](https://sonarcloud.io/dashboard?id=Ma-Vin_de.ma_vin.util.sampleLayerGenerator)

## Comment

For illustration the generated code, based on *exampleModel.xml*, is also committed at package *de.ma_vin.util.sample.content*

The Github workflow *Build and analyze sample* starts a maven build, which calls the plugin, cleans up the 
*de.ma_vin.util.sample.content* package and regenerates the content.