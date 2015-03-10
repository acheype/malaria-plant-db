-- execute with postgres privileges
-- command : psql -h localhost -p 5432 -U postgres -f create_user_and_db.sql

CREATE ROLE malariaplantdb LOGIN PASSWORD 'malaria';
CREATE DATABASE malariaplantdb WITH ENCODING = 'UTF8' OWNER = malariaplantdb;
\ CONNECT malariaplantdb
ALTER SCHEMA public
OWNER TO malariaplantdb;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO malariaplantdb;
