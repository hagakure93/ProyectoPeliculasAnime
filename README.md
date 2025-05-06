# Movie API - Spring Boot Backend

![GitHub last commit](https://img.shields.io/github/last-commit/hagakure93/ProyectoPeliculasAnime)
![GitHub contributors](https://imgshields.io/github/contributors/hagakure93/ProyectoPeliculasAnime)
![GitHub issues](https://imgshields.io/github/issues/hagakure93/ProyectoPeliculasAnime)
![GitHub pull requests](https://imgshields.io/github/issues-pr/hagakure93/ProyectoPeliculasAnime)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT) ## Overview

This project provides a robust RESTful API for managing a collection of movies. It's built using the latest Spring Boot framework and follows standard practices for backend development.

**Note:** This repository currently contains only the backend API code. It does **not** include a graphical user interface (frontend).

## Technologies Used

* **Java:** Version 24
* **Framework:** Spring Boot Web
* **Build Tool:** Maven
* **Database:** MySQL
* **ORM:** Spring Data JPA & Hibernate

## Features (Backend API)

The API allows you to perform standard CRUD (Create, Read, Update, Delete) operations on movie resources:

* `GET /api/movies`: Retrieve a list of all movies.
* `GET /api/movies/{id}`: Retrieve details of a specific movie by its ID.
* `POST /api/movies`: Add a new movie to the collection.
* `PUT /api/movies/{id}`: Update details of an existing movie.
* `DELETE /api/movies/{id}`: Delete a movie by its ID.
* _[List any other specific endpoints or features you might have, e.g., search, filter]_

## Getting Started

These instructions will help you get a copy of the project running on your local machine.

### Prerequisites

* Java Development Kit (JDK) 24 or later
* Maven
* A running MySQL database instance
* Your preferred IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

### Database Setup

1.  Create a database in your MySQL instance (e.g., `movie_db`).
2.  Update the database connection properties in `src/main/resources/application.properties` (or `application.yml`) to point to your MySQL instance, database name, username, and password. Spring Data JPA/Hibernate will handle schema creation automatically based on your entities.

### Installation

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/hagakure93/ProyectoPeliculasAnime.git](https://github.com/hagakure93/ProyectoPeliculasAnime.git)
    cd ProyectoPeliculasAnime
    ```

2.  **Build the project using Maven:**
    ```bash
    mvn clean install
    ```

3.  **Run the Spring Boot application:**
    ```bash
    mvn spring-boot:run
    ```

The application should start, connect to your MySQL database, and the API will be accessible, typically at `http://localhost:8080`. Check the console output for the exact port if configured differently.

## API Usage (Examples with cURL)

Assuming the API is running on `http://localhost:8080`:

* **Get all movies:**
    ```bash
    curl http://localhost:8080/api/movies
    ```

* **Get movie by ID (e.g., ID 1):**
    ```bash
    curl http://localhost:8080/api/movies/1
    ```

* **Add a new movie:**
    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"title": "The Example Movie", "director": "Example Director", "year": 2024}' http://localhost:8080/api/movies
    ```

* **Update movie by ID (e.g., ID 1):**
    ```bash
    curl -X PUT -H "Content-Type: application/json" -d '{"title": "Updated Example Movie", "director": "Updated Director", "year": 2025}' http://localhost:8080/api/movies/1
    ```

* **Delete movie by ID (e.g., ID 1):**
    ```bash
    curl -X DELETE http://localhost:8080/api/movies/1
    ```

*(Adjust the JSON structure in POST/PUT examples to match your Movie entity fields)*

## Contribution

This project is open for contributions!

As shown in my latest YouTube video ([Enlace a tu vídeo de YouTube]), this is currently a backend-only project. I'm inviting anyone interested to contribute!

**Here's how you can help:**

1.  **Build a Frontend:** Create a graphical user interface using HTML, CSS, and JavaScript (plain JS or frameworks like React, Vue, Angular, etc.) to interact with this API.
2.  **Add New Functionalities:** Implement new features in the backend API (e.g., search by title, filter by genre, add user authentication, ratings, etc.).
3.  **Improve Existing Code:** Refactor, optimize, fix bugs, improve documentation, add tests.

To contribute, please follow the standard GitHub flow:

1.  **Fork** this repository.
2.  Create a **branch** for your changes.
3.  Make your changes and **commit** them clearly.
4.  **Push** your branch to your fork.
5.  Create a **Pull Request** from your fork to the `main` branch of this repository, explaining your changes.

All contributions are welcome and appreciated!


## Contact

If you have any questions, suggestions, or just want to connect, feel free to:

* Leave a comment on my YouTube video ([https://www.youtube.com/@0xhagakure]
* Open an Issue on this GitHub repository.
* [www.linkedin.com/in/hagakure]

---
_Happy Coding and keep on the way of the Samurai :) _
