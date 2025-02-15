## Project Overview

This project is a web application for managing employee information. It is developed using the Vaadin framework and Spring Boot. The system includes employee addition, listing, updating, and soft-delete operations.

## Technologies Used

- Java 21
- Spring Boot 3.2.2
- Vaadin 24.3.3
- H2 Database
- JPA/Hibernate
- Lombok
- Maven

## System Architecture

The project is developed following the Layered Architecture principles:

1. **Presentation Layer (UI)**: Vaadin views
2. **Service Layer**: Business logic operations
3. **Repository Layer**: Database operations
4. **Entity Layer**: Data models

## EmployeeView Detailed Review

EmployeeView is the main page of the system and includes the following features:

### 1. Employee Listing
- Display of employees using a Grid structure
- Columns: First Name, Last Name (TC ID number is not displayed for privacy reasons)
- Sortable grid columns

### 2. Search Feature
- Real-time search by name
- Case-insensitive search
- Lazy loading support
- Searches only among active employees (Soft-deleted employees are not shown)

### 3. Add New Employee
- Dialog-based form structure
- Validation controls:
    - TC ID number check (11 digits)
    - Mandatory field checks
    - Duplicate record check
- Error messages and successful operation notifications

### 4. Update Employee
- Double-clicking an employee on the grid opens the update dialog
- Dialog content:
    - First Name update field
    - Last Name update field
    - TC ID number is not editable
- Validation controls:
    - Mandatory first and last name
    - Minimum character check
- Successful update notification
- Cancel button to close the dialog

### 5. Soft-Delete Operation
- Employees are not physically deleted
- Deleted employees are retained in the database
- Confirmation dialog before deletion
- Reversible delete operation

## Dummy Data Management

When the system first runs, sample data is automatically loaded by the DataInitializationConfiguration class:

- Executes at application startup using `@PostConstruct` annotation
- Reads data from `employees.txt` file
- Parses CSV formatted data (TC ID, First Name, Last Name)
- Creates a new Employee object for each employee
- Saves created employees to the database
- All employees are added as active by default (status=1)

## Key Features

### Soft-Delete Mechanism
- Prevents data loss
- Ensures data integrity for historical records
- Useful for reporting
- Preserves data for statistical analysis

### Validation System
- TC ID format check
- Mandatory field checks
- Custom error messages

### Notification System
- Notifications for operation results
- Error messages
- Success notifications
- Confirmation messages