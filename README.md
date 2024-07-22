# Inventory Management System

## Overview

The Inventory Management System is a JavaFX application designed to manage inventory for a business. This application allows users to track and manage products, suppliers, and inventory levels. It includes features for adding, updating, and removing inventory items, as well as generating reports.

## Features

- **User Authentication**: Secure login and user management.
- **Product Management**: Add, update, and delete products.
- **Supplier Management**: Manage supplier information.
- **Inventory Tracking**: Track inventory levels and update stock.
- **Reporting**: Generate inventory reports and export data.
- **JavaFX GUI**: User-friendly graphical interface for interacting with the system.

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- JavaFX SDK (included in the Maven dependencies)

## Getting Started

### Clone the Repository

Clone the repository to your local machine:

```bash
git clone https://github.com/yourusername/inventory-management-system.git
cd inventory-management-system
```

### Build the Project

Navigate to the project directory and build the project using Maven:

```bash
mvn clean install
```

### Run the Application

After building the project, you can run the application using the Maven `exec` plugin:

```bash
mvn javafx:run
```

### Running the Application from IDE

If you are using an IDE such as IntelliJ IDEA or Eclipse, you can import the project
as a Maven project and run the `Main` class directly

## Project Structure

- `src/main/java`: Contains Java source code files.
  - `com.example.controller`: Controllers for handling user interactions.
  - `com.example.model`: Data model classes for inventory, user, and suppliers.
  - `com.example.service`: Services for business logic and database interactions.
  - `com.example`: Main application entry point.
- `src/main/resources`: Contains FXML files, CSS stylesheets, and other resources.
- `pom.xml`: Maven configuartion file that defines project dependencies and build settings.

## Configuration

The application uses a postgreSQL database through supabase by default. You can
configure the database connection in the `src/main/java/com/example/database/DatabaseConnection.java` file.

## Dependencies

- `JavaFX`: For building the graphical user interface.
- `PostgreSQL`: for database management.
- `Maven`: For project management and build automation.
