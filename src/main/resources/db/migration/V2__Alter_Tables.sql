-- Tabla OrdenCompra
ALTER TABLE OrdenCompra ADD COLUMN status VARCHAR(50) DEFAULT 'CREADA';
-- Tabla DetalleCarrito
ALTER TABLE DetalleCarrito ADD COLUMN price NUMERIC(10, 2) ;
