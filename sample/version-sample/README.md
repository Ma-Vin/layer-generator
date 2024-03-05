# Version sample
This maven projects contains an example of version usage

## Model definition
The example is defined by [exampleModel.yaml](src/main/resources/exampleModel.yaml)
This model contains four entities. One root entity has a relation to each of the other three. 
The root and one of the child entity are versioned.
The versions are using the following features
 * Reference between versioned entities
 * Reference between a versioned and a non versioned entity
 * Reference between versioned entities, but they have different version ids
 * Added and removed fields
 * Added and removed references