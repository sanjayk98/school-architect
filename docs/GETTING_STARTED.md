# Getting Started

## Prerequisites

- Java 17+
- Node.js 18+
- PostgreSQL 15+
- Python 3.10+

## Quick Start

1. Clone repository
2. Start database: `docker-compose up -d postgres`
3. Run migrations: `cd database && ./scripts/setup.sh`
4. Start backend: `cd backend && ./mvnw spring-boot:run`
5. Start mobile: `cd mobile && npx expo start`

See component READMEs for details.
