# Meetx
Application for organizing private events. Refactored project from [this](https://github.com/forafox/Sber-Teambuilding) repository: backend and frontend are united into a single project, optimized and improved containerization, more convenient assembly (via make) and etc.

## Current functionality (backend)
* Registration and authorization of members
* Searching for members
* Creating and searching for events

## Techstack
### Backend
* Java Spring Boot (Data JPA, Security, Validation, etc.)
* PostgreSQL, Pgadmin
* OpenAPI (Swagger) documentation
### Frontend
* Typescript React ...
* Vite

## Deploying backend
* Create an .env file based on .env.example
* Build an image: `make build-backend`
* Deploy containers in test mode: `make dev-up-backend`
* Stop all containers: `dev-down`
* Description of all make targets: `make help`




