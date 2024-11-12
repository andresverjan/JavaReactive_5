CREATE TABLE IF NOT EXISTS nota (
    id SERIAL PRIMARY KEY,
    estudiante_id BIGINT NOT NULL,
    materia_id BIGINT NOT NULL,
    valor NUMERIC(5, 2) NOT NULL,
    CONSTRAINT fk_estudiante
        FOREIGN KEY(estudiante_id)
        REFERENCES estudiantes(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_materia
        FOREIGN KEY(materia_id)
        REFERENCES materias(id)
        ON DELETE CASCADE
);