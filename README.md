# FineBeard - E-Commerce Website for Beard Care Products

FineBeard is an e-commerce platform dedicated to offering premium beard care products, including oils, balms, and grooming tools. Developed as part of the Java Web / Spring Advanced course at SoftUni, this project showcases proficiency in Java, the Spring Framework, and modern web development practices.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Environment Variables](#environment-variables)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [Deployment with Docker](#deployment-with-docker)
- [Future Enhancements](#future-enhancements)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgements](#acknowledgements)

## Features

- **User Authentication and Authorization**
  - Secure registration and login with roles: `MASTER`, `ADMIN`, `USER`.
  - Password encryption and validation.
  - OAuth integration with GitHub (supports future addition of Google and Facebook).
  - Account activation via email with rate-limiting on activation attempts.

- **Product Management**
  - Admin panel for adding, editing, and deleting products.
  - Product categorization and search functionality.
  - Product details with image gallery and descriptions.
  - Random product rotation on the homepage via a scheduler.

- **Responsive Design**
  - Mobile-first design using Bootstrap and Thymeleaf.
  - Consistent user experience across devices.

- **Monitoring and Services**
  - Logging of product search events.
  - Rate-Limiting using Resilience4j to prevent abuse of activation attempts.
  - Currency conversion service (planned feature) using OpenExchangeRates API.
  - Email service for sending activation emails (using MailTrap for testing).
  - Schedulers for product rotation and cleaning obsolete activation data.

## Tech Stack

- **Backend**
  - Java 17
  - Spring Boot, Spring MVC, Spring Security, Spring Data JPA
  - Resilience4j for rate-limiting
  - MySQL
  - Gradle for build automation

- **Frontend**
  - Thymeleaf
  - Bootstrap
  - JavaScript (ES6), HTML5, CSS3

- **Tools & Environment**
  - Docker & Docker Compose
  - Prometheus & Grafana for monitoring
  - IntelliJ IDEA
  - Git for version control

## Prerequisites

- Java 17 or higher
- Docker & Docker Compose (for containerized deployment)
- MySQL database
- GitHub account (for OAuth integration)
- Accounts for the following services (if you wish to use them):
  - Google ReCAPTCHA
  - MailTrap
  - OpenExchangeRates API

## Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/Lirkoff/finebeard.git
   cd finebeard
   ```

2. **Configure the Database**

   Update the `application.yaml` file with your MySQL credentials:

   ```yaml
   spring:
     datasource:
       driverClassName: com.mysql.cj.jdbc.Driver
       url: jdbc:mysql://${MYSQL_HOST:localhost}:3306/finebeard?allowPublicKeyRetrieval=true&useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC
       username: ${MYSQL_USER:root}
       password: ${MYSQL_PASSWORD:}
   ```

3. **Set Environment Variables**

   Create a `.env` file in the root directory or update the existing one with your environment variables. Refer to the [Environment Variables](#environment-variables) section for details.

4. **Build the Project**

   ```bash
   ./gradlew build
   ```

5. **Run the Application**

   ```bash
   ./gradlew bootRun
   ```

6. **Access the Application**

   Open your browser and navigate to [http://localhost:8080](http://localhost:8080).

## Environment Variables

The application uses several environment variables for configuration. These can be set in the `.env` file or as system environment variables.

- **Database Configuration**
  - `MYSQL_HOST`
  - `MYSQL_USER`
  - `MYSQL_PASSWORD`

- **Security**
  - `REMEMBER_ME_KEY` (can be freely changed)
  - `MASTER_PASS` (default is `test`, can be changed)

- **Third-Party Services** (replace with your own credentials)
  - `GITHUB_CLIENT_ID`
  - `GITHUB_CLIENT_SECRET`
  - `GOOGLE_RECAPTCHA_KEY`
  - `MAILTRAP_USERNAME`
  - `MAILTRAP_PASSWORD`
  - `OPENEXCHANGERATES_API_KEY`

> **Note:** The application can run without most of these variables due to default values set in `application.yaml`. However, database variables are essential.

## Usage

- **Admin Access**

  Only users with `MASTER` or `ADMIN` roles can access the admin menu in the navbar. They can manage products, user roles, and blog articles.

- **User Registration**

  Upon registration, users receive an activation email (tested using MailTrap). The activation service handles email sending, activation code generation, and account activation. Rate-limiting on activation    attempts is enforced using Resilience4j to prevent abuse.

- **OAuth Login**

  Users can log in using their GitHub accounts. The `AuthProvider` field in `UserEntity` is set accordingly.

- **Product Rotation**

  A scheduler rotates the products displayed on the homepage by randomly selecting a subset from the available products.

- **Data Initialization**

  If the user repository is empty, an initial user (`master@example.com`) with the `MASTER` role is created. The password is set via the `MASTER_PASS` environment variable.

## Screenshots

  ![изображение](https://github.com/user-attachments/assets/c00d4242-38f6-4b02-be2e-55443b5a407e)


## Deployment with Docker

The project includes a `deployment` directory containing Docker-related files:

- **Files Included**
  - `Dockerfile`
  - `docker-compose.yaml`
  - `prometheus.yaml`
  - `.env`

- **Steps**

  1. **Navigate to the Deployment Directory**

     ```bash
     cd deployment
     ```

  2. **Start the Containers**

     ```bash
     docker-compose up
     ```

  This will start the FineBeard app on `localhost:8080`, along with MySQL, Prometheus, and Grafana. The app comes pre-loaded with dummy products, categories, and blog articles.

> **Note:** The Docker image of the app is pushed to Docker Hub, so there's no need to build it manually.

## Future Enhancements

1. **	Shopping cart integration

Add shopping cart API integration

2. **	Payment Gateway Integration

Add support for online payments using services like Stripe or PayPal.

3. **	Product Reviews

Enable users to leave reviews and ratings for products.

4. **	Wishlist Functionality

Allow users to save products for later.

## Contributing

Contributions are welcome! Please follow these steps:

1. **Fork the Repository**

2. **Create a Feature Branch**

   ```bash
   git checkout -b feature/YourFeature
   ```

3. **Commit Your Changes**

   ```bash
   git commit -m "Add YourFeature"
   ```

4. **Push to the Branch**

   ```bash
   git push origin feature/YourFeature
   ```

5. **Open a Pull Request**

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgements

- **SoftUni** – For providing the educational platform and resources.
- Resilience4j – For rate-limiting functionality.
- **ReCaptcha** – Google ReCAPTCHA for bot prevention.
- **OpenExchangeRates API** – For currency conversion (planned feature).
- **MailTrap** – For testing email services.
