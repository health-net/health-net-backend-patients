# Health-Net Backend Patients Service

Patients CRUD operations service

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

```
Java 12 (or higher)
```

### Installing

```
mvn install
```

## Deployment

1. Set up MySql database
2. Run create-local-databases.sh located in src/main/resources/scripts
3. Set environment variables
4. Run service
```
java -jar target/patients-[version]-jar-with-dependencies.jar
```

## Details
### Environment Variables
| Name                  | Description                     | Default value |
|-----------------------|---------------------------------|---------------|
| SERVICE_PORT        | The service port                | 8080          |
| DB_USER     | User for the local DB           | root          |
| DB_PASSWORD | Password to access the local DB | root          |
