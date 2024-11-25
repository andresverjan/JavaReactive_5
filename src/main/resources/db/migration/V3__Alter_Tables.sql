-- Tabla Cliente
ALTER TABLE Cliente ADD COLUMN carrito INT REFERENCES Carrito(id);

