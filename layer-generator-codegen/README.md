![Maven Workflow Action](https://github.com/Ma-Vin/de.ma_vin.util.layerGenerator/actions/workflows/maven.yml/badge.svg)

# Code Generator

Code generator to create domain model, data transport or data access objects.

## Model formats

It is possible to define the model with a **xml**, **yaml/yml** or **json** file. At the following the xml variant is used to
describe the configuration elements.
There exists four test resources which define an equal model:
- [exampleModel.xml](src/test/resources/references/config/exampleModel.xml)
- [exampleModel.yaml](src/test/resources/references/config/exampleModel.yaml)
- [exampleModel.yml](src/test/resources/references/config/exampleModel.yml)
- [exampleModel.json](src/test/resources/references/config/exampleModel.json)

## Model definition

The following description refers to the xml format because it contains nodes and attributes element types in contrast to yaml and json
The xsd generation base on [Config](src/main/java/de/ma_vin/util/layer/generator/config/elements/Config.java)

These abbreviations apply in the following for the xml type:

* A : Attribute
* N : Node

### Config

| Property           | Nullable           | xml type | Description                                                                                                                                                                                                                                                                                                                                                                                                               |
|--------------------|--------------------|----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| basePackage        | :x:                | *N*      | Basic package which will be used for generated java classes                                                                                                                                                                                                                                                                                                                                                               |
| dtoPackage         | :x:                | *N*      | Extension of the basic package for dto objects                                                                                                                                                                                                                                                                                                                                                                            |
| domainPackage      | :x:                | *N*      | Extension of the basic package for domain objects                                                                                                                                                                                                                                                                                                                                                                         |
| daoPackage         | :x:                | *N*      | Extension of the basic package for dao objects                                                                                                                                                                                                                                                                                                                                                                            |
| idGeneratorPackage | :heavy_check_mark: | *N*      | The package of an id generator                                                                                                                                                                                                                                                                                                                                                                                            |
| idGeneratorClass   | :heavy_check_mark: | *N*      | The class of an id generator, which is used to transform the database id to an identification with some prefix.<br>The prefix makes it easier to classy the identification to a concrete type of model object<br>Two static function are to provide:<ul><li>*public static String generateIdentification(Long id, String prefix)*</li><li>*public static Long generateId(String identification, String prefix)*</li></ul> |
| entities           | :x:                | *N*      | List of entities which will be used to generate domain objects, dto or dao.                                                                                                                                                                                                                                                                                                                                               |
| groupings          | :x:                | *N*      | List of groupings of entities. Each grouping gets an own sub package                                                                                                                                                                                                                                                                                                                                                      |

### Entity

| Property             | Nullable           | xml type | Description                                                                                                                                                                                        |
|----------------------|--------------------|----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| baseName             | :x:                | *A*      | Base name of the objects, which will be extended by some postfix for dto or dao                                                                                                                    |
| tableName            | :x:                | *A*      | Name of Table at database. if null the baseName is used                                                                                                                                            |
| models               | :x:                | *A*      | Which objects should be generated                                                                                                                                                                  |
| description          | :heavy_check_mark: | *A*      | Description of the entity. Will be added at javadoc                                                                                                                                                |
| identificationPrefix | :heavy_check_mark: | *A*      | If there is a use of idGeneratorClass this field is not null able                                                                                                                                  |
| parent               | :heavy_check_mark: | *A*      | super entity which is extended by this entity. The super entity has to abstract.                                                                                                                   |
| isAbstract           | :heavy_check_mark: | *A*      | indication if the generated java class should be abstract                                                                                                                                          |
| derivedFrom          | :heavy_check_mark: | *A*      | Entity to derive from. The actual entity will be at the transport model and the source has to support the domain model. The generated mapper work only one way: from domain model to transport one |
| fields               | :heavy_check_mark: | *N*      | List of attributes of the entity                                                                                                                                                                   |
| indices              | :heavy_check_mark: | *N*      | List of indices of the entity                                                                                                                                                                      |
| references           | :heavy_check_mark: | *N*      | References to other entities                                                                                                                                                                       |

### Grouping

| Property        | Nullable | xml type | Description                                                                 |
|-----------------|----------|----------|-----------------------------------------------------------------------------|
| groupingPackage | :x:      | *A*      | sub package name for the grouping                                           |
| entities        | :x:      | *N*      | List of entities which will be used to generate domain objects, dto or dao. |

### Field

| Property         | Nullable           | xml type | Description                                                     |
|------------------|--------------------|----------|-----------------------------------------------------------------|
| fieldName        | :x:                | *A*      | Name of the attribute                                           |
| type             | :x:                | *A*      | Type of the attribute                                           |
| typePackage      | :heavy_check_mark: | *A*      | package of the type if necessary                                |
| isTypeEnum       | :heavy_check_mark: | *A*      | indication if the field is an enum or not (Default ist *false*) |
| shortDescription | :heavy_check_mark: | *A*      | Short description of the attribute                              |
| description      | :heavy_check_mark: | *N*      | Description of the attribute                                    |
| models           | :heavy_check_mark: | *A*      | For which object is this field relevant                         |
| daoInfo          | :heavy_check_mark: | *N*      | additional information for database.                            |

### Index

| Property  | Nullable           | xml type | Description                                                            |
|-----------|--------------------|----------|------------------------------------------------------------------------|
| indexName | :x:                | *A*      | Name of the index                                                      |
| isUnique  | :heavy_check_mark: | *A*      | Indicator if this is an unique index                                   |
| fieldList | :x:                | *A*      | List of field names which define columns of the index. Comma separated |

### DaoInfo

| Property         | Nullable           | xml type | Description                                                                                                                    |
|------------------|--------------------|----------|--------------------------------------------------------------------------------------------------------------------------------|
| columnName       | :heavy_check_mark: | *A*      | different column name compared to fieldName                                                                                    |
| nullable         | :heavy_check_mark: | *A*      | indicator whether the database column is nullable.                                                                             |
| length           | :heavy_check_mark: | *A*      | The column length.                                                                                                             |
| precision        | :heavy_check_mark: | *A*      | The precision for a decimal column                                                                                             |
| scale            | :heavy_check_mark: | *A*      | The scale for a decimal column.                                                                                                |
| useEnumText      | :heavy_check_mark: | *A*      | True if enum values should be stored by text and not by id                                                                     |
| columnDefinition | :heavy_check_mark: | *A*      | The SQL fragment that is used when generating the DDL for the column.                                                          |
| isLobType        | :heavy_check_mark: | *A*      | Specifies that a persistent property or field should be persisted as a large object to a database-supported large object type. |

### Reference

| Property            | Nullable           | xml type | Description                                                                                                                         |
|---------------------|--------------------|----------|-------------------------------------------------------------------------------------------------------------------------------------|
| referenceName       | :x:                | *A*      | Name of the reference                                                                                                               |
| targetEntity        | :x:                | *A*      | The baseName of the entity where to point at                                                                                        |
| shortDescription    | :heavy_check_mark: | *A*      | Short description of the reference                                                                                                  |
| filterField         | :heavy_check_mark: | *A*      | Field of enum type to filter references from one entity to another multiple times                                                   |
| filterFieldValue    | :heavy_check_mark: | *A*      | Value which should be used for filtering                                                                                            |
| isOwner             | :heavy_check_mark: | *A*      | *true* if the parent should also be the parent at database. Otherwise some connection table will be generated (Default ist *false*) |
| isList              | :heavy_check_mark: | *A*      | Indicator if a one to one relation or an one to many relation exists (Default ist *false*)                                          |
| nonOwnerFilterField | :heavy_check_mark: | *N*      | Filtering on non owner references whose target value differs from target entity values                                              |

### NonOwnerFilterField

| Property           | Nullable | xml type | Description                                                                                                         |
|--------------------|----------|----------|---------------------------------------------------------------------------------------------------------------------|
| filterFieldPackage | :x:      | *A*      | Package of the filtering enum. Only in case of filtered reference, non ownership. Will be used at connection table. |
| filterFieldType    | :x:      | *A*      | Field of enum type to filter references from one entity to another multiple times                                   |
| filterFieldValue   | :x:      | *A*      | Value which should be used for filtering.                                                                           |

### Models

Available model values.

* DOMAIN
* DAO
* DTO
* DOMAIN_DAO
* DOMAIN_DTO
* DAO_DTO
* DOMAIN_DAO_DTO

If the nothing is set, it will be interpreted as *DOMAIN_DAO_DTO*