# Example for usage of `references`

A small example to show the usage of references with configuration
at [referenceExampleModel.yaml](./src/main/resources/referenceExampleModel.yaml)

* A reference from `SourceEntityOneToOne` to `TargetEntityOneToOne` with a cardinality of 1:1
* A reference from `SourceEntityOneToMany` to `TargetEntityOneToMany` with a cardinality of 1:n
* References from `SourceEntityManyToMany` and `SourceEntityManyToOne` to `TargetEntityManyToMany` with cardinality of
  m:n and m:1
* References from `SourceEntityFilter` to `TargetEntityFilter` which are filtered by different enum values
* References from `SourceEntityFilterNotAtTarget` to `TargetEntityFilterNotAtTarget` which are filtered by
  different enum values, but the filtered field is stored at some connection table and is not a property
  of `TargetEntityFilterNotAtTarget`