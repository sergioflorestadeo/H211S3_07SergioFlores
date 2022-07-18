USE master;
GO

-- Creacion de Base de Datos:
CREATE DATABASE dbPizzeria
GO

-- Uso de Base de datos:
USE dbPizzeria
GO

-- tables
-- Table: CLIENTE
CREATE TABLE CLIENTE (
    CODCLI int  NOT NULL IDENTITY(1, 1),
    NOMCLI varchar(50)  NOT NULL,
    APECLI varchar(50)  NOT NULL,
    TELCLI char(9)  NOT NULL,
    DIRCLI varchar(30)  NOT NULL,
    ESTCLI char(1)  NOT NULL,
    CORCLI varchar(50)  NOT NULL,
    CODUBI char(6)  NOT NULL,
    CONSTRAINT CLIENTE_pk PRIMARY KEY  (CODCLI)
);

-- Table: EMPLEADO
CREATE TABLE EMPLEADO (
    CODEMP int  NOT NULL IDENTITY(1, 1),
    NOMEMP varchar(25)  NOT NULL,
    APEEMP varchar(25)  NOT NULL,
    TELEMP char(9)  NOT NULL,
    ESTEMP char(1)  NOT NULL,
    ROLEMP char(1)  NOT NULL,
    DNIEMP char(8)  NOT NULL,
    IDSUC int  NOT NULL,
    CODUBI char(6)  NOT NULL,
    CONSTRAINT EMPLEADO_pk PRIMARY KEY  (CODEMP)
);

-- Table: SURCURSAL
CREATE TABLE SURCURSAL (
    IDSUC int  NOT NULL IDENTITY(1, 1),
    NOMSUC varchar(50)  NOT NULL,
    CORSUC varchar(50)  NOT NULL,
    DIRSUC varchar(50)  NOT NULL,
    CODUBI char(6)  NOT NULL,
    CONSTRAINT SURCURSAL_pk PRIMARY KEY  (IDSUC)
);

-- Table: UBIGEO
CREATE TABLE UBIGEO (
    CODUBI char(6)  NOT NULL,
    DISUBI varchar(25)  NOT NULL,
    PROVUBI varchar(25)  NOT NULL,
    DEPUBI varchar(25)  NOT NULL,
    CONSTRAINT UBIGEO_pk PRIMARY KEY  (CODUBI)
);

-- Table: boleta
CREATE TABLE boleta (
    IDBOL int  NOT NULL IDENTITY(1, 1),
    FECBOL date  NOT NULL,
    CODEMP int  NOT NULL,
    CODCLI int  NOT NULL,
    CONSTRAINT boleta_pk PRIMARY KEY  (IDBOL)
);

-- Table: boleta_detalle
CREATE TABLE boleta_detalle (
    IDDETBOL int  NOT NULL IDENTITY(1, 1),
    CANTPROD int  NOT NULL,
    IDBOL int  NOT NULL,
    CODPROD int  NOT NULL,
    CONSTRAINT boleta_detalle_pk PRIMARY KEY  (IDDETBOL)
);

-- Table: producto
CREATE TABLE producto (
    CODPROD int  NOT NULL IDENTITY(1, 1),
    NOMPROD varchar(50)  NOT NULL,
    PRECPROD decimal(10,2)  NOT NULL,
    STOCKPROD int  NOT NULL,
    ESTPROD char(1)  NOT NULL,
    CONSTRAINT producto_pk PRIMARY KEY  (CODPROD)
);

-- foreign keys
-- Reference: CLIENTE_UBIGEO (table: CLIENTE)
ALTER TABLE CLIENTE ADD CONSTRAINT CLIENTE_UBIGEO
    FOREIGN KEY (CODUBI)
    REFERENCES UBIGEO (CODUBI);

-- Reference: EMPLEADO_SURCURSAL (table: EMPLEADO)
ALTER TABLE EMPLEADO ADD CONSTRAINT EMPLEADO_SURCURSAL
    FOREIGN KEY (IDSUC)
    REFERENCES SURCURSAL (IDSUC);

-- Reference: EMPLEADO_UBIGEO (table: EMPLEADO)
ALTER TABLE EMPLEADO ADD CONSTRAINT EMPLEADO_UBIGEO
    FOREIGN KEY (CODUBI)
    REFERENCES UBIGEO (CODUBI);

-- Reference: SURCURSAL_UBIGEO (table: SURCURSAL)
ALTER TABLE SURCURSAL ADD CONSTRAINT SURCURSAL_UBIGEO
    FOREIGN KEY (CODUBI)
    REFERENCES UBIGEO (CODUBI);

-- Reference: boleta_CLIENTE (table: boleta)
ALTER TABLE boleta ADD CONSTRAINT boleta_CLIENTE
    FOREIGN KEY (CODCLI)
    REFERENCES CLIENTE (CODCLI);

-- Reference: boleta_EMPLEADO (table: boleta)
ALTER TABLE boleta ADD CONSTRAINT boleta_EMPLEADO
    FOREIGN KEY (CODEMP)
    REFERENCES EMPLEADO (CODEMP);

-- Reference: boleta_detalle_boleta (table: boleta_detalle)
ALTER TABLE boleta_detalle ADD CONSTRAINT boleta_detalle_boleta
    FOREIGN KEY (IDBOL)
    REFERENCES boleta (IDBOL);

-- Reference: boleta_detalle_producto (table: boleta_detalle)
ALTER TABLE boleta_detalle ADD CONSTRAINT boleta_detalle_producto
    FOREIGN KEY (CODPROD)
    REFERENCES producto (CODPROD);




