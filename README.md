# datastax-dmbe-investment

This is an Spring Boot application that implements the [Investment Portfolio Data Modeling](https://www.datastax.com/learn/data-modeling-by-example/investment-data-model) from the DataStax Data Modeling by Example Coursework. 

For simplicity we have both backend and frontend components built into this one application, but we intent to have a clear deliniation of these 2 components. 

## TODO
* http session with username
* test suite order
* timeuuid to date in UI
* integrate symbol price into ui

## Data Platform
We are using Astra not only as our Cassandra / NoSQL datastore, but as our data platform.

## Key libraries used

* lombok
* jayway

## Backend
Currently one big services, but design to be split into individual microservices

We exercise different Astra API and access patterns. We use Spring Data Cassandra and the DataStax drivers for CQL access, but we also explore the Astra GraphQL API interface to showcase the simplification of creating data access APIs. 

## Frontend
Currently using Spring Boot Web with Thymeleaf and bootstrap, but keeping most of the logic in the backend for resuability. 

