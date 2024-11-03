# Hatio tudo  - Task Management System

Hatio tudo  is a backend-only Task Management System developed using Spring Boot. This application allows for efficient management of tasks within projects, including creating, retrieving, updating, and deleting tasks. It integrates with GitHub for exporting project

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Database Schema](#database-schema)


## Introduction

Hatio tudo  is designed to manage tasks within projects efficiently. It provides functionalities to manage tasks and export project summaries as GitHub gists. The backend application is built with Spring Boot.

## Features

- **Task Management**:
    - Create new tasks
    - Retrieve tasks by project
    - Update task details
    - Delete tasks from projects

- **Project Reporting**:
    - Export project summaries as GitHub gists

## Technologies Used

- **Spring Boot**: Framework for building Java applications.
- **Spring Security**: Provides authentication and authorization.
- **JWT Authentication**: Secure token-based authentication mechanism.
- **MySQL**: Relational database management system.
- **GitHub Gists**: For exporting project summaries.

## Getting Started

### Prerequisites

- **Java 17 or higher**: Install Java Development Kit (JDK) from [Oracle's official website](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).
- **Maven**: Download from [official Maven website](https://maven.apache.org/download.cgi) or install via your package manager.
- **MySQL**: Download and install from [official MySQL website](https://dev.mysql.com/downloads/).


### Installation

1. **Clone the repository**

   ```
   git clone https://github.com/abhijithajithkumarj/hatio-tudo-task.git
   ```


2. **Configure the database**
   Edit` src/main/resources/application`.properties to configure your MySQL database  You need to set the following properties:
    ```
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
   spring.datasource.username=your_database_username
   spring.datasource.password=your_database_password

   `````

4. **Build the project**
   ```
   mvn clean install
   ```

5. **Run the application**
   ```
   mvn spring-boot:run


## Exporting Project Reports

The TaskStream application allows for exporting project summaries as GitHub gists. The exported gist includes:

### File Name
`<Project title>.md`

### Content
1. **Project Title**

## Summary
- **Completed Todos:** <No. of completed todos>
- **Total Todos:** <No. of total todos>
### Pending Todos
- [ ] Todo 1
- [ ] Todo 2
- [ ] Todo 3
### Pending Todos
- [ ] Todo 1
- [ ] Todo 2
- [ ] Todo 3



  


