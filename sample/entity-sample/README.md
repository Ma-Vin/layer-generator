# Example for usage of `entities`

A small example to show the usage of entity with configuration at [entityExampleModel.yaml](./src/main/resources/entityExampleModel.yaml)

## Basic
A normal basic entity is given by `RootEntity` which is defined for all model `com.github.ma_vin.util.layer_generator.config.elements.Models`

## Abstract and extend it
There are two entities `AbstractEntity` and `ExtendingEntity`. The first one is of abstract type and the second one extend it, so it inherit all possible attributes and references

## Derived data transport object
The entity `DerivedEntity` is derived by `RootEntity` and has to define which should inherit. 
In comparison to `AbstractEntity` and `ExtendingEntity` not all properties will be inherited and it has to be a data transport model.
The mapping of data transport and domain model will take place between `DerivedEntityDto` and `RootEntity`.