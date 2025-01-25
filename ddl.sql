DROP SEQUENCE IF EXISTS cliente_id_seq
;

DROP SEQUENCE IF EXISTS cuenta_id_seq
;

DROP SEQUENCE IF EXISTS diferido_id_seq
;

DROP SEQUENCE IF EXISTS direccion_id_seq
;

DROP SEQUENCE IF EXISTS disputa_id_seq
;

DROP SEQUENCE IF EXISTS notificacion_id_seq
;

DROP SEQUENCE IF EXISTS persona_juridica_id_seq
;

DROP SEQUENCE IF EXISTS persona_natural_id_seq
;

DROP SEQUENCE IF EXISTS seguridad_marca_id_seq
;

DROP SEQUENCE IF EXISTS tarjeta_id_seq
;

DROP SEQUENCE IF EXISTS transaccion_id_seq
;

DROP SEQUENCE IF EXISTS usuario_id_seq
;

DROP TABLE IF EXISTS CLIENTE CASCADE
;

DROP TABLE IF EXISTS CUENTA CASCADE
;

DROP TABLE IF EXISTS DETALLE_TRANSACCION CASCADE
;

DROP TABLE IF EXISTS DIFERIDO CASCADE
;

DROP TABLE IF EXISTS DIRECCION CASCADE
;

DROP TABLE IF EXISTS DISPUTA CASCADE
;

DROP TABLE IF EXISTS NOTIFICACION CASCADE
;

DROP TABLE IF EXISTS PERSONA_JURIDICA CASCADE
;

DROP TABLE IF EXISTS PERSONA_NATURAL CASCADE
;

DROP TABLE IF EXISTS SEGURIDAD_MARCA CASCADE
;

DROP TABLE IF EXISTS TARJETA CASCADE
;

DROP TABLE IF EXISTS TRANSACCION CASCADE
;

DROP TABLE IF EXISTS USUARIO CASCADE
;

CREATE TABLE CLIENTE
(
	ID integer NOT NULL   DEFAULT NEXTVAL(('"cliente_id_seq"'::text)::regclass),
	PERSONA_NATURAL_ID integer NULL,
	PERSONA_JURIDICA_ID integer NULL,
	TIPO varchar(3) NOT NULL,
	INGRESO_PROMEDIO_MENSUAL numeric(18,2) NOT NULL,
	ESTADO varchar(3) NOT NULL,
	FECHA_CREACION varchar(50) NULL,
	FECHA_ACTUALIZACION varchar(50) NULL
)
;

CREATE TABLE CUENTA
(
	ID integer NOT NULL   DEFAULT NEXTVAL(('"cuenta_id_seq"'::text)::regclass),
	CLIENTE_ID integer NOT NULL,
	TIPO varchar(3) NOT NULL,
	NUMERO varchar(16) NOT NULL,
	SALDO_TOTAL numeric(18,2) NOT NULL,
	SALDO_DISPONIBLE numeric(18,2) NOT NULL,
	SALDO_ACREDITAR numeric(18,2) NOT NULL,
	ESTADO varchar(3) NOT NULL,
	FECHA_CREACION timestamp with time zone NULL,
	FECHA_ACTUALIZACION timestamp with time zone NULL
)
;

CREATE TABLE DETALLE_TRANSACCION
(
	TRANSACCION_ID integer NOT NULL,
	TIPO_CUENTA varchar(3) NULL,
	CUENTA_ORIGEN varchar(16) NULL,
	CUENTA_DESTINO varchar(16) NULL,
	BIN_BANCO_ORIGEN varchar(8) NULL,
	BIN_BANCO_DESTINO varchar(8) NULL,
	DESCRIPCION varchar(128) NULL,
	DETALLE_JSON text NULL,
	FECHA_AUTORIZACION timestamp with time zone NULL
)
;

CREATE TABLE DIFERIDO
(
	ID integer NOT NULL   DEFAULT NEXTVAL(('"diferido_id_seq"'::text)::regclass),
	TRANSACCION_ID integer NOT NULL,
	CUOTAS integer NOT NULL,
	CUOTAS_CANCELADAS integer NOT NULL,
	TAZA_INTERES numeric(5,2) NOT NULL,
	VALOR_CUOTA numeric(18,2) NOT NULL,
	VALOR_DEUDA numeric(18,2) NOT NULL,
	VALOR_RESTANTE numeric(18,2) NOT NULL,
	VALOR_INTERES numeric(18,2) NOT NULL,
	ESTADO varchar(3) NOT NULL
)
;

CREATE TABLE DIRECCION
(
	ID integer NOT NULL   DEFAULT NEXTVAL(('"direccion_id_seq"'::text)::regclass),
	PERSONA_NATURAL_ID integer NULL,
	PERSONA_JURIDICA_ID integer NULL,
	PROVINCIA varchar(32) NOT NULL,
	CIUDAD varchar(32) NOT NULL,
	CANTON varchar(32) NOT NULL,
	SECTOR varchar(32) NOT NULL,
	CALLE_PRINCIPAL varchar(64) NOT NULL,
	CALLE_SECUNDARIA varchar(64) NOT NULL,
	NUMERO varchar(16) NOT NULL,
	REFERENCIA varchar(64) NOT NULL,
	DETALLE varchar(256) NULL
)
;

CREATE TABLE DISPUTA
(
	ID integer NOT NULL   DEFAULT NEXTVAL(('"disputa_id_seq"'::text)::regclass),
	TRANSACCION_ID integer NOT NULL,
	RAZON varchar(256) NOT NULL,
	ESTADO varchar(3) NOT NULL,
	FECHA_RESOLUCION date NOT NULL,
	FECHA_CREACION timestamp with time zone NULL,
	FECHA_ACTUALIZACION timestamp with time zone NULL
)
;

CREATE TABLE NOTIFICACION
(
	ID integer NOT NULL   DEFAULT NEXTVAL(('"notificacion_id_seq"'::text)::regclass),
	CLIENTE_ID integer NOT NULL,
	TRANSACCION_ID integer NULL,
	TIPO varchar(3) NOT NULL,
	ESTADO varchar(3) NOT NULL,
	CONTENIDO text NOT NULL,
	FECHA_ENVIO date NOT NULL
)
;

CREATE TABLE PERSONA_JURIDICA
(
	ID integer NOT NULL   DEFAULT NEXTVAL(('"persona_juridica_id_seq"'::text)::regclass),
	PERSONA_NATURAL_ID integer NOT NULL,
	RUC varchar(13) NOT NULL,
	RAZON_SOCIAL varchar(256) NOT NULL,
	NOMBRE_COMERCIAL varchar(128) NOT NULL,
	EMAIL varchar(64) NOT NULL,
	NUMERO_TELEFONICO varchar(10) NOT NULL,
	FECHA_CONSTITUCION date NOT NULL,
	FECHA_CREACION timestamp with time zone NULL,
	FECHA_ACTUALIZACION timestamp with time zone NULL
)
;

CREATE TABLE PERSONA_NATURAL
(
	ID integer NOT NULL   DEFAULT NEXTVAL(('"persona_natural_id_seq"'::text)::regclass),
	TIPO_IDENTIFICACION varchar(3) NOT NULL,
	IDENTIFICACION varchar(13) NOT NULL,
	PRIMER_NOMBRE varchar(32) NOT NULL,
	SEGUNDO_NOMBRE varchar(32) NULL,
	PRIMER_APELLIDO varchar(32) NOT NULL,
	SEGUNDO_APELLIDO varchar(32) NOT NULL,
	EMAIL varchar(50) NOT NULL,
	NUMERO_TELEFONICO varchar(10) NOT NULL,
	FECHA_NACIMIENTO date NOT NULL,
	FECHA_CREACION timestamp with time zone NULL,
	FECHA_ACTUALIZACION timestamp with time zone NULL
)
;

CREATE TABLE SEGURIDAD_MARCA
(
	ID integer NOT NULL   DEFAULT NEXTVAL(('"seguridad_marca_id_seq"'::text)::regclass),
	NOMBRE varchar(50) NOT NULL,
	CLAVE_AUTENTICACION varchar(256) NOT NULL,
	URI_BASE varchar(256) NOT NULL,
	FECHA_CREACION timestamp with time zone NULL,
	FECHA_ACTUALIZACION timestamp with time zone NULL
)
;

CREATE TABLE TARJETA
(
	ID integer NOT NULL   DEFAULT NEXTVAL(('"tarjeta_id_seq"'::text)::regclass),
	CLIENTE_ID integer NOT NULL,
	NUMERO varchar(256) NOT NULL,
	CLAVE varchar(256) NULL,
	CUPO_APROBADO numeric(18,2) NOT NULL,
	CUPO_DISPONIBLE numeric(18,2) NOT NULL,
	DIA_CORTE integer NOT NULL,
	FECHA_EMISION date NOT NULL,
	ESTADO varchar(3) NOT NULL,
	FECHA_CREACION timestamp with time zone NULL,
	FECHA_ACTUALIZACION timestamp with time zone NULL
)
;

CREATE TABLE TRANSACCION
(
	ID integer NOT NULL   DEFAULT NEXTVAL(('"transaccion_id_seq"'::text)::regclass),
	CUENTA_ID integer NULL,
	TARJETA_ID integer NULL,
	TIPO varchar(3) NOT NULL,
	CANAL varchar(3) NOT NULL,
	FECHA_HORA timestamp with time zone NOT NULL,
	VALOR numeric(18,2) NOT NULL,
	COMISION numeric(18,2) NOT NULL,
	TAZA_INTERES numeric(18,2) NOT NULL,
	ES_DIFERIDO boolean NOT NULL,
	ESTADO varchar(3) NOT NULL,
	FECHA_CREACION timestamp with time zone NULL,
	FECHA_ACTUALIZACION timestamp with time zone NULL
)
;

CREATE TABLE USUARIO
(
	ID integer NOT NULL   DEFAULT NEXTVAL(('"usuario_id_seq"'::text)::regclass),
	CLIENTE_ID integer NULL,
	ROL varchar(3) NOT NULL,
	USUARIO varchar(32) NOT NULL,
	CONTRASENIA varchar(256) NOT NULL,
	IMG varchar(50) NOT NULL,
	ESTADO varchar(3) NOT NULL,
	FECHA_ULTIMO_INGRESO timestamp with time zone NULL,
	IP_ULTIMO_INGRESO varchar(15) NULL,
	FECHA_CREACION timestamp with time zone NULL,
	FECHA_ACTUALIZACION timestamp with time zone NULL
)
;

CREATE SEQUENCE cliente_id_seq INCREMENT 1 START 1
;

CREATE SEQUENCE cuenta_id_seq INCREMENT 1 START 1
;

CREATE SEQUENCE diferido_id_seq INCREMENT 1 START 1
;

CREATE SEQUENCE direccion_id_seq INCREMENT 1 START 1
;

CREATE SEQUENCE disputa_id_seq INCREMENT 1 START 1
;

CREATE SEQUENCE notificacion_id_seq INCREMENT 1 START 1
;

CREATE SEQUENCE persona_juridica_id_seq INCREMENT 1 START 1
;

CREATE SEQUENCE persona_natural_id_seq INCREMENT 1 START 1
;

CREATE SEQUENCE seguridad_marca_id_seq INCREMENT 1 START 1
;

CREATE SEQUENCE tarjeta_id_seq INCREMENT 1 START 1
;

CREATE SEQUENCE transaccion_id_seq INCREMENT 1 START 1
;

CREATE SEQUENCE usuario_id_seq INCREMENT 1 START 1
;

ALTER TABLE CLIENTE ADD CONSTRAINT PK_CLIENTE
	PRIMARY KEY (ID)
;

ALTER TABLE CUENTA ADD CONSTRAINT PK_CUENTA
	PRIMARY KEY (ID)
;

ALTER TABLE DETALLE_TRANSACCION ADD CONSTRAINT PK_DETALLE_TRANSACCION
	PRIMARY KEY (TRANSACCION_ID)
;

ALTER TABLE DIFERIDO ADD CONSTRAINT PK_DIFERIDO
	PRIMARY KEY (ID)
;

ALTER TABLE DIRECCION ADD CONSTRAINT PK_DIRECCION
	PRIMARY KEY (ID)
;

ALTER TABLE DISPUTA ADD CONSTRAINT PK_DISPUTA
	PRIMARY KEY (ID)
;

ALTER TABLE NOTIFICACION ADD CONSTRAINT PK_NOTIFICACION
	PRIMARY KEY (ID)
;

ALTER TABLE PERSONA_JURIDICA ADD CONSTRAINT PK_PERSONA_JURIDICA
	PRIMARY KEY (ID)
;

ALTER TABLE PERSONA_NATURAL ADD CONSTRAINT PK_PERSONA_NATURAL
	PRIMARY KEY (ID)
;

ALTER TABLE SEGURIDAD_MARCA ADD CONSTRAINT PK_SEGURIDAD_MARCA
	PRIMARY KEY (ID)
;

ALTER TABLE TARJETA ADD CONSTRAINT PK_TARJETA
	PRIMARY KEY (ID)
;

ALTER TABLE TRANSACCION ADD CONSTRAINT PK_TRANSACCION
	PRIMARY KEY (ID)
;

ALTER TABLE USUARIO ADD CONSTRAINT PK_USUARIO
	PRIMARY KEY (ID)
;

CREATE INDEX IXFK_CLIENTE_PERSONA_JURIDICA ON CLIENTE (PERSONA_JURIDICA_ID ASC)
;

CREATE INDEX IXFK_CLIENTE_PERSONA_NATURAL ON CLIENTE (PERSONA_NATURAL_ID ASC)
;

CREATE INDEX IXFK_CUENTA_CLIENTE ON CUENTA (CLIENTE_ID ASC)
;

CREATE INDEX IXFK_DETALLE_TRANSACCION_TRANSACCION ON DETALLE_TRANSACCION (TRANSACCION_ID ASC)
;

CREATE INDEX IXFK_DIFERIDO_TRANSACCION ON DIFERIDO (TRANSACCION_ID ASC)
;

CREATE INDEX IXFK_DIRECCION_PERSONA_JURIDICA ON DIRECCION (PERSONA_JURIDICA_ID ASC)
;

CREATE INDEX IXFK_DIRECCION_PERSONA_NATURAL ON DIRECCION (PERSONA_NATURAL_ID ASC)
;

CREATE INDEX IXFK_DISPUTA_TRANSACCION ON DISPUTA (TRANSACCION_ID ASC)
;

CREATE INDEX IXFK_NOTIFICACION_CLIENTE ON NOTIFICACION (CLIENTE_ID ASC)
;

CREATE INDEX IXFK_NOTIFICACION_TRANSACCION ON NOTIFICACION (TRANSACCION_ID ASC)
;

CREATE UNIQUE INDEX IX_RUC ON PERSONA_JURIDICA (RUC ASC)
;

CREATE UNIQUE INDEX IX_IDENTIFICACION ON PERSONA_NATURAL (IDENTIFICACION ASC)
;

CREATE INDEX IXFK_TARJETA_CLIENTE ON TARJETA (CLIENTE_ID ASC)
;

CREATE INDEX IXFK_TRANSACCION_CUENTA ON TRANSACCION (CUENTA_ID ASC)
;

CREATE INDEX IXFK_TRANSACCION_TARJETA ON TRANSACCION (TARJETA_ID ASC)
;

CREATE INDEX IXFK_USUARIO_CLIENTE ON USUARIO (CLIENTE_ID ASC)
;

ALTER TABLE CUENTA 
  ADD CONSTRAINT UQ_NUMERO_CUENTA UNIQUE (NUMERO)
;

ALTER TABLE CLIENTE ADD CONSTRAINT CHK_ESTADO_CLIENTE CHECK (ESTADO IN ('ACT', 'INA'))
;

ALTER TABLE CLIENTE ADD CONSTRAINT CHK_TIPO_CLIENTE CHECK (TIPO IN ('COR', 'PER'))
;

ALTER TABLE CUENTA ADD CONSTRAINT CHK_ESTADO_CUENTA CHECK (ESTADO IN ('ACT', 'INA'))
;

ALTER TABLE CUENTA ADD CONSTRAINT CHK_TIPO_CUENTA CHECK (TIPO IN ('AHO', 'COR')
)
;

ALTER TABLE DETALLE_TRANSACCION ADD CONSTRAINT CHK_TIPO_CUENTA_DETALLE_TRANSACCION CHECK (TIPO_CUENTA IN ('AHO', 'COR'))
;

ALTER TABLE DIFERIDO ADD CONSTRAINT CHK_ESTADO_DIFERIDO CHECK (ESTADO IN ('PAG', 'PEN'))
;

ALTER TABLE DISPUTA ADD CONSTRAINT CHK_ESTADO_DISPUTA CHECK (ESTADO IN ('PEN', 'RES'))
;

ALTER TABLE NOTIFICACION ADD CONSTRAINT CHK_ESTADO_NOTIFICACION CHECK (ESTADO IN ('ACT', 'INA'))
;

ALTER TABLE NOTIFICACION ADD CONSTRAINT CHK_TIPO_NOTIFICACION CHECK (TIPO IN ('TRA', 'SLD', 'ALR', 'VEN')
)
;

ALTER TABLE PERSONA_NATURAL ADD CONSTRAINT CHK_TIPO_IDENTIFICACION CHECK (TIPO_IDENTIFICACION IN ('CED', 'RUC', 'PAS'))
;

ALTER TABLE TARJETA ADD CONSTRAINT CHK_ESTADO_TARJETA CHECK (ESTADO IN ('ACT', 'INA', 'BLO'))
;

ALTER TABLE TRANSACCION ADD CONSTRAINT CHK_CANAL_TRANSACCION CHECK (CANAL IN ('WEB', 'EXT'))
;

ALTER TABLE TRANSACCION ADD CONSTRAINT CHK_ESTADO_TRANSACCION CHECK (ESTADO IN ('ANU', 'APR', 'REC', 'REV', 'PEN'))
;

ALTER TABLE TRANSACCION ADD CONSTRAINT CHK_TIPO_TRANSACCION CHECK (TIPO IN ('DEP', 'PTC', 'CNS', 'REV', 'TRI', 'TRE', 'PAC')
)
;

ALTER TABLE USUARIO ADD CONSTRAINT CHK_ESTADO_USUARIO CHECK (ESTADO IN ('ACT', 'INA', 'BLO'))
;

ALTER TABLE USUARIO ADD CONSTRAINT CHK_ROL_USUARIO CHECK (ROL IN ('ADN', 'USR'))
;

ALTER TABLE CLIENTE ADD CONSTRAINT FK_CLIENTE_PERSONA_JURIDICA
	FOREIGN KEY (PERSONA_JURIDICA_ID) REFERENCES PERSONA_JURIDICA (ID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE CLIENTE ADD CONSTRAINT FK_CLIENTE_PERSONA_NATURAL
	FOREIGN KEY (PERSONA_NATURAL_ID) REFERENCES PERSONA_NATURAL (ID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE CUENTA ADD CONSTRAINT FK_CUENTA_CLIENTE
	FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTE (ID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE DETALLE_TRANSACCION ADD CONSTRAINT FK_DETALLE_TRANSACCION_TRANSACCION
	FOREIGN KEY (TRANSACCION_ID) REFERENCES TRANSACCION (ID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE DIFERIDO ADD CONSTRAINT FK_DIFERIDO_TRANSACCION
	FOREIGN KEY (TRANSACCION_ID) REFERENCES TRANSACCION (ID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE DIRECCION ADD CONSTRAINT FK_DIRECCION_PERSONA_JURIDICA
	FOREIGN KEY (PERSONA_JURIDICA_ID) REFERENCES PERSONA_JURIDICA (ID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE DIRECCION ADD CONSTRAINT FK_DIRECCION_PERSONA_NATURAL
	FOREIGN KEY (PERSONA_NATURAL_ID) REFERENCES PERSONA_NATURAL (ID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE DISPUTA ADD CONSTRAINT FK_DISPUTA_TRANSACCION
	FOREIGN KEY (TRANSACCION_ID) REFERENCES TRANSACCION (ID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE NOTIFICACION ADD CONSTRAINT FK_NOTIFICACION_CLIENTE
	FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTE (ID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE NOTIFICACION ADD CONSTRAINT FK_NOTIFICACION_TRANSACCION
	FOREIGN KEY (TRANSACCION_ID) REFERENCES TRANSACCION (ID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE TARJETA ADD CONSTRAINT FK_TARJETA_CLIENTE
	FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTE (ID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE TRANSACCION ADD CONSTRAINT FK_TRANSACCION_CUENTA
	FOREIGN KEY (CUENTA_ID) REFERENCES CUENTA (ID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE TRANSACCION ADD CONSTRAINT FK_TRANSACCION_TARJETA
	FOREIGN KEY (TARJETA_ID) REFERENCES TARJETA (ID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE USUARIO ADD CONSTRAINT FK_USUARIO_CLIENTE
	FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTE (ID) ON DELETE No Action ON UPDATE No Action
;

