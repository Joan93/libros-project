drop database if exists librosdb;
create database librosdb;

use librosdb;

create table users (
    username	varchar(20) not null primary key,
	name		varchar(70) not null,
	email		varchar(255) not null
);

create table libros (
	titulo varchar(100) not null primary key,
	autor varchar(20) not null,
	lengua varchar(20) not null,
	edicion varchar(20) not null,
	fecha_ed date,
	fecha_imp date,
	editorial varchar(20) not null	
);

create table resenas (
    username	varchar(20) not null,
	name		varchar(70) not null,
	fecha date,
	texto varchar(500) not null,
	titulolibro varchar(100) not null,
	foreign key(username) references users(username),
	foreign key(titulolibro) references libros(titulo)
);