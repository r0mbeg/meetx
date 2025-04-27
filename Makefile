include .env

BACKEND_IMAGE_NAME ?= meetx-backend
FRONTEND_IMAGE_NAME ?= meetx-frontend

BACKEND_VERSION ?= 0.0.1
FRONTEND_VERSION ?= 0.0.1

BACKEND_IMAGE = ghcr.io/r0mbeg/${BACKEND_IMAGE_NAME}:${BACKEND_VERSION}
FRONTEND_IMAGE = ghcr.io/r0mbeg/${FRONTEND_IMAGE_NAME}:${FRONTEND_VERSION}

POSTGRES_IMAGE=postgres:16.0-bullseye
PGADMIN_IMAGE=dpage/pgadmin4:8.3

export

.PHONY: all build-backend dev-up-postgres

all: build-backend build-frontend

build-backend:
	@echo "Building Docker image (with JAR build inside)..."
	docker build -t ${BACKEND_IMAGE} -f backend/Dockerfile backend
	@echo "\nImage ${BACKEND_IMAGE} ready!"


dev-up-backend:
	docker compose -f compose.yml -f compose.dev.yml up postgres backend pgadmin -d

dev-up-postgres:
	docker compose -f compose.yml -f compose.dev.yml up postgres pgadmin -d

.PHONY: help
help:
	@echo "Available targets:"
	@echo "  all             - Build all components"
	@echo "  build-backend   - Build backend only"
	@echo "  dev-up-backend  - Start development environment"
	@echo "  dev-up-postgres - Start development database and DBMS pgadmin"
