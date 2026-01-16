# 🏫 School Architect

> **A Domain-Driven Design (DDD) system for K-12 school configuration with AI-powered automation**

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

School Architect is a production-ready school management system built with Domain-Driven Design principles.

## ✨ Features

- 🎯 Hub-and-Spoke Aggregates with BatchId, SiteId, GradeId
- 🔒 Database-enforced domain invariants
- 🤖 Claude AI-powered configuration agent
- 📊 PostgreSQL + pgvector for semantic search
- ⚡ Event-driven architecture
- 📱 React Native mobile + React web

## 🚀 Quick Start

### Prerequisites
- Java 17+, Node.js 18+, PostgreSQL 15+, Python 3.10+

### Installation

```bash
# 1. Start database
cd backend
docker-compose up -d postgres

# 2. Run migrations
cd ../database && ./scripts/setup.sh

# 3. Start backend
cd ../backend && ./mvnw spring-boot:run

# 4. Start mobile app
cd ../mobile && npm install && npx expo start

# 5. Run AI agent
cd ../ai-agent
pip install -r requirements.txt
export ANTHROPIC_API_KEY=your_key
python examples/basic_setup.py
```

## 📖 Documentation

- [Architecture](./docs/architecture/)
- [API Reference](./docs/api/)
- [Database Schema](./database/README.md)

## 🛠️ Technology Stack

- **Backend**: Java 17 + Spring Boot 3.2
- **Database**: PostgreSQL 15 + pgvector
- **Frontend**: TypeScript + React Native/Expo
- **AI**: Claude Sonnet 4 (Anthropic)

## 🤝 Contributing

See [CONTRIBUTING.md](./CONTRIBUTING.md)

## 📄 License

MIT License - see [LICENSE](./LICENSE)
