

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

ALTER SEQUENCE estudiante_id_seq
OWNED BY estudiante.id;


ALTER SEQUENCE materia_id_seq
OWNED BY materia.id;

drop table estudiante;
drop table materia;
drop SEQUENCE estudiante_id_seq;
drop sequence materia_id_seq;

insert into materia(nombre,nota,estudiante)
values('matematicas', 2,3);
insert into materia(nombre,nota,estudiante)
values('sociales',3 ,3);
insert into materia(nombre,nota,estudiante)
values('español', 3,3);



insert into estudiante(nombre,edad)
values('Carlos', 25);
insert into estudiante(nombre,edad)
values('Juan', 25);
insert into estudiante(nombre,edad)
values('David', 25);

select * 
from estudiante e, materia m 
where e.id = m.estudiante;


