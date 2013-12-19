libros-project
==============
Hay que crear los usuarios sql siguientes:

  create user 'libros'@'localhost' identified by 'libros'; 
  grant all privileges on librosdb.* to 'libros'@'localhost'; 
  flush privileges; 
  exit;
  
  create user 'realm'@'localhost' identified by 'realm'; 
  grant all privileges on realmdb.* to 'realm'@'localhost'; 
  flush privileges; 
  exit;
  
Luego cargamos las bases de datos con las tablas:

  mysql -u libros -p 
  libros 
  source (path relativo)/librosdb-schema.sql; 
  source (path relativo)/librosdb-data.sql; 
  exit;
  

  mysql -u realm -p 
  realm 
  source (path relativo)/realmdb-schema.sql; 
  source (path relativo)/realmdb-data.sql; 
  exit;
  
Hacemos maven build del paquete Libros-api
Cargamos en Tomcat el .war de Libros-api 

Y ya podemos comprobar en POSTMAN 
adjunto en el zip:"Libros API.json.postman_collection"
