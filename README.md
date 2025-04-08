# FusionTech MVC
FusionTech MVC is a full-stack e-commerce web application built with Java and Spring Boot using the MVC (Model-View-Controller) 
architecture. It integrates dynamic frontend features using Thymeleaf, HTML, CSS, and JavaScript.The project provides user-facing 
features such as product browsing, admin panel management, shopping cart, order placement, customer reviews, and product ratings.


## Technologies and Tools Used
- **Java 17**
- **Spring Boot (MVC)**
- **Spring Security** – form-based login & role-based access control
- **Thymeleaf** – server-side template engine
- **PostgreSQL** – database
- **Lombok** – reduces boilerplate code
- **HTML, CSS, JavaScript** – user interface and interactivity
- **Validation (Jakarta)** – form input validation
- **DTOs and ModelMapper** – data transfer and mapping

## Key Features
- User registration and login
- Role-based access control (Admin/User)
- Product listing, search and detail view
- Product ratings and review functionality
- Shopping cart and order placement
- Admin panel to manage products, users, categories
- Product rating and review system
- Image upload and preview
- Form validation and error handling

## Installation and Setup
> Prerequisites:
> - Java 17+
> - Maven 3.9+
> - PostgreSQL database

1. Clone the repository:
   ```bash
   git clone https://github.com/Chingiz-Taghili/fusiontech-mvc.git
   ```
2. Configure your PostgreSQL settings in application.yml or application.properties:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/fusiontech
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   ```
3. Run the project via terminal:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Open the application in your browser: [http://localhost:8080](http://localhost:8080)

## Note 
This project was developed for portfolio purposes and demonstrates a complete Spring Boot MVC application with user interaction, 
admin dashboard, and essential e-commerce features.

## Author
Chingiz Taghili  
chingiz.taghili@gmail.com  
GitHub: @Chingiz-Taghili
