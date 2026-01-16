#!/bin/bash
set -e
echo "Setting up database..."
createdb schoolarch 2>/dev/null || echo "Database exists"
psql -d schoolarch -f ../schema/01-complete-schema.sql
echo "✓ Setup complete"
