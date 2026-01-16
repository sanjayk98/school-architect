# 🏫 School Architect

**School Architect** is a production-ready school management system built with **Domain-Driven Design (DDD)** principles. It provides a comprehensive suite for K-12 school configuration, featuring AI-powered automation and a modern tech stack.

## 🚀 Project Components

The repository is organized into several key modules:

| Component | Technology Stack | Description |
| :--- | :--- | :--- |
| **Backend** | Java 17, Spring Boot 3.2, JPA | Core business logic following DDD patterns. |
| **Database** | PostgreSQL 15, pgvector | Relational storage with semantic search capabilities. |
| **Mobile** | React Native, Expo, TypeScript | Cross-platform mobile application for school management. |
| **AI Agent** | Python 3.10+, Claude (Anthropic) | Intelligent configuration agent for automated setup. |
| **Infrastructure** | Docker, Docker Compose | Containerized environment for easy local deployment. |

## ✨ Key Features

- **DDD Architecture**: Hub-and-Spoke aggregates with strict domain invariants.
- **AI Integration**: Claude-powered agent for complex school configurations.
- **Semantic Search**: Leveraging `pgvector` for intelligent data retrieval.
- **Event-Driven**: Built-in support for asynchronous event processing.
- **Cross-Platform**: Unified experience across web and mobile.

## 🛠️ Quick Start

### Prerequisites
- Java 17+, Node.js 18+, Python 3.10+, Docker & Docker Compose.

### Local Setup
1. **Spin up Infrastructure**:
   ```bash
   docker-compose up -d
   ```
2. **Initialize Database**:
   ```bash
   cd database/scripts && ./setup.sh
   ```
3. **Run Backend**:
   ```bash
   cd backend && ./mvnw spring-boot:run
   ```
4. **Launch Mobile App**:
   ```bash
   cd mobile && npm install && npx expo start
   ```

## 📄 License
This project is licensed under the **MIT License**.
