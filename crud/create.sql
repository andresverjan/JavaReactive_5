/*Taller de Person*/
CREATE TABLE "person" (
	id smallserial NOT NULL,
	name varchar,
	age int,
	gender varchar,
	dateOfBirth date,
	bloodType varchar
);

/*Taller de Materia y Estudiante*/
drop table estudiante;
drop table materia;
drop sequence estudiante_id_seq;
drop sequence materia_id_seq;

CREATE SEQUENCE estudiante_id_seq;
create table estudiante
(id integer NOT NULL DEFAULT nextval('estudiante_id_seq'),
nombre varchar,
edad integer,
constraint estudiante_pkey primary key(id)
);

CREATE SEQUENCE materia_id_seq;
create table materia (
id integer NOT NULL DEFAULT nextval('materia_id_seq'),
nombre varchar,
nota integer,
estudiante integer,
constraint materia_pkey primary key(id),
constraint estudiante_fke foreign key(estudiante) references estudiante(id)
);

CREATE SEQUENCE nota_id_seq;
create table nota (
idNota integer NOT NULL DEFAULT nextval('nota_id_seq'),
idEstudiante integer,
idMateria integer,
nota integer,
constraint nota_pkey primary key(idNota),
constraint nota_fke foreign key(idEstudiante) references estudiante(idEstudiante),
constraint nota_fkm foreign key(idMateria) references materia(idMateria)
);




ALTER SEQUENCE estudiante_id_seq
OWNED BY estudiante.id;


ALTER SEQUENCE materia_id_seq
OWNED BY materia.id;

drop table estudiante;
drop table materia;
drop SEQUENCE estudiante_id_seq;
drop sequence materia_id_seq;


insert into estudiante(nombre,edad)
values('Carlos', 25);
insert into estudiante(nombre,edad)
values('Juan', 18);
insert into estudiante(nombre,edad)
values('David', 20);

insert into materia(nombre)
values('Matematicas');
insert into materia(nombre)
values('Españo');
insert into materia(nombre)
values('Sociales');

select * 
from estudiante e, materia m 
where e.id = m.estudiante;



/*Proyecto final, carrito de compras*/
CREATE SEQUENCE cliente_seq;
create table cliente(
idCliente integer NOT NULL DEFAULT nextval('cliente_seq'),
nombre varchar,
correo varchar,
direccion varchar,
telefono varchar,
constraint cliente_pkey primary key(idCliente)
);

CREATE SEQUENCE carrito_seq;
create table carrito(
idcarrito integer NOT NULL DEFAULT nextval('carrito_seq'),
fecha_creacion varchar,
constraint carrito_pkey primary key(idCarrito),
);

CREATE SEQUENCE ventas_seq;
create table ventas(
idventa integer NOT NULL DEFAULT nextval('ventas_seq'),
idcliente integer,
idcarrito integer,
totalventa integer,
fecha date,
constraint venta_pkey primary key(idventa),
constraint venta_fk foreign key(idCliente) references cliente(idcliente),
constraint venta_fkc foreign key(idcarrito) references carrito(idcarrito)
);

CREATE SEQUENCE producto_seq;
create table producto(
idproducto integer NOT NULL DEFAULT nextval('producto_seq'),
nombre varchar,
descripcion varchar,
precio integer,
stock int,
categoria varchar,
constraint producto_pkey primary key(idproducto)
);

CREATE SEQUENCE detallecarrito_seq;
create table detallecarrito(
iddetalleCarrito integer NOT NULL DEFAULT nextval('detallecarrito_seq'),
idcarrito integer,
idproducto integer,
cantidad integer,
constraint detallecarrito_pkey primary key(iddetallecarrito),
constraint detallecarrito_fkc foreign key(idcarrito) references carrito(idcarrito),
constraint detallecarrito_fkp foreign key(idproducto) references producto(idproducto)
);


CREATE SEQUENCE proveedor_seq;
create table proveedor(
idproveedor integer NOT NULL DEFAULT nextval('proveedor_seq'),
nombre varchar,
correo varchar,
direccion varchar,
telefono varchar,
constraint proveedor_pkey primary key(idproveedor)
);

CREATE SEQUENCE compra_seq;
create table compra(
idcompra integer NOT NULL DEFAULT nextval('compra_seq'),
idproducto integer,
idproveedor integer,
fecha date,
cantidad integer,
estado boolean,
constraint compra_pkey primary key(idcompra),
constraint compra_fkc foreign key(idproducto) references producto(idproducto),
constraint compra_fkp foreign key(idproveedor) references proveedor(idproveedor)
);


drop table compra;
drop table proveedor;
drop table detallecarrito;
drop table producto;
drop table carrito;
drop table detallecarrito;
drop table cliente;


drop sequence carrito_seq;
drop sequence producto_seq;
drop sequence detallecarrito_seq;
drop sequence proveedor_seq;
drop sequence compra_seq;
drop sequence cliente_seq;