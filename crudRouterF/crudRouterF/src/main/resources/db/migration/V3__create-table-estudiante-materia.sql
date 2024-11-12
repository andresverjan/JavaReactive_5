CREATE TABLE IF NOT EXISTS estudiante_materia (
    estudiante_id BIGINT NOT NULL,
    materia_id BIGINT NOT NULL,
    PRIMARY KEY (estudiante_id, materia_id),
    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id) ON DELETE CASCADE,
    FOREIGN KEY (materia_id) REFERENCES materias(id) ON DELETE CASCADE
);