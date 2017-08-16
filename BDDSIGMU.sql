create table contribuyente (
 ruc varchar,
 razon_social varchar,
 nom_comercial varchar,
 estado varchar,
 clase varchar,
 inicio_act varchar,
 fec_actualizacion varchar,
 fec_suspension varchar,
 reinicio_act varchar,
 obligado varchar,
 tipo_cont varchar,
 num_estab varchar,
 nom_fantasia varchar,
 calle varchar,
 num_calle varchar,
 interseccion varchar,
 estado_est varchar,
 provincia varchar,
 canton varchar,
 parroquia varchar,
 codigo_ciiu varchar,
 actividad_eco varchar
);

copy contribuyente from 'C:\contribuyente.txt'  USING DELIMITERS ';'

CREATE TABLE Auditoria(
   id int,
   modulo 		varchar(50) 		NOT NULL,
   accion		varchar(50) 		NOT NULL,
   cuenta		varchar(50) 		NOT NULL,
   usuario		varchar(50) 		NOT NULL,
   equipo		varchar(50) 		NOT NULL,
   mac			varchar(50) 		NOT NULL,
   ip			varchar(50) 		NOT NULL,
   fecha		date,
   CONSTRAINT PK_Auditoria PRIMARY KEY (id) 
);

CREATE TABLE Ciudad(
   id int,
   nombre 		varchar(100) 		NOT NULL,
   CONSTRAINT PK_Ciudad PRIMARY KEY (id) 
);

CREATE TABLE Ciudadano(
   id int,   
   tipo			varchar(20) 		NOT NULL,
   identificacion	varchar(50) 		NOT NULL,
   apellidos		varchar(100) 		NOT NULL,
   nombres		varchar(100) 		NOT NULL,
   fechaNac		date			NULL,
   estadoCivil		varchar(15) 		NULL,
   sexo			varchar(10) 		NULL,
   ciudad		varchar(100) 		NOT NULL,
   email		varchar(100) 		NULL,
   telf			varchar(7) 		NULL,
   celular		varchar(10) 		NULL,
   direccion		varchar(200) 		NULL,
   CONSTRAINT PK_Ciudadano PRIMARY KEY(id) 
);

CREATE TABLE Institucion(
   id int,
   ruc			varchar(20) 		NOT NULL,
   nombre		varchar(100) 		NOT NULL,
   direccion		varchar(100) 		NOT NULL,
   provincia		varchar(2) 		NOT NULL,
   canton		varchar(2)		NOT NULL,
   alcalde		varchar(100) 		NOT NULL,
   directorCatastro	varchar(100)		NULL,
   telefonos		varchar(50) 		NULL,
   paginaWeb		varchar(150) 		NULL,
   CONSTRAINT PK_Institucion PRIMARY KEY (id) 
);

CREATE TABLE InteresMoral(
   id int,
   año 			integer			NOT NULL,
   interes		decimal(5, 2) 		NOT NULL,
   CONSTRAINT PK_InteresMoral PRIMARY KEY (id)
);

CREATE TABLE Sitio(
   id int,
   nombre		varchar(100)		NOT NULL,
   CONSTRAINT PK_Sitio PRIMARY KEY (id) 
);

CREATE TABLE Usuario(
   id serial,
   identificacion	varchar(15)		NOT NULL,
   apellido		varchar(150)		NOT NULL,
   nombre		varchar(150)		NOT NULL,
   usuario		varchar(50)		NOT NULL,
   clave		varchar(300)		NOT NULL,
   tipo			varchar(50)		NOT NULL,
   estado		varchar(10)		NOT NULL,
   ultimoAcceso		timestamp without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NULL,
   CONSTRAINT PK_Usuario PRIMARY KEY (id)
);

CREATE TABLE CalidadSuelo(
   id serial,
   tipoRiesgo		varchar(25)		NOT NULL,
   indicador		float   		NOT NULL,
   CONSTRAINT PK_CalidadSuelo PRIMARY KEY (id) 
);

CREATE TABLE CarteraVencida(
   id serial,
   ClaveCatastral		varchar(18)	NOT NULL,
   Numero_Titulo		varchar(50) 	NOT NULL,
   Valor_T_Terreno_Predio 	decimal(18, 2) 	NOT NULL,
   Valor_T_Edificio_Predio 	decimal(18, 2) 	NOT NULL,
   Valor_Comercial		decimal(18, 2) 	NOT NULL,
   Base_Imponible		decimal(18, 2) 	NOT NULL,
   Valor_IPR			decimal(18, 2) 	NOT NULL,
   Fecha_Emision 		timestamp without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
   Valor_Emitido		decimal(18, 2) 	NOT NULL,
   Descuento			decimal(18, 2) 	NOT NULL,
   Recargo			decimal(18, 2) 	NOT NULL,
   Interes			decimal(18, 2) 	NOT NULL,
   Valor_T_Cobrado		decimal(18, 2) 	NULL,
   Fecha_Recaudacion		timestamp without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NULL,
   Tasa_Administrativa		decimal(18, 2) 	NOT NULL,
   Observaciones		varchar(200)	NULL,
   UsuarioEmite			varchar(50) 	NOT NULL,
   Identificacion		varchar(25) 	NOT NULL,
   Nombres			varchar(100) 	NOT NULL,
   Direccion			varchar(200) 	NOT NULL,
   UsuarioRecauda		varchar(50) 	NULL,
   Estado			varchar(1) 	NOT NULL,
   CONSTRAINT PK_CarteraVencida PRIMARY KEY (id)
);

CREATE TABLE ParametrosGenerales(
   id serial,
   impuestoPredial	numeric(8, 4)		NOT NULL,
   tasaAdministrativa	numeric(8, 2)		NOT NULL,
   rbu			numeric(8, 2)		NOT NULL,
   CONSTRAINT PK_ParametrosGenerales PRIMARY KEY (id)
);

CREATE TABLE PlanoValores(
   id serial,
   calidadSuelo		integer			NOT NULL,
   sh			integer			NOT NULL,
   valor		numeric(10, 2)		NOT NULL,
   CONSTRAINT PK_PlanoValores PRIMARY KEY (id)
);

CREATE TABLE TitulosActuales(
   id serial,
   ClaveCatastral		varchar(18) 	NOT NULL,
   Numero_Titulo		varchar(50)	NULL,
   Valor_T_Terreno_Predio	decimal(18, 2)	NULL,
   Valor_T_Edificio_Predio	decimal(18, 2)	NULL,
   Valor_Comercial		decimal(18, 2)	NULL,
   ValorExoneracion		decimal(18, 2)	NULL,
   Base_Imponible		decimal(18, 2)	NULL,
   Valor_IPR			decimal(18, 2)	NULL,
   Fecha_Emision		timestamp without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NULL,
   Valor_Emitido		decimal(18, 2)	NULL,
   Descuento			decimal(18, 2)	NULL,
   Recargo			decimal(18, 2)	NULL,
   Valor_T_Cobrado		decimal(18, 2)	NULL,
   Fecha_Recaudacion		timestamp without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NULL,
   Tasa_Administrativa		decimal(18, 2)	NULL,
   Observaciones		varchar(200)	NULL,
   UsuarioEmite			varchar(50)	NULL,
   Identificacion		varchar(25)	NULL,
   Nombres			varchar(300)	NULL,
   Direccion			varchar(100)	NULL,
   UsuarioRecauda		varchar(50)	NULL,
   Estado			varchar(1)	NULL,
   CONSTRAINT PK_TitulosActuales PRIMARY KEY (id)
);

CREATE TABLE Parroquia(
   id int,
   codigo		varchar(3) 		NULL,
   nombre		varchar(50) 		NULL,
   CONSTRAINT PK_Parroquia PRIMARY KEY (id) 
);

CREATE TABLE Zona(
   id serial,
   idParroquia		integer			NOT NULL,
   codigo		varchar(2)		NOT NULL,
   nombre		varchar(50)		NULL,
   CONSTRAINT PK_Zona PRIMARY KEY (id),
   CONSTRAINT FK_Parroquia_Zona FOREIGN KEY (idParroquia) references Parroquia (id)
);

CREATE TABLE Sector(
   id serial,
   idZona		integer			NOT NULL,
   codigo		varchar(2)		NOT NULL,
   nombre		varchar(50)		NULL,
   CONSTRAINT PK_Sector PRIMARY KEY (id),
   CONSTRAINT FK_Zona_Sector FOREIGN KEY (idZona) references Zona (id)
);

CREATE TABLE Manzana(
   id serial,
   idSector 		integer 		NOT NULL,
   codigo		varchar(2)		NOT NULL,
   nombre		varchar(50)		NULL,
   CONSTRAINT PK_Manzana PRIMARY KEY (id),
   CONSTRAINT FK_Sector_Manzana FOREIGN KEY (idSector) references Sector (id)
);

CREATE TABLE NumPredio(
   id serial,
   idManzana		integer			NOT NULL,
   numero		integer			NOT NULL,
   CONSTRAINT PK_NumPredio PRIMARY KEY (id),
   CONSTRAINT FK_Manzana_NumPredio FOREIGN KEY (idManzana) references Manzana (id) 
);

CREATE TABLE Predio(
   id serial,
   claveCatastral	varchar(20)		NOT NULL,
   claveAnterior	varchar(20)		NULL,
   sitio		varchar(100)		NOT NULL,
   nombrePredio		varchar(100)		NOT NULL,
   dominio		varchar(50)		NULL,
   traslacionDominio	varchar(50)		NULL,
   escritura		varchar(50)		NULL,
   notaria		varchar(50)		NULL,
   notario		varchar(50)		NULL,
   lugarEscritura	varchar(50)		NULL,
   registroPropiedad	varchar(50)		NULL,
   imagenPredio		varchar(50)		NULL,
   fAerea		varchar(50)		NULL,
   ftopografico		varchar(50)		NULL,
   Otros		varchar(50)		NULL,
   coordenadaX		varchar(50)		NULL,
   coordenadaY		varchar(50)		NULL,
   norte		varchar(50)		NULL,
   sur			varchar(50)		NULL,
   este			varchar(50)		NULL,
   oeste		varchar(50)		NULL,
   fechaInscripcion	date			NULL,
   observacion		varchar(300)		NULL,
   estado		varchar(20)		NOT NULL,
   fechaIngreso		date			NOT NULL,
   CONSTRAINT PK_Predio PRIMARY KEY (id) 
);

CREATE TABLE PredioValor(
   id serial,
   idPredio		integer			NOT NULL,
   sectorHomogeneo	integer			NOT NULL,
   calidadSuelo		integer			NOT NULL,
   precioBase		numeric(10, 2)		NOT NULL,
   factorAfectacion	numeric(10, 2)		NOT NULL,
   superficieM2		numeric(10, 2)		NOT NULL,
   CONSTRAINT PK_PredioValor PRIMARY KEY (id),
   CONSTRAINT FK_Predio_PredioValor FOREIGN KEY (idPredio) references Predio (id)
);

CREATE TABLE AccesibilidadRiego(
   id int,
   frecuencia		varchar(25)		NOT NULL,
   indicador		float			NOT NULL,
   CONSTRAINT PK_AccesibilidadRiego PRIMARY KEY (id)
);

CREATE TABLE AccesoyVias(
   id int,
   accesoYvias		varchar(25)		NOT NULL,
   indicador		float			NOT NULL,
   CONSTRAINT PK_AccesoyVias PRIMARY KEY (id) 
);

CREATE TABLE Drenaje(
   id int,
   drenaje		varchar(25)		NOT NULL,
   indicador		float			NOT NULL,
   CONSTRAINT PK_Drenaje PRIMARY KEY (id) 
);

CREATE TABLE Erosion(
   id int,
   erosion		varchar(20)		NOT NULL,
   indicador		float			NOT NULL,
   CONSTRAINT PK_Erosion PRIMARY KEY (id) 
);

CREATE TABLE FormaPredio(
   id int,
   formaPredio		varchar(25)		NOT NULL,
   indicador		float			NOT NULL,
   CONSTRAINT PK_FormaPredio PRIMARY KEY (id) 
);

CREATE TABLE PoblCercanas(
   id int,
   poblacionesCercanas	varchar(25)		NOT NULL,
   indicador		float			NOT NULL,
   CONSTRAINT PK_PoblCercanas PRIMARY KEY (id) 
);

CREATE TABLE ServBasicos(
   id INT,
   serviciosBasicos	varchar(25)		NOT NULL,
   indicadores		FLOAT			NOT NULL,
   CONSTRAINT PK_ServBasicos PRIMARY KEY (id)
);

CREATE TABLE Superficie(
   id int,
   superficie		varchar(30)		NOT NULL,
   indicador		float			NOT NULL,
   CONSTRAINT PK_Superficie PRIMARY KEY (id)
);


CREATE TABLE TipoRiesgos(
   id int,
   riesgos		varchar(30)		NOT NULL,
   indicador		float			NOT NULL,
   CONSTRAINT PK_TipoRiesgos PRIMARY KEY (id) 
);

CREATE TABLE Topograficos(
   id int,
   topograficos		varchar(25)		NOT NULL,
   indicadores		float			NOT NULL,
   CONSTRAINT PK_Topograficos PRIMARY KEY (id)
);

CREATE TABLE PredioParametros(
   id serial,
   idPredio		integer			NOT NULL,
   idFormaPredio	integer			NOT NULL,
   idPoblCercanas	integer			NOT NULL,
   idTopograficos	integer			NOT NULL,
   idAccesibilidadRiego	integer			NOT NULL,
   idTipoRiesgos	integer			NOT NULL,
   idAccesoyVias	integer			NOT NULL,
   idServBasicos	integer			NOT NULL,
   idErosion		integer			NOT NULL,
   idDrenaje		integer			NOT NULL,
   idSuperficie		integer			NOT NULL,
   CONSTRAINT PK_PredioParametros PRIMARY KEY (id),
   CONSTRAINT FK_Predio_PredioParametros FOREIGN KEY (idPredio) references Predio (id),
   CONSTRAINT FK_FormaPredio_PredioParametros FOREIGN KEY (idFormaPredio) references FormaPredio (id),
   CONSTRAINT FK_PoblCercanas_PredioParametros FOREIGN KEY (idPoblCercanas) references PoblCercanas (id),
   CONSTRAINT FK_Topograficos_PredioParametros FOREIGN KEY (idTopograficos) references Topograficos (id),
   CONSTRAINT FK_AccesibilidadRiego_PredioParametros FOREIGN KEY (idAccesibilidadRiego) references AccesibilidadRiego (id),
   CONSTRAINT FK_TipoRiesgos_PredioParametros FOREIGN KEY (idTipoRiesgos) references TipoRiesgos (id),
   CONSTRAINT FK_AccesoyVias_PredioParametros FOREIGN KEY (idAccesoyVias) references AccesoyVias (id),
   CONSTRAINT FK_ServBasicos_PredioParametros FOREIGN KEY (idServBasicos) references ServBasicos (id),
   CONSTRAINT FK_Erosion_PredioParametros FOREIGN KEY (idErosion) references Erosion (id),
   CONSTRAINT FK_Drenaje_PredioParametros FOREIGN KEY (idDrenaje) references Drenaje (id),
   CONSTRAINT FK_Superficie_PredioParametros FOREIGN KEY (idSuperficie) references Superficie (id)
);


INSERT INTO Ciudad (id, nombre) VALUES (1, 'JIPIJAPA');

INSERT INTO Institucion (id, ruc, nombre, direccion, provincia, canton, alcalde, directorCatastro, telefonos, paginaWeb) 
VALUES (1, '1360000630001', 'GAD MUNICIPAL DE JIPIJAPA', 'SUCRE Y MONTALVO', '13', '06', 'SR. TEODORO ANDRADE', 'ING. JONNY SOLEDISPA', '052600429', 'www.jipijapa.gob.rc');

INSERT INTO Parroquia (id, codigo, nombre) VALUES (1, '50', 'JIPIJAPA');
INSERT INTO Parroquia (id, codigo, nombre) VALUES (2, '51', 'AMERICA');
INSERT INTO Parroquia (id, codigo, nombre) VALUES (3, '52', 'EL ANEGADO');
INSERT INTO Parroquia (id, codigo, nombre) VALUES (4, '53', 'JULCUY');
INSERT INTO Parroquia (id, codigo, nombre) VALUES (5, '54', 'LA UNION');
INSERT INTO Parroquia (id, codigo, nombre) VALUES (6, '56', 'MEMBRILLAL');
INSERT INTO Parroquia (id, codigo, nombre) VALUES (7, '57', 'PEDRO PABLO GOMEZ');
INSERT INTO Parroquia (id, codigo, nombre) VALUES (8, '58', 'PUERTO CAYO');

INSERT INTO AccesoyVias (id, accesoYvias, indicador) VALUES (1, 'Primer Orden', 1.00);
INSERT INTO AccesoyVias (id, accesoYvias, indicador) VALUES (2, 'Segundo Orden', CAST(0.98 AS Decimal(10, 2)));
INSERT INTO AccesoyVias (id, accesoYvias, indicador) VALUES (3, 'Tercer Orden', CAST(0.97 AS Decimal(10, 2)));
INSERT INTO AccesoyVias (id, accesoYvias, indicador) VALUES (4, 'Herradura', CAST(0.96 AS Decimal(10, 2)));
INSERT INTO AccesoyVias (id, accesoYvias, indicador) VALUES (5, 'Fluvial', CAST(0.95 AS Decimal(10, 2)));
INSERT INTO AccesoyVias (id, accesoYvias, indicador) VALUES (6, 'Línea Férrea', CAST(0.94 AS Decimal(10, 2)));
INSERT INTO AccesoyVias (id, accesoYvias, indicador) VALUES (7, 'No Tiene', CAST(0.93 AS Decimal(10, 2)));

select * from AccesoyVias;
/**

CREATE TABLE Tenencia(
   id serial,
   idPredio int NOT NULL,
   idPropietario int NOT NULL,
   descTenencia varchar(25) NOT NULL,
 CONSTRAINT PK_Tenencia PRIMARY KEY (id) 
)

CREATE TABLE [dbo].[tR_IndicadoresPredio](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[idCatastral] [int] NOT NULL,
	[accesibilidadRiego] [numeric](10, 2) NOT NULL,
	[accesoVias] [numeric](10, 2) NOT NULL,
	[drenaje] [numeric](10, 2) NOT NULL,
	[erosion] [numeric](10, 2) NOT NULL,
	[formaPredio] [numeric](10, 2) NOT NULL,
	[poblCercanas] [numeric](10, 2) NOT NULL,
	[servBasicos] [numeric](10, 2) NOT NULL,
	[tipoRiesgos] [numeric](10, 2) NOT NULL,
	[topograficos] [numeric](10, 2) NOT NULL,
	[superficie] [numeric](10, 2) NOT NULL,
 CONSTRAINT [PK_tR_ValoresPredio] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
**/
