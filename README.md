# Fibonacci API

This is a project for a REST API in Java that calculates the n-th Fibonacci number and stores the results in a relational database to improve response times using a caching mechanism. Additionally, it saves statistics on the most frequently queried numbers.

## Technologies Used

- **Java 17**: Programming language.
- **Spring Boot 3.3.3**: Framework for building web applications.
- **Spring Data JPA**: Data access abstraction using ORM.
- **H2 Database**: In-memory relational database.
- **JUnit 5**: Framework for unit testing.
- **Mockito**: Framework for testing components and services.
- **Maven**: Build and dependency management tool.

## Requirements

To run this project, you need to have:

- **Java 17** or higher.
- **Maven 3.6.0** or higher.

## Installation

1. Clone this repository to your local machine:

    ```bash
    git clone https://github.com/IgnacioPerez22/Fibonacci-api.git
    cd Fibonacci-api
    ```

2. Compile and build the project using **Maven**:

    ```bash
    mvn clean install
    ```

## Execution

To run the application locally, use the following **Maven** command:

```bash
mvn spring-boot:run
```
The application will be available at http://localhost:8080.
## Usage:

**Fibonacci Endpoint**
You can calculate the n-th Fibonacci number by sending a GET request to the following endpoint:

```bash
GET http://localhost:8080/fibonacci/{n}
```
Replace {n} with the number for which you want to calculate the Fibonacci number.

**Statistics Endpoint**
To get the statistics of the most frequently queried Fibonacci numbers, use the following endpoint:

```bash
GET http://localhost:8080/fibonacci/statistics
```

## Usage Examples with Postman:

**Calculate Fibonacci:**
```bash
Method: GET
URL: http://localhost:8080/fibonacci/3
Response: {2}
```

**Query Statistics:**
```bash
Method: GET
URL: http://localhost:8080/fibonacci/statistics
Response: [{"id": 3,"fibonacciValue": 2,"queryCount": 0}]
```

## Testing
To run the automated tests, use the following Maven command:

```bash
mvn test
```
This will execute the tests and generate a coverage report using JaCoCo.

Author
Ignacio Pérez
