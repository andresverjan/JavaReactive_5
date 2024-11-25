-- Tabla Categoria
CREATE TABLE Categoria (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Tabla Producto
CREATE TABLE Producto (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(10, 2) NOT NULL,
    stock INT DEFAULT 0 NOT NULL,
    category_id INT REFERENCES Categoria(id)
);

-- Tabla Proveedor
CREATE TABLE Proveedor (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20)
);

-- Tabla OrdenCompra
CREATE TABLE OrdenCompra (
    id SERIAL PRIMARY KEY,
    provider_id INT REFERENCES Proveedor(id),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_price NUMERIC(10, 2) NOT NULL
);

-- Tabla DetalleOrdenCompra
CREATE TABLE DetalleOrdenCompra (
    id SERIAL PRIMARY KEY,
    orden_compra_id INT REFERENCES OrdenCompra(id),
    product_id INT REFERENCES Producto(id),
    quantity INT NOT NULL,
    unit_price NUMERIC(10, 2) NOT NULL
);

-- Tabla Cliente
CREATE TABLE Cliente (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20)
);

-- Tabla OrdenVenta
CREATE TABLE OrdenVenta (
    id SERIAL PRIMARY KEY,
    cliente_id INT REFERENCES Cliente(id),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_price NUMERIC(10, 2) NOT NULL,
    status VARCHAR(50) DEFAULT 'CREADA'
);

-- Tabla DetalleOrdenVenta
CREATE TABLE DetalleOrdenVenta (
    id SERIAL PRIMARY KEY,
    orden_venta_id INT REFERENCES OrdenVenta(id),
    product_id INT REFERENCES Producto(id),
    quantity INT NOT NULL,
    unit_price NUMERIC(10, 2) NOT NULL
);

-- Tabla Carrito
CREATE TABLE Carrito (
    id SERIAL PRIMARY KEY,
    cliente_id INT REFERENCES Cliente(id),
    total NUMERIC(10, 2)
);

-- Tabla DetalleCarrito
CREATE TABLE DetalleCarrito (
    id SERIAL PRIMARY KEY,
    carrito_id INT REFERENCES Carrito(id),
    product_id INT REFERENCES Producto(id),
    quantity INT NOT NULL
);