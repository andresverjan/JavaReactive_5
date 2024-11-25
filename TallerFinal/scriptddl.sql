-- Crear la base de datos
CREATE DATABASE ecommerce;

-- Crear esquema principal
CREATE SCHEMA ecommerce;
SET search_path TO ecommerce;

-- Crear tabla de proveedores
CREATE TABLE proveedor (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    direccion TEXT NOT NULL,
    correo VARCHAR(255) NOT NULL
);

-- Crear tabla de clientes
CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    direccion TEXT NOT NULL,
    documento VARCHAR(50) NOT NULL UNIQUE
);

-- Crear tabla de productos
CREATE TABLE producto (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL CHECK (precio_unitario > 0),
    stock INT NOT NULL CHECK (stock >= 0),
    categoria VARCHAR(100) NOT NULL
);

-- Crear tabla de compras
CREATE TABLE compra (
    id SERIAL PRIMARY KEY,
    proveedor_id INT NOT NULL,
    fecha TIMESTAMP NOT NULL,
    total DECIMAL(10, 2) NOT NULL CHECK (total > 0),
    FOREIGN KEY (proveedor_id) REFERENCES proveedor (id) ON DELETE CASCADE
);

-- Crear tabla de detalles de compras
CREATE TABLE detalle_compra (
    id SERIAL PRIMARY KEY,
    compra_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(10, 2) NOT NULL CHECK (precio_unitario > 0),
    FOREIGN KEY (compra_id) REFERENCES compra (id) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES producto (id) ON DELETE CASCADE
);

-- Crear tabla de ventas
CREATE TABLE venta (
    id SERIAL PRIMARY KEY,
    cliente_id INT NOT NULL,
    fecha TIMESTAMP NOT NULL,
    total DECIMAL(10, 2) NOT NULL CHECK (total > 0),
    FOREIGN KEY (cliente_id) REFERENCES cliente (id) ON DELETE CASCADE
);

-- Crear tabla de detalles de ventas
CREATE TABLE detalle_venta (
    id SERIAL PRIMARY KEY,
    venta_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(10, 2) NOT NULL CHECK (precio_unitario > 0),
    FOREIGN KEY (venta_id) REFERENCES venta (id) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES producto (id) ON DELETE CASCADE
);

-- Crear tabla de carritos
CREATE TABLE carrito (
    id SERIAL PRIMARY KEY,
    cliente_id INT, -- Puede ser NULL si el cliente no está registrado
    fecha TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (cliente_id) REFERENCES cliente (id) ON DELETE SET NULL
);

-- Crear tabla de detalles del carrito
CREATE TABLE detalle_carrito (
    id SERIAL PRIMARY KEY,
    carrito_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(10, 2) NOT NULL CHECK (precio_unitario > 0),
    FOREIGN KEY (carrito_id) REFERENCES carrito (id) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES producto (id) ON DELETE CASCADE
);

-- Crear índices para optimización de consultas
CREATE INDEX idx_proveedor_nombre ON proveedor (nombre);
CREATE INDEX idx_cliente_documento ON cliente (documento);
CREATE INDEX idx_producto_categoria ON producto (categoria);
CREATE INDEX idx_compra_proveedor ON compra (proveedor_id);
CREATE INDEX idx_venta_cliente ON venta (cliente_id);
CREATE INDEX idx_carrito_cliente ON carrito (cliente_id);
CREATE INDEX idx_detalle_carrito_producto ON detalle_carrito (producto_id);
