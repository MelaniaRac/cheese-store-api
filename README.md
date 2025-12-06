# Cheese Store Management API

This project is a Spring Boot REST API for managing cheese products. It demonstrates the use of Spring Data JPA, MapStruct for DTO mapping and basic security using HTTP Basic authentication. The application targets Java 17.

## Features

- CRUD operations on `CheeseProduct` entities with DTO mapping via MapStruct.
- Role based endpoints protected via Spring Security (`ADMIN` and `USER` roles).
- Global exception handling for custom exceptions
- Basic stock level checks which trigger warning messages when stock is low.
- Example JUnit tests using Mockito
- Warnings, other security-related features

## Business requirements
- If a product reaches stock=0, it remains in the database - only the admin can choose to remove it from the db


## Requirements

- Java 17
- Maven (or use the provided Maven Wrapper `./mvnw`)
- MySQL (connection details configured in `src/main/resources/application.properties`)

## Running the application

1. Configure your MySQL instance in `application.properties`.
2. Build and run with Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
   The API runs on port `2028` by default.

## API Endpoints

The base path is `/cheese`.

| Method & Path | Access | Description |
|---------------|--------|-------------|
| `POST /cheese/create` | `ADMIN` | Add a cheese product. |
| `GET /cheese/{name}` | `USER` or `ADMIN` | Retrieve a cheese by name. |
| `GET /cheese/{stockUnits}/{retailPrice}` | `USER` or `ADMIN` | Get cheeses with stock units and price below the given values. |
| `PUT /cheese` | `ADMIN` | Update the price of a cheese product. |
| `DELETE /cheese/{stockUnits}` | `ADMIN` | Delete cheeses whose stock units match the given value. |

Authentication uses HTTP Basic. Example credentials are defined in `SecurityConfig`.

## Testing

Run all tests using Maven:

```bash
./mvnw test
```

JUnit tests with Mockito cover service and stock logic.

## Notes

- MapStruct generates mappers at build time.
- The code includes TODO comments for input validation improvements (positive values for stockUnits and retailPrice)
- and better duplicate-handling messages (Price-only, duplicate entry)
- Screenshots of API usage can be found in the `Screenshots different experiments` directory.
- comments from the code may cover what I forgot here!

## Future Work

- Work in Progress (see latest commits): Introduce an `Order` entity to capture purchases of cheese products, including
  quantity, order date and total cost + service method for sales/day

## Resources
- Inspired by the three words every woman wants to hear: "Cheese is available"
- https://www.produits-laitiers-aop.fr/en/products/
