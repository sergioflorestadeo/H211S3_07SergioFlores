-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2022-07-08 17:10:25.059

-- tables
-- Table: CLIENTE
CREATE TABLE CLIENTE (
    CODCLI int  NOT NULL,
    NOMCLI varchar(50)  NOT NULL,
    TELCLI char(9)  NOT NULL,
    DIRCLI varchar(30)  NOT NULL,
    UBIGEO_CODUBI int  NOT NULL,
    boleta_IDBOL int  NOT NULL,
    CONSTRAINT CLIENTE_pk PRIMARY KEY  (CODCLI)
);

-- Table: REPARTIDOR
CREATE TABLE REPARTIDOR (
    CODREP int  NOT NULL,
    NOMREP varchar(50)  NOT NULL,
    TELREP char(9)  NOT NULL,
    UBIREP int  NOT NULL,
    CLIENTE_CODCLI int  NOT NULL,
    CONSTRAINT REPARTIDOR_pk PRIMARY KEY  (CODREP)
);

-- Table: UBIGEO
CREATE TABLE UBIGEO (
    CODUBI int  NOT NULL,
    DISUBI int  NOT NULL,
    PROVUBI int  NOT NULL,
    DEPUBI int  NOT NULL,
    CONSTRAINT UBIGEO_pk PRIMARY KEY  (CODUBI)
);

-- Table: VENDEDOR
CREATE TABLE VENDEDOR (
    CODVEN int  NOT NULL,
    NOMVEN int  NOT NULL,
    TELVEN int  NOT NULL,
    boleta_IDBOL int  NOT NULL,
    CONSTRAINT VENDEDOR_pk PRIMARY KEY  (CODVEN)
);

-- Table: boleta
CREATE TABLE boleta (
    IDBOL int  NOT NULL,
    FECBOL date  NOT NULL,
    CODCLI int  NOT NULL,
    TOTBOL decimal(10,2)  NOT NULL,
    boleta_detalle_IDDETBOL int  NOT NULL,
    CONSTRAINT boleta_pk PRIMARY KEY  (IDBOL)
);

-- Table: boleta_detalle
CREATE TABLE boleta_detalle (
    IDDETBOL int  NOT NULL,
    CANTPROD int  NOT NULL,
    PRECBOL int  NOT NULL,
    CODPROD int  NOT NULL,
    IDBOL int  NOT NULL,
    producto_CODPROD int  NOT NULL,
    CONSTRAINT boleta_detalle_pk PRIMARY KEY  (IDDETBOL)
);

-- Table: producto
CREATE TABLE producto (
    CODPROD int  NOT NULL,
    NOMPROD varchar(50)  NOT NULL,
    PRECPROD decimal(10,2)  NOT NULL,
    STOCKPROD int  NOT NULL,
    ESTSTOCKPROD char(1)  NOT NULL,
    CONSTRAINT producto_pk PRIMARY KEY  (CODPROD)
);

-- foreign keys
-- Reference: CLIENTE_UBIGEO (table: CLIENTE)
ALTER TABLE CLIENTE ADD CONSTRAINT CLIENTE_UBIGEO
    FOREIGN KEY (UBIGEO_CODUBI)
    REFERENCES UBIGEO (CODUBI);

-- Reference: CLIENTE_boleta (table: CLIENTE)
ALTER TABLE CLIENTE ADD CONSTRAINT CLIENTE_boleta
    FOREIGN KEY (boleta_IDBOL)
    REFERENCES boleta (IDBOL);

-- Reference: REPARTIDOR_CLIENTE (table: REPARTIDOR)
ALTER TABLE REPARTIDOR ADD CONSTRAINT REPARTIDOR_CLIENTE
    FOREIGN KEY (CLIENTE_CODCLI)
    REFERENCES CLIENTE (CODCLI);

-- Reference: VENDEDOR_boleta (table: VENDEDOR)
ALTER TABLE VENDEDOR ADD CONSTRAINT VENDEDOR_boleta
    FOREIGN KEY (boleta_IDBOL)
    REFERENCES boleta (IDBOL);

-- Reference: boleta_boleta_detalle (table: boleta)
ALTER TABLE boleta ADD CONSTRAINT boleta_boleta_detalle
    FOREIGN KEY (boleta_detalle_IDDETBOL)
    REFERENCES boleta_detalle (IDDETBOL);

-- Reference: boleta_detalle_producto (table: boleta_detalle)
ALTER TABLE boleta_detalle ADD CONSTRAINT boleta_detalle_producto
    FOREIGN KEY (producto_CODPROD)
    REFERENCES producto (CODPROD);

-- End of file.

