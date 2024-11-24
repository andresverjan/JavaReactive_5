-- Crear la base de datos
CREATE DATABASE ecommercedb;

-- Conectar a la base de datos
\c ecommercedb;

-- Crear el esquema
CREATE SCHEMA ecommerce_schema;

CREATE TABLE ecommerce_schema.Productos (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    descripcion TEXT,
    imageUrl VARCHAR(255),
    stock INT NOT NULL,
	precio DECIMAL(10, 2) NOT NULL
);

-- Crear la tabla de Proveedores en el esquema
CREATE TABLE ecommerce_schema.Proveedores (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion TEXT,
    telefono VARCHAR(20),
    email VARCHAR(255)
);

-- Crear la tabla de Carrito en el esquema
CREATE TABLE ecommerce_schema.Carrito (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Crear la tabla de Detalles del Carrito en el esquema
CREATE TABLE ecommerce_schema.Carrito_Detalles (
    id SERIAL PRIMARY KEY,
    carrito_id INT REFERENCES ecommerce_schema.Carrito(id) ON DELETE CASCADE,
    producto_id INT REFERENCES ecommerce_schema.Productos(id),
    cantidad INT NOT NULL
);

-- Crear la tabla de Órdenes de Venta en el esquema
CREATE TABLE ecommerce_schema.Ordenes_Venta (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    fecha_orden TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2) NOT NULL
);

-- Crear la tabla de Detalles de las Órdenes de Venta en el esquema
CREATE TABLE ecommerce_schema.Ordenes_Venta_Detalles (
    id SERIAL PRIMARY KEY,
    orden_id INT REFERENCES ecommerce_schema.Ordenes_Venta(id) ON DELETE CASCADE,
    producto_id INT REFERENCES ecommerce_schema.Productos(id),
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL
);

-- Crear la tabla de Órdenes de Compra en el esquema
CREATE TABLE ecommerce_schema.Ordenes_Compra (
    id SERIAL PRIMARY KEY,
    proveedor_id INT REFERENCES ecommerce_schema.Proveedores(id),
    fecha_orden TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2) NOT NULL
);

-- Crear la tabla de Detalles de las Órdenes de Compra en el esquema
CREATE TABLE ecommerce_schema.Ordenes_Compra_Detalles (
    id SERIAL PRIMARY KEY,
    orden_id INT REFERENCES ecommerce_schema.Ordenes_Compra(id) ON DELETE CASCADE,
    producto_id INT REFERENCES ecommerce_schema.Productos(id),
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL
);

INSERT INTO ecommerce_schema.Proveedores (nombre, direccion, telefono, email) VALUES
('Proveedor 1', 'Calle 123, Ciudad, País', '123456789', 'proveedor1@example.com'),
('Proveedor 2', 'Avenida 456, Ciudad, País', '987654321', 'proveedor2@example.com');

INSERT INTO ecommerce_schema.Productos (name, descripcion, imageUrl, stock, precio) VALUES
('Producto 1', 'Descripción del producto 1', 'http://example.com/producto1.jpg', 100, 19.99),
('Producto 2', 'Descripción del producto 2', 'http://example.com/producto2.jpg', 50, 29.99),
('Producto 3', 'Descripción del producto 3', 'http://example.com/producto3.jpg', 75, 39.99),
('Producto 4', 'Descripción del producto 4', 'http://example.com/producto4.jpg', 80, 49.99),
('Producto 5', 'Descripción del producto 5', 'http://example.com/producto5.jpg', 60, 59.99),
('Producto 6', 'Descripción del producto 6', 'http://example.com/producto6.jpg', 90, 69.99);

INSERT INTO ecommerce_schema.Carrito (usuario_id, fecha_creacion) VALUES
(1, '2024-11-24 10:00:00'),
(2, '2024-11-24 11:00:00');

INSERT INTO ecommerce_schema.Carrito_Detalles (carrito_id, producto_id, cantidad) VALUES
(1, 1, 2),
(1, 3, 1),
(2, 2, 5);

INSERT INTO ecommerce_schema.Ordenes_Compra (proveedor_id, fecha_orden, total) VALUES
(1, '2024-11-20 10:00:00', 299.90),
(1, '2024-11-21 11:00:00', 599.90),
(2, '2024-11-22 12:00:00', 399.90),
(2, '2024-11-23 13:00:00', 499.90);

INSERT INTO ecommerce_schema.Ordenes_Compra_Detalles (orden_id, producto_id, cantidad, precio_unitario) VALUES
(1, 1, 10, 19.99),
(1, 2, 5, 29.99),
(2, 3, 10, 39.99),
(2, 4, 5, 49.99),
(3, 5, 5, 59.99),
(3, 6, 5, 69.99),
(4, 1, 20, 19.99),
(4, 2, 10, 29.99);


INSERT INTO ecommerce_schema.Ordenes_Venta (usuario_id, fecha_orden, total) VALUES
(1, '2024-11-20 14:00:00', 59.97),
(1, '2024-11-21 15:00:00', 149.95),
(2, '2024-11-22 16:00:00', 199.93),
(2, '2024-11-23 17:00:00', 249.91);


INSERT INTO ecommerce_schema.Ordenes_Venta_Detalles (orden_id, producto_id, cantidad, precio_unitario) VALUES
(1, 1, 2, 19.99),
(1, 3, 1, 39.99),
(2, 2, 5, 29.99),
(2, 4, 1, 49.99),
(3, 5, 3, 59.99),
(3, 6, 2, 69.99),
(4, 1, 10, 19.99),
(4, 2, 5, 29.99);


