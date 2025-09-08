-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-09-07 20:01:49.424

-- tables
-- Table: Evento
CREATE TABLE Evento (
    ID_Evento int  NOT NULL GENERATED ALWAYS AS IDENTITY,
    ID_Municipalidad int  NOT NULL,
    Nombre varchar(50)  NOT NULL,
    Descripcion varchar(300)  NOT NULL,
    PesoObjetivo decimal(5,2)  NOT NULL,
    FechaInicio date  NOT NULL,
    FechaFin date  NOT NULL,
    CONSTRAINT Evento_pk PRIMARY KEY (ID_Evento)
);

-- Table: Evento_x_Vecino
CREATE TABLE Evento_x_Vecino (
    ID_ExV int  NOT NULL GENERATED ALWAYS AS IDENTITY,
    ID_Evento int  NOT NULL,
    ID_Vecino int  NOT NULL,
    Comentario int  NOT NULL,
    CONSTRAINT Evento_x_Vecino_pk PRIMARY KEY (ID_ExV)
);

-- Table: Logro
CREATE TABLE Logro (
    ID_Logro int  NOT NULL GENERATED ALWAYS AS IDENTITY,
    ID_Vecino int  NOT NULL,
    Nombre varchar(50)  NOT NULL,
    CONSTRAINT Logro_pk PRIMARY KEY (ID_Logro)
);

-- Table: Municipalidad
CREATE TABLE Municipalidad (
    ID_Municipalidad int  NOT NULL GENERATED ALWAYS AS IDENTITY,
    ID_Usuario int  NOT NULL,
    Distrito varchar(50)  NOT NULL,
    PuntajeTotal int  NOT NULL,
    CONSTRAINT Municipalidad_pk PRIMARY KEY (ID_Municipalidad)
);

-- Table: Reciclaje
CREATE TABLE Reciclaje (
    ID_Reciclaje int  NOT NULL GENERATED ALWAYS AS IDENTITY,
    ID_Vecino int  NOT NULL,
    Peso decimal(5,2)  NOT NULL,
    Tipo varchar(50)  NOT NULL,
    Metodo varchar(50)  NOT NULL,
    FechaHora date  NOT NULL,
    Puntaje int  NOT NULL,
    CONSTRAINT Reciclaje_pk PRIMARY KEY (ID_Reciclaje)
);

-- Table: Usuario
CREATE TABLE Usuario (
    ID_Usuario int  NOT NULL GENERATED ALWAYS AS IDENTITY,
    Codigo varchar(8)  NOT NULL,
    Contrasena varchar(50)  NOT NULL,
    CONSTRAINT Usuario_pk PRIMARY KEY (ID_Usuario)
);

-- Table: Vecino
CREATE TABLE Vecino (
    ID_Vecino int  NOT NULL GENERATED ALWAYS AS IDENTITY,
    ID_Usuario int  NOT NULL,
    Nombre varchar(100)  NOT NULL,
    Genero varchar(1)  NOT NULL,
    Edad int  NOT NULL,
    Distrito varchar(50)  NOT NULL,
    Direccion varchar(150)  NOT NULL,
    PuntajeTotal int  NOT NULL,
    Icono int  NOT NULL,
    CONSTRAINT Vecino_pk PRIMARY KEY (ID_Vecino)
);

-- foreign keys
-- Reference: Evento_Municipalidad (table: Evento)
ALTER TABLE Evento ADD CONSTRAINT Evento_Municipalidad
    FOREIGN KEY (ID_Municipalidad)
    REFERENCES Municipalidad (ID_Municipalidad)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Evento_x_Vecino_Evento (table: Evento_x_Vecino)
ALTER TABLE Evento_x_Vecino ADD CONSTRAINT Evento_x_Vecino_Evento
    FOREIGN KEY (ID_Evento)
    REFERENCES Evento (ID_Evento)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Evento_x_Vecino_Vecino (table: Evento_x_Vecino)
ALTER TABLE Evento_x_Vecino ADD CONSTRAINT Evento_x_Vecino_Vecino
    FOREIGN KEY (ID_Vecino)
    REFERENCES Vecino (ID_Vecino)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Logro_Vecino (table: Logro)
ALTER TABLE Logro ADD CONSTRAINT Logro_Vecino
    FOREIGN KEY (ID_Vecino)
    REFERENCES Vecino (ID_Vecino)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Municipalidad_Usuario (table: Municipalidad)
ALTER TABLE Municipalidad ADD CONSTRAINT Municipalidad_Usuario
    FOREIGN KEY (ID_Usuario)
    REFERENCES Usuario (ID_Usuario)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Reciclaje_Vecino (table: Reciclaje)
ALTER TABLE Reciclaje ADD CONSTRAINT Reciclaje_Vecino
    FOREIGN KEY (ID_Vecino)
    REFERENCES Vecino (ID_Vecino)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Vecino_Usuario (table: Vecino)
ALTER TABLE Vecino ADD CONSTRAINT Vecino_Usuario
    FOREIGN KEY (ID_Usuario)
    REFERENCES Usuario (ID_Usuario)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- End of file.

