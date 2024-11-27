# Todo Hexagonal

## Project Objective

This project provides backend APIs for managing a to-do list application.

The goal of this project is to implement the principles of **Hexagonal Architecture**, also known as **Ports and
Adapters architecture**.   
This architectural style promotes a clean separation of concerns, making the application flexible, testable, and
adaptable to changes in technology or business requirements.

### Technologies

    Java 21  
    Spring Boot 3

### API Documentation

The API specifications are available in the form of [API Blueprint](https://todosbackend.docs.apiary.io/), offering a
clear and structured reference for all
endpoints. Please refer to the documentation to understand how to interact with the API.

### Database Setup

To run the database locally using Docker, execute the following command:

    docker-compose up -d

This will initialize the database container in detached mode. Ensure Docker and Docker Compose are installed on your
system before running the command.

Database credentials are available in the `application.yml` file.

Adminer is available on localhost:8081 to manage the database.

### Swagger UI

The Swagger UI is available to interact with the API endpoints. To access the Swagger UI, navigate to the following URL:

    http://localhost:8080/swagger-ui.html 

### Running the Application

Fist of all You need to init the database using the following command:

    docker-compose up -d

You'll need to build the project using the following command:

    ./mvnw clean install

Then, you can run the application using the following command:

    ./mvnw spring-boot:run

## Hexagonal Architecture Overview

Hexagonal Architecture organizes the application into three primary layers:

### Domain (Inside Layer):

Contains the business logic, entities, and use cases.
This layer is technology-agnostic and represents the core of the application, independent of external frameworks.

### Ports (Boundary):

Define interfaces or contracts for communication with the external world.
Examples include repositories, services, or messaging interfaces. These ports act as entry points (e.g., API calls) or
exit points (e.g., database operations) for the application.

### Adapters (Outside Layer):

Implement the ports to interact with external systems such as databases.