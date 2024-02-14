![Maven Workflow Action](https://github.com/Ma-Vin/de.ma_vin.util.layerGenerator/actions/workflows/maven.yml/badge.svg)

# Base Layer Generator

Code generator to create domain model, data transport or data access objects.

## Model formats

It is possible to define the model with a **xml**, **yaml/yml** or **json** file. At the following the xml variant is
used to describe the configuration elements.
There exists four test resources which define an equal model:

- [exampleModel.xml](src/test/resources/references/config/exampleModel.xml)
- [exampleModel.yaml](src/test/resources/references/config/exampleModel.yaml)
- [exampleModel.yml](src/test/resources/references/config/exampleModel.yml)
- [exampleModel.json](src/test/resources/references/config/exampleModel.json)

## Model definition

The following description refers to the xml format because it contains nodes and attributes element types in contrast to
yaml and json The xsd generation based on [Config](src/main/java/de/ma_vin/util/layer/generator/config/elements/Config.java)

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
| versions             | :heavy_check_mark: | *N*      | List of versions for this entity                                                                                                                                                                   |

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

### Version

A version can be used to generate multiple additional ***versions of the data transport objects***, which have different
fields or reference (by adding or removing them) in comparison to the original entity.
***Domain and data access objects remain versionless!*** Because of this, converting methods between versioned data
transport and domain objects will be generated at transport mapper classes.

A default mapping is realized for shared field and references. Added fields and references are to map manually by an
extension of the specific transport mapper class.

| Property              | Nullable           | xml type | Description                                                                                                          |
|-----------------------|--------------------|----------|----------------------------------------------------------------------------------------------------------------------|
| versionName           | :heavy_check_mark: | *N*      | Name of the versioned entity. If not set, it will be generated from entity name and versionId.                       |
| versionId             | :x:                | *A*      | Identification of the version                                                                                        |
| baseVersionId         | :heavy_check_mark: | *A*      | Id of the version which is to use as reference to derive difference from. If not set, the owner entity will be used. |
| removedFieldNames     | :heavy_check_mark: | *N*      | List of field names which are to remove in comparison to the base version                                            |
| addedFields           | :heavy_check_mark: | *N*      | List of fields which are to add in comparison to the base version                                                    |
| removedReferenceNames | :heavy_check_mark: | *N*      | List of reference names which are to remove in comparison to the base version                                        |
| addedReferences       | :heavy_check_mark: | *N*      | List of reference which are to add in comparison to the base version                                                 |

#### AddedField

This is a special reduced variant of *Field* and has only the following properties

* fieldName
* type
* typePackage
* isTypeEnum
* shortDescription
* description

#### AddedReferences

This is a special reduced variant of *Reference* and has only the following properties

* referenceName
* targetEntity
* shortDescription
* isOwner

In addition, it is possible to point to a different version id than the source entity of the reference by setting the
optional attribute *divergentTargetVersion*.

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