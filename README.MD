# Schema

## Description

Creates dynamic getter and setter functions which operate in near direct access time.

This is done by making use of the LambdaMetaFactory and adding wrappings and abstractions around it.

There is a cost to be paid in creation time which dictates that this only be used to create
schema model objects once on start-up.

This should only be used as a way to swap out reflection when heavily used multiple read/writes
to an object and when there is no suitable alternative to using reflection.

## Models

### Getter / Setter

The getter and setter objects are the lowest level abstraction and can be created using
the LambdaFactory class, or the AccessorFactory class

The getter interface is functional and contains one method get

The setter interface is functional and contains one method set

Both interfaces are type safe and specify what the model the getter and setter apply to are, 
this can be forgone by specifying as Object etc.

### Accessor

The accessor is a wrapping which implements both getter and setter

The accessor can be created through the AccessorFactory and can be created as 
read, write, or read/write (default)

Note it's recommended to use whichever mode is suitable for the underlying codebase
if there are only read operations dynamically performed use read only mode to cut creation time in half

### Schema

The schema is a representation of all the fields within a class using accessors to represent fields

The schema can be created through the SchemaFactory class and can be either read, write, or read/write (default)

The SchemaFactory uses reflection to extract the classes fields and make a schema object with them. With each layer of 
abstraction the process will incur a penalty while still being much faster than reflection. The takeaway is that if you
need faster operations and want to map the fields manually to getters and setters by hardcoded name then do so.

## Benchmarks

|Benchmark           |Mode   |Cnt  |Score   |Error     |Units |
|:------------------ |:-----:|:---:|:------:|:--------:|:----:|
|AccessorBenchmark.accessorCreationTime |avgt |25 |149910.733 |±9309.084 |ns/op |
|AccessorBenchmark.accessorGetterTime |avgt |25 |3.308 |±0.024 |ns/op |
|AccessorBenchmark.accessorSetterTime |avgt |25 |3.312 |±0.012 |ns/op |
|AccessorBenchmark.readAccessorCreationTime |avgt |25 |70321.898 |±3908.369 |ns/op |
|AccessorBenchmark.writeAccessorCreationTime |avgt |25 |67583.207 |±3701.870 |ns/op |
|GetterBenchmark.directGetterTime |avgt |25 |2.515 |±0.226 |ns/op |
|GetterBenchmark.getterCreationTime |avgt |25 |68112.995 |±3242.310 |ns/op |
|GetterBenchmark.lambdaGetterTime |avgt |25 |2.560 |±0.006 |ns/op |
|GetterBenchmark.reflectionGetterTime |avgt |25 |4.609 |±0.011 |ns/op |
|GetterBenchmark.reflectiveGetterCreationTime |avgt |25 |16.519 |±0.071 |ns/op |
|SchemaBenchmark.getterFromReflectiveMapTime |avgt |25 |8.600 |±0.028 |ns/op |
|SchemaBenchmark.readOnlySchemaCreationTime |avgt |25 |63975.810 |±3529.673 |ns/op |
|SchemaBenchmark.schemaAccessorTime |avgt |25 |5.298 |±0.295 |ns/op |
|SchemaBenchmark.schemaCreationTime |avgt |25 |131078.935 |±9073.760 |ns/op |
|SchemaBenchmark.schemaGetterTime |avgt |25 |6.249 |±0.029 |ns/op |
|SchemaBenchmark.schemaRawAccessorTime |avgt |25 |5.098 |±0.017 |ns/op |
|SchemaBenchmark.schemaSetterTime |avgt |25 |6.752 |±0.157 |ns/op |
|SchemaBenchmark.setterFromReflectiveMapTime |avgt |25 |9.808 |±0.078 |ns/op |
|SchemaBenchmark.writeOnlySchemaCreationTime |avgt |25 |64609.145 |±3566.331 |ns/op |
|SetterBenchmark.directSetterTime |avgt |25 |2.789 |±0.008 |ns/op |
|SetterBenchmark.lambdaSetterTime |avgt |25 |3.067 |±0.029 |ns/op |
|SetterBenchmark.reflectionSetterTime |avgt |25 |5.827 |±0.039 |ns/op |
|SetterBenchmark.reflectiveSetterCreationTime |avgt |25 |16.457 |±0.027 |ns/op |
|SetterBenchmark.setterCreationTime |avgt |25 |70310.621 |±3508.607 |ns/op |
