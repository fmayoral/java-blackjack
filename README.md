# Blackjack Game

## Table of Contents

1. [Overview](#overview)
2. [Installation](#installation)
3. [Local deployment](#local-deployment)
4. [Tech Stack](#tech-stack)
5. [Documentation](#documentation)
6. [Contributing](#contributing)

## Overview

This is a simple Blackjack game. For those unfamiliar with Blackjack, it is a card game where the
objective is to have a hand value as close to 21 as possible but not exceeding it.

This project was created for training purposes, to showcase the use of different design patterns, principles, idioms and tools
used in real-life Java applications.

It is not particularly remarkable, but it is a clean cut of a small-medium service that might be helpful in understanding usage.

---
## Pre-requisites

* Java 11+
* Maven
* Docker

---

## Running locally

1. Clone this repository:

```
git clone https://gitlab.com/saggezza-internal-software/tech-excellence/blackjack-service.git
```

2. Navigate into the local repository directory and run:

```
mvn clean install
```

## Local deployment

1. Deploy mongodb locally using docker

```
docker run -d -p 27017:27017 --name mongodb mongo
```

2. Run the application locally:

```
mvn spring-boot:run -Dspring-boot.run.mainClass=uk.fmayoral.blackjack.Main
```

## Docker deployment
1. Create a network so that both your DB and application container can communicate
  ```
  docker network create blackjack_network
  ```
2. Deploy mongodb locally using docker
  ```
  docker run -d -p 27017:27017 --network=blackjack_network --name mongodb mongo 
  ```
3. Build the application image
  ```
  docker build -t blackjack-service .
  ```
4. Run the application image
  ```
  docker run -e SPRING_PROFILES_ACTIVE=prod -e MONGO_URI=mongodb://mongodb:27017/fmayoral -dp 127.0.0.1:8080:8080 --network=blackjack_network blackjack-service
  ```
---

## Tech Stack

### Testing
- **JUnit 5**: Unit testing framework.
- **Mockito**: Mocking framework for isolated testing.
- **JaCoCo**: Code coverage library.

### Database
- **MongoDB**: NoSQL database.

### Frameworks
- **Spring Boot Web**: Framework for creating web applications.

### Utilities
- **Lombok**: Java library that automatically plugs into your editor and build tools, spicing up your java.

### Static Code Analysis
- **SonarQube**: Continuous inspection of code quality.

### Containerization
- **Docker**: Platform for developing, shipping, and running applications.

### Application Performance Monitoring (APM)
- **OpenTelemetry**: Provides observability through tracing, metrics, and logs to monitor and troubleshoot the application's performance.

---

## Documentation
A few documents were created to explain this codebase, different patterns and implementation highlights alongside with principles applied.

* Architectural style
  * [REST](./docs/architecture/REST.md)
* Patterns
  * [Adapter](./docs/patterns/Adapter.md)
  * [Factory](./docs/patterns/Factory.md)
  * [Strategy and Command](./docs/patterns/Strategy-and-Command.md)
  * [Strategy](./docs/patterns/Strategy-classic.md)
* Principles explained
  * [SOLID](./docs/principles/SOLID.md)
* Principles implementation highlights
  * [DRY-SRP](./docs/principles/DRY-SRP.md)
  * [SRP-OCP-DIP](./docs/principles/SRP-OCP-DIP.md)
---

## Contributing

Please read [CONTRIBUTING.md](./CONTRIBUTING.md) for details on how to contribute.

---
