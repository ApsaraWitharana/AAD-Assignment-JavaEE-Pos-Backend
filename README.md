<a href="https://git.io/typing-svg"><img src="https://readme-typing-svg.herokuapp.com?font=Fira+Code&weight=600&size=50&pause=1000&center=true&vCenter=true&color=green&width=835&height=70&lines=POS+SYSTEM+BACKEND" alt="pos" /></a>

# POS System Backend
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![JavaEE](https://img.shields.io/badge/JavaEE-6DB33F?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/Apache%20Tomcat-F8DC75?style=for-the-badge&logo=apache-tomcat&logoColor=black)

## Project Description
The POS System Backend is a RESTful API that supports the frontend operations of a Point of Sale system. This backend service manages customer, order, and inventory data, providing essential functionalities such as creating, reading, updating, and deleting records.

## Table of Contents
- [Project Description](#project-description)
- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Features
- Manage customer records
- Process orders and track inventory
- Update item quantities
- Apply discounts and calculate totals
- Secure endpoints with authentication

## Technologies
- **Backend Framework**: Java EE
- **Database**: MySQL
- **Build Tool**: Maven
- **Application Server**: Apache Tomcat 10
- **JDK**: OpenJDK 17

## Installation
### Prerequisites
- Java 17 (OpenJDK 17)
- Maven
- MySQL
- Apache Tomcat 10

### Steps
1. **Clone the repository - Backend**
    ```bash
    git clone https://github.com/ApsaraWitharana/AAD-Assignment-JavaEE-Pos-Backend.git
  
    ```
   **Clone the repository -Frontend**
    ```bash
   git clone https://github.com/ApsaraWitharana/AAD-Assignment-JavaEE-Pos-Frontend.git
  
    ```



2. **Configure the database**
    - Create a MySQL database named `pos_system`
    - Update the `persistence.xml` file with your MySQL credentials
    ```xml
    <properties>
        <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/pos_system"/>
        <property name="javax.persistence.jdbc.user" value="your_username"/>
        <property name="javax.persistence.jdbc.password" value="your_password"/>
        <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
        <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL8Dialect"/>
        <property name="hibernate.hbm2ddl.auto" value="update"/>
    </properties>
    ```

3. **Build the project**
    ```bash
    mvn clean install
    ```

4. **Deploy to Tomcat**
    - Ensure Tomcat is installed and running.
    - Copy the generated WAR file from the `target` directory to the Tomcat `webapps` directory.
    - Restart Tomcat.

## Usage
### Running the Server
After installation, run the server by starting Tomcat.

## Project Structure

### Back-end

The back-end code is organized to follow best practices and maintainability. Important directories and files include:

- src/main/java: Directory containing Java classes.
- src/main/resources/schema: Database schema.
- src/main/resources/File: Tomcate Text File.
- src/main/webapp/WEB-INF/: Configuration files for the JavaEE application.

#### Project Packages

The back-end codebase is further organized into the following packages:

(src/main/java/lk.ijse.gdse68/pos_system_back_end)

- **api**: Contains classes defining the API endpoints and services.
- **bo**: Business Objects - classes that encapsulate business logic.
- **dao**: Data Access Objects - classes responsible for database interactions.
- **entity**: Entity classes representing database tables.
- **dto**: Data Transfer Objects - classes used for data exchange between layers.
- **filter**: Contains classes implementing filters for intercepting and processing requests.

## Getting Started

To set up and run the Shop Management project locally, follow these steps:

&nbsp;1. Clone the repository.  
&nbsp;2. Set up the back-end dependencies.  
&nbsp;3. Configure the database connection.  
&nbsp;4. Deploy the JavaEE application on the Apache Tomcat server.


## Usage

Once the back-end application is running, users can seamlessly interact with the front-end without interruptions. The system consists of four main pages: Customer, Items, Place Order, and Order Details. Users can effortlessly navigate through these pages to perform various actions and tasks.

## Dependencies

#### Back-end

- Java EE : Enterprise Edition of the Java platform for building robust and scalable enterprise applications. 
- Apache Tomcat : Servlet container that implements the Java Servlet and JavaServer Pages technologies. (Version 10.1.24)

#### Database

- MySQL Connector : Java-based driver for connecting to MySQL databases. (Versoin 8.0.33)
- Java Naming and Directory Interface (JNDI): Java API for connecting to directory services, used for managing database connections efficiently through connection pooling, enhancing performance and scalability.

#### Development Tools

- Maven : Build automation and project management tool. (Version 4.0.0)

### Accessing the API
The API will be available at `http://localhost:8080/`.

## API Documentation
For detailed API documentation, please refer to the project’s Swagger UI available .

This documentation provides interactive access to the following APIs and Refer to the project Documentations:

- **Customer API documentation URL**  [https://documenter.getpostman.com/view/35385905/2sA3rxrZm7]
- **Item API documentation URL**  [https://documenter.getpostman.com/view/35385905/2sA3rxrZm1]
- **Order API documentation URL**  [https://documenter.getpostman.com/view/35385905/2sA3rxrZgi]
- **Order Details API documentation URL**  [https://documenter.getpostman.com/view/35385905/2sA3rxrZgg]


## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

<div align="center">

#### This project is licensed under the [MIT License](LICENSE)

#### © 2024 All Right Reserved, Designed By [Sachini Apsara](https://github.com/ApsaraWitharana)

</div>



 
