# Devices Catalogue Manager

This project is a Spring Boot application for managing a device database. It includes functionalities for adding, updating, deleting, and searching devices.

## Features

- Add a new device
- Get device by ID
- List all devices
- Update device (full and partial)
- Delete a device
- Search devices by brand

## Technologies

- Spring Boot
- Spring Data JPA (for the H2 version)
- H2 Database (in-memory, for the H2 version)
- ConcurrentHashMap (for the in-memory version)
- Swagger/OpenAPI for API documentation
- Docker

## Versions

### Version 1: H2 Database

- **Database**: Uses H2 in-memory database.
- **Persistence**: Uses Spring Data JPA for database operations.
- **Docker Setup**: Provides a Dockerfile and docker-compose.yml for running the application with an H2 database.

### Version 2: In-Memory Storage

- **Storage**: Uses an in-memory `ConcurrentHashMap` for device storage.
- **Persistence**: No database is used; device data is stored directly in memory.
- **Concurrency**: Includes locking mechanisms to handle concurrent updates and partial updates.
- **Docker Setup**: Provides a Dockerfile and docker-compose.yml for running the application.

## Switching Between Versions

To switch to the in-memory storage version, follow these steps:

1. **Clone the repository** (if you haven't already):

    ```sh
    git clone https://github.com/iftikha1122/device-catalogue-manager.git
    cd devices-catalogue-manager
    ```

2. **Switch to the in-memory storage version branch**:

    ```sh
    git checkout in-memory-device-storage-version
    ```

3. **Build the project**:

    ```sh
    ./gradlew build
    ```

4. **Run the application**:

    ```sh
    ./gradlew bootRun
    ```

   By default, the application will run on [http://localhost:8080](http://localhost:8080). This version uses `ConcurrentHashMap` for storing device data in memory.

### Running the H2 Database Version

1. **Clone the repository** (if you haven't already):

    ```sh
    git clone https://github.com/iftikha1122/device-catalogue-manager.git
    cd devices-catalogue-manager
    ```

2. **Switch to the H2 database version branch** (if you're not already on it):

    ```sh
    git checkout main
    ```

3. **Build the project**:

    ```sh
    ./gradlew build
    ```

4. **Run the application**:

    ```sh
    ./gradlew bootRun
    ```

   By default, the application will run on [http://localhost:8080](http://localhost:8080).

5. **Docker Setup**:

   **Start the Docker container**:

    ```sh
    docker-compose up
    ```

   This will build and start the container to run application on docker.

   **Stop and remove the Docker container**:

    ```sh
    docker-compose down
    ```

   This will stop and remove the running container.

### API Documentation

Swagger/OpenAPI documentation is available at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

### Testing

**Unit Tests**:
Unit tests are located in `src/test/java` and can be run using:

```sh
./gradlew test
