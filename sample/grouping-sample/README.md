# Example for usage of `groupings`

A small example to show the usage of groupings with configuration
at [groupingExampleModel.yaml](./src/main/resources/groupingExampleModel.yaml)

There is a normal entity `CommonEntity` which uses the default package defined at `basePackage` with its
elements `dtoPackage`, `domainPackage` and `daoPackage` (dao will be empty because only `DOMAIN_DTO` models are
configured).

In addition, the entity `SubEntity` is defined at grouping `sub`. Its object classes are generated
to [.../domain/sub/SubEntity.java](./src/main/generated/plugin/java/com/github/ma_vin/util/layer_generator/sample/extension/content/domain/sub/SubEntity.java)
and [.../dto/sub/SubEntityDto.java](./src/main/generated/plugin/java/com/github/ma_vin/util/layer_generator/sample/extension/content/dto/sub/SubEntityDto.java)
(dao will be empty because only `DOMAIN_DTO` models are configured).