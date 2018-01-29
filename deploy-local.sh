#!/bin/bash

# Reset immutable DB
scripts/immutable-db-reset.sh

# Clean, test, build and package the project
mvn clean install

# Drop local postgres/cruise_company DB
PGPASSWORD=postgres dropdb -U postgres -h localhost -p 5432 cruise_company

# Load DB from db/model.sql
PGPASSWORD=postgres createdb -U postgres -h localhost -p 5432 -T template0 cruise_company
PGPASSWORD=postgres psql -U postgres -h localhost -p 5432 cruise_company < db/model.sql

# Run project
DATABASE_URL=postgresql://localhost:5432/cruise_company java -jar target/cruise-company-1.0-SNAPSHOT.jar
