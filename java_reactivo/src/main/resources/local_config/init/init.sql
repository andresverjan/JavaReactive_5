-- Create data base
CREATE DATABASE java_reactivo;

-- Connection to database
\c java_reactivo;

-- Create schema if not exists
CREATE SCHEMA IF NOT EXISTS schema_reactivo;

-- Set search_path to the schema
SET search_path TO schema_reactivo;

create table if not exists estudiante
(
    id serial primary key,
    nombre varchar(100),
    edad integer
);

create table if not exists materia
(
    id serial primary key,
    nombre varchar(100)
);

CREATE TABLE IF NOT EXISTS registro (
    id SERIAL PRIMARY KEY,
    estudiante_id INTEGER REFERENCES estudiante(id),
    materia_id INTEGER REFERENCES materia(id),
    nota float,
    UNIQUE (estudiante_id, materia_id)
    );

CREATE TABLE IF NOT EXISTS person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    age INTEGER,
    gender VARCHAR(50),
    date_of_birth DATE,
    blood_type VARCHAR(10)
);

INSERT INTO estudiante (nombre, edad) VALUES ('Juan Perez', 20);
INSERT INTO estudiante (nombre, edad) VALUES ('Maria Gomez', 22);
INSERT INTO estudiante (nombre, edad) VALUES ('Carlos Ruiz', 21);
INSERT INTO estudiante (nombre, edad) VALUES ('Ana Torres', 23);
INSERT INTO estudiante (nombre, edad) VALUES ('Luis Fernandez', 19);

INSERT INTO materia (nombre) VALUES ('Matematicas');
INSERT INTO materia (nombre) VALUES ('Fisica');

INSERT INTO registro (estudiante_id, materia_id, nota) VALUES (1, 1, 4.5);
INSERT INTO registro (estudiante_id, materia_id, nota) VALUES (1, 2, 3.8);
INSERT INTO registro (estudiante_id, materia_id, nota) VALUES (2, 1, 4.0);
INSERT INTO registro (estudiante_id, materia_id, nota) VALUES (2, 2, 4.2);
INSERT INTO registro (estudiante_id, materia_id, nota) VALUES (3, 1, 3.5);
INSERT INTO registro (estudiante_id, materia_id, nota) VALUES (3, 2, 4.1);
INSERT INTO registro (estudiante_id, materia_id, nota) VALUES (4, 1, 4.7);
INSERT INTO registro (estudiante_id, materia_id, nota) VALUES (4, 2, 3.9);
INSERT INTO registro (estudiante_id, materia_id, nota) VALUES (5, 1, 2.0);
INSERT INTO registro (estudiante_id, materia_id, nota) VALUES (5, 2, 2.8);