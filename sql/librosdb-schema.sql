drop database if exists librosdb;
create database librosdb;

use librosdb;

create table users (
    username	varchar(20) not null primary key,
	name		varchar(70) not null,
	email		varchar(255) not null
);

create table libros (
	id int not null auto_increment primary key,
	titulo varchar(100) not null,
	autor varchar(20) not null,
	lengua varchar(20) not null,
	edicion varchar(20) not null,
	fecha_ed date,
	fecha_imp date,
	editorial varchar(20) not null	
);

create table resenas (
	idres int not null auto_increment primary key,
	idlibro int not null,
    username	varchar(20) not null,
	name		varchar(70) not null,
	fecha date,
	texto varchar(500) not null,
	foreign key(username) references users(username),
	foreign key(idlibro) references libros(id)
);