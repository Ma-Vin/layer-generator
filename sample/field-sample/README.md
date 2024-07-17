# Example for usage of `fields`

A small example to show the usage of entity with configuration
at [fieldExampleModel.yaml](./src/main/resources/fieldExampleModel.yaml)

## Fields

* `someString` a normal field of `String` type
* `someInteger`a normal field of `Integer` type
* `someEnum` a field of a custom enum type
* `someCustom` a field of a custom class type 
* `onlyDao` a field which is only defined at data access object
* `onlyDomain` a field which is only defined at domain object
* `onlyDto` a field which is only defined at data transport object
* `daoAndDomain` a field which is defined at data access and domain object
* `dtoAndDomain` a field which is only defined at data transport and domain object
* `textWithDaoInfo`: a field of `String` type which is not nullable and a max length at data access object
* `numberWithDaoInfo`: a field of `double` type which has a precision and scale at data access object
* `daoEnum` a field of a custom enum type and its `EnumType` will be assumed as `ORDINAL`
* `daoEnumWithText` a field of a custom enum type and its `EnumType` will be assumed as `STRING` (the behavior is equal to `someEnum`)
* `someName` a field which is mapped to other named column
* `document` a sting field with a description and `BLOB` type at database