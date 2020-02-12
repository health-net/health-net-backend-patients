# Health-Net Backend Patients Service

Patients CRUD operations service

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

```
node.js
```

### Installing

Install npm project dependencies
```
npm install
```

## Deployment

1. Set up a MySQL database on your machine
2. Run create-local-databases.sh located in src/main/resources/scripts
3. Set environment variables inline and run service

## Details
### Environment Variables
| Name                  | Description                     | Default value |
|-----------------------|---------------------------------|---------------|
| HEALTHNET_PORT        | The service port                | 8080          |
| HEALTHNET_DB_USER     | User for the local DB           | root          |
| HEALTHNET_DB_PASSWORD | Password to access the local DB | root          |
