-- Create data base
CREATE DATABASE java_reactivo;

-- Connection to database
\c java_reactivo;

-- Create schema if not exists
CREATE SCHEMA IF NOT EXISTS schema_reactivo;

-- Set search_path to the schema
SET search_path TO schema_reactivo;