/*
Created: 10/02/2016
Modified: 18/02/2016
Model: MySQL 5.5
Database: MySQL 5.5
*/

-- Create tables section -------------------------------------------------

-- Table Rol

CREATE TABLE Rol
(
  Cod_Permiso Int NOT NULL AUTO_INCREMENT,
  Tipo Varchar(50),
 PRIMARY KEY (Cod_Permiso),
 UNIQUE Cod_Permiso (Cod_Permiso)
)
;

-- Table Usuario

CREATE TABLE Usuario
(
  Cod_usuario Int NOT NULL AUTO_INCREMENT,
  Nombre Varchar(50),
  Apellidos Varchar(100),
  Nombre_Usuario Varchar(20),
  Password Varchar(20),
 PRIMARY KEY (Cod_usuario),
 UNIQUE Cod_usuario (Cod_usuario)
)
;

-- Table Roles_Usuarios

CREATE TABLE Roles_Usuarios
(
  Cod_Permiso Int NOT NULL,
  Cod_usuario Int NOT NULL,
  Activado Bool
)
;

ALTER TABLE Roles_Usuarios ADD PRIMARY KEY (Cod_Permiso,Cod_usuario)
;

-- Table Editorial

CREATE TABLE Editorial
(
  Cod_Editorial Int NOT NULL AUTO_INCREMENT,
  Nombre Varchar(50),
 PRIMARY KEY (Cod_Editorial),
 UNIQUE Cod_Editorial (Cod_Editorial)
)
;

-- Table Libro

CREATE TABLE Libro
(
  Cod_Libro Int NOT NULL AUTO_INCREMENT,
  Titulo Varchar(100),
 PRIMARY KEY (Cod_Libro),
 UNIQUE Cod_Libro (Cod_Libro)
)
;

-- Table Editorial_Libro

CREATE TABLE Editorial_Libro
(
  Cod_Editorial Int NOT NULL,
  Cod_Libro Int NOT NULL,
  Anio_Publicacion Int
)
;

ALTER TABLE Editorial_Libro ADD PRIMARY KEY (Cod_Editorial,Cod_Libro)
;

-- Table Autor

CREATE TABLE Autor
(
  Cod_Autor Int NOT NULL AUTO_INCREMENT,
  Nombre Char(20),
 PRIMARY KEY (Cod_Autor),
 UNIQUE Cod_Autor (Cod_Autor)
)
;

-- Table Autor_Libro

CREATE TABLE Autor_Libro
(
  Cod_Libro Int NOT NULL,
  Cod_Autor Int NOT NULL
)
;

ALTER TABLE Autor_Libro ADD PRIMARY KEY (Cod_Libro,Cod_Autor)
;

-- Table Prestamo

CREATE TABLE Prestamo
(
  Cod_Prestamo Int NOT NULL AUTO_INCREMENT,
  Cod_Libro Int NOT NULL,
  Cod_usuario Int,
  Fecha_Prestamo Date,
  Fecha_Devolucion Date,
 PRIMARY KEY (Cod_Prestamo,Cod_Libro),
 UNIQUE Cod_Prestamo (Cod_Prestamo)
)
;

CREATE INDEX IX_Relationship9 ON Prestamo (Cod_usuario)
;

-- Table Sancion

CREATE TABLE Sancion
(
  Cod_Sancion Int NOT NULL AUTO_INCREMENT,
  Cod_Prestamo Int NOT NULL,
  Cod_Libro Int NOT NULL,
  Fecha_Alta Date,
  Fecha_Finalizacion Date,
 PRIMARY KEY (Cod_Sancion,Cod_Prestamo,Cod_Libro),
 UNIQUE Cod_Sancion (Cod_Sancion)
)
;

-- Create relationships section ------------------------------------------------- 

ALTER TABLE Roles_Usuarios ADD CONSTRAINT Permiso_Usuario FOREIGN KEY (Cod_Permiso) REFERENCES Rol (Cod_Permiso) ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE Roles_Usuarios ADD CONSTRAINT Usuario_Permiso FOREIGN KEY (Cod_usuario) REFERENCES Usuario (Cod_usuario) ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE Editorial_Libro ADD CONSTRAINT Editorial_Libro FOREIGN KEY (Cod_Editorial) REFERENCES Editorial (Cod_Editorial) ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE Editorial_Libro ADD CONSTRAINT Libro_Editorial FOREIGN KEY (Cod_Libro) REFERENCES Libro (Cod_Libro) ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE Autor_Libro ADD CONSTRAINT Libro_Autor FOREIGN KEY (Cod_Libro) REFERENCES Libro (Cod_Libro) ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE Autor_Libro ADD CONSTRAINT Autor_Libro FOREIGN KEY (Cod_Autor) REFERENCES Autor (Cod_Autor) ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE Prestamo ADD CONSTRAINT Libro_Prestamo FOREIGN KEY (Cod_Libro) REFERENCES Libro (Cod_Libro) ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE Prestamo ADD CONSTRAINT Usuario_Prestamo FOREIGN KEY (Cod_usuario) REFERENCES Usuario (Cod_usuario) ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE Sancion ADD CONSTRAINT Prestamo_Sancion FOREIGN KEY (Cod_Prestamo, Cod_Libro) REFERENCES Prestamo (Cod_Prestamo, Cod_Libro) ON DELETE CASCADE ON UPDATE CASCADE
;


