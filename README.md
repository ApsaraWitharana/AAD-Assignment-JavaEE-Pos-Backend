<a href="https://git.io/typing-svg"><img src="https://readme-typing-svg.herokuapp.com?font=Fira+Code&weight=600&size=50&pause=1000&center=true&vCenter=true&color=green&width=835&height=70&lines=POS+SYSTEM+BACKEND" alt="pos" /></a>

# POS System Backend


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
1. **Clone the repository**
    ```bash
    git clone https://github.com/yourusername/pos-system-backend.git
    cd pos-system-backend
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

### Accessing the API
The API will be available at `http://localhost:8080/`.

### Example Endpoints
- **GET /customers**: Retrieve all customers
- **POST /orders**: Create a new order
- **PUT /items/{id}**: Update item information

## API Documentation
For detailed API documentation, please refer to the project’s Swagger UI available at:


## Contributing
### How to Contribute
1. Fork the repository
2. Create a new branch (`git checkout -b feature-branch`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature-branch`)
5. Open a pull request

### Code of Conduct
Please follow the [Code of Conduct](CODE_OF_CONDUCT.md) for contributing.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

<div align="center">

#### This project is licensed under the [MIT License](LICENSE)

#### © 2024 All Right Reserved, Designed By [Sachini Apsara](https://github.com/ApsaraWitharana)

</div>



 
