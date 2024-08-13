# BeardCare - E-Commerce Website for Beard Care Products



## Project Overview

**BeardCare** is an e-commerce website dedicated to selling premium beard care products. This project was developed as part of the Java Web / Spring Advanced course at SoftUni. The website provides users with a seamless shopping experience, offering a wide range of beard care products, including oils, balms, and grooming tools.

The project demonstrates proficiency in Java, the Spring Framework, and modern web development practices, incorporating essential features such as user authentication, product management, and order processing. It also integrates MySQL for data storage and uses Thymeleaf for server-side rendering of dynamic content.

## Key Features

- **User Authentication and Authorization**: 
  - Secure user registration and login with roles (e.g., Admin, User).
  - Password encryption and validation.

- **Product Management**: 
  - Admin panel for adding, editing, and deleting products.
  - Product categorization and search functionality.
  - Product details view with image gallery and detailed descriptions.

- **Responsive Design**: 
  - Fully responsive layout optimized for mobile, tablet, and desktop devices.
  - Consistent user experience across all devices.

- **Database Management**: 
  - MySQL used for storing user, product, and order data.
  - Efficient data retrieval and manipulation through JPA/Hibernate.

- **Spring Framework Integration**: 
  - Spring Boot for application setup and dependency management.
  - Spring MVC for handling web requests and mapping.
  - Spring Security for managing authentication and authorization.
  - Spring Data JPA for interacting with the database.

- **Front-End Technologies**:
  - Thymeleaf for server-side rendering and dynamic content management.
  - JavaScript for client-side interactivity and AJAX requests.
  - Bootstrap for responsive design and UI components.

## Tech Stack

- **Backend**: 
  - Java 17
  - Spring Framework (Spring Boot, Spring MVC, Spring Security, Spring Data JPA)
  
- **Frontend**: 
  - Thymeleaf
  - JavaScript (ES6)
  - HTML5, CSS3, Bootstrap

- **Database**: 
  - MySQL

- **Tools & Environment**: 
  - Gradle for project management and build automation.
  - IntelliJ IDEA as the integrated development environment (IDE).
  - Git for version control.

## Setup and Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Lirkoff/finebeard.git
   ```

2. **Navigate to the project directory**:
   ```bash
   cd finebeard
   ```

3. **Configure the database**:
   - Update the `application.yaml` file with your MySQL credentials:
     ```properties
     spring:
      datasource:
        driverClassName: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://${MYSQL_HOST:localhost}:3306/finebeard?allowPublicKeyRetrieval=true&useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC
        username: ${MYSQL_USER:root}
        password: ${MYSQL_PASSWORD:}
     ```

4. **Access the website**:
   - Open your browser and go to `http://localhost:8080`.

## Contribution

Contributions are welcome! Please fork the repository and create a pull request with your proposed changes. For major changes, open an issue to discuss what you would like to improve.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.



