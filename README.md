Malaria Plant Db
================

Database of medicinal plants against malaria

Prerequisites:
---
- PostgreSQL 9.4
- Java 8 

Install
---
Create the database, the user and the schema by executing the sql in the package src/main/resources/input_db :
```
psql -h localhost -p 5432 -U postgres -f create_user_and_db.sql
psql -h localhost -p 5432 -d malariaplantdb -U malariaplantdb -f malariaplantdb_schema_1.0.sql
```
