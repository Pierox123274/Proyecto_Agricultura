-- Crear la base de datos
CREATE DATABASE agricultura;
GO

-- Usar la base de datos creada
USE agricultura;
GO

-- Crear tabla Usuario
CREATE TABLE Usuario (
    IDUsuario INT PRIMARY KEY IDENTITY(1,1),
    UsuNombre VARCHAR(100) NOT NULL,
    UsuDireccion VARCHAR(200),
    UsuCorreo VARCHAR(100) CHECK (UsuCorreo LIKE '%@%.%'),
    UsuTelefono VARCHAR(15)
);

-- Crear tabla Terreno
CREATE TABLE Terreno (
    IDTerreno INT PRIMARY KEY IDENTITY(1,1),
    terNombre VARCHAR(100) NOT NULL,
    terTama�o DECIMAL(10,2) CHECK (terTama�o > 0),
    terUbicacion VARCHAR(200),
    terTipo VARCHAR(50)
);

-- Crear tabla Cultivo
CREATE TABLE Cultivo (
    IDCultivo INT PRIMARY KEY IDENTITY(1,1),
    CulTipo VARCHAR(50) NOT NULL,
    CulVariedad VARCHAR(50),
    CulTemporada VARCHAR(20)
);

-- Crear tabla intermedia para relaci�n N:N entre Cultivo y Terreno
CREATE TABLE CultivoTerreno (
    IDCultivo INT FOREIGN KEY REFERENCES Cultivo(IDCultivo),
    IDTerreno INT FOREIGN KEY REFERENCES Terreno(IDTerreno),
    PRIMARY KEY (IDCultivo, IDTerreno)
);

-- Crear tabla DetalleCultivo
CREATE TABLE DetalleCultivo (
    IDDetalleCultivo INT PRIMARY KEY IDENTITY(1,1),
    IDCultivo INT FOREIGN KEY REFERENCES Cultivo(IDCultivo),
    DtcResponsable VARCHAR(100) NOT NULL,
    DtcFechaSiembra DATE NOT NULL,
    DtcFechaCosecha DATE,
    DtcObservaciones TEXT
);


-- Insertar 10 usuarios
INSERT INTO Usuario (UsuNombre, UsuDireccion, UsuCorreo, UsuTelefono) VALUES
('Juan P�rez', 'Calle Primavera 123, Huancayo', 'juan.perez@email.com', '987654321'),
('Mar�a G�mez', 'Av. Libertad 456, Jauja', 'maria.gomez@email.com', '987654322'),
('Carlos Rojas', 'Jr. Lima 789, Concepci�n', 'carlos.rojas@email.com', '987654323'),
('Ana Torres', 'Calle Sol 234, Chupaca', 'ana.torres@email.com', '987654324'),
('Luis Mendoza', 'Av. Bol�var 567, Huancayo', 'luis.mendoza@email.com', '987654325'),
('Rosa Vargas', 'Jr. Ayacucho 890, Jauja', 'rosa.vargas@email.com', '987654326'),
('Pedro Castro', 'Calle Jun�n 345, Concepci�n', 'pedro.castro@email.com', '987654327'),
('Sof�a Ruiz', 'Av. Grau 678, Chupaca', 'sofia.ruiz@email.com', '987654328'),
('Miguel D�az', 'Jr. Cusco 901, Huancayo', 'miguel.diaz@email.com', '987654329'),
('Luc�a Herrera', 'Calle Huancavelica 123, Jauja', 'lucia.herrera@email.com', '987654330');

-- Insertar 10 terrenos
INSERT INTO Terreno (terNombre, terTama�o, terUbicacion, terTipo) VALUES
('Parcela Norte', 2.5, 'Huancayo, Sector Norte', 'Regad�o'),
('Campo Sur', 3.0, 'Jauja, Valle Sur', 'Secano'),
('Chacra Central', 1.8, 'Concepci�n, Zona Central', 'Mixto'),
('Fundo Esperanza', 4.2, 'Chupaca, Distrito Esperanza', 'Regad�o'),
('Terreno Los Olivos', 2.0, 'Huancayo, Los Olivos', 'Secano'),
('Hacienda Verde', 5.5, 'Jauja, Valle Verde', 'Regad�o'),
('Parcela El Sol', 1.5, 'Concepci�n, Alto El Sol', 'Mixto'),
('Campo Florido', 3.8, 'Chupaca, Florido', 'Regad�o'),
('Chacra San Juan', 2.2, 'Huancayo, San Juan', 'Secano'),
('Fundo Primavera', 4.0, 'Jauja, Primavera', 'Regad�o');

-- Insertar 10 cultivos
INSERT INTO Cultivo (CulTipo, CulVariedad, CulTemporada) VALUES
('Ma�z', 'Amarillo duro', 'Verano 2024'),
('Papa', 'Yungay', 'Invierno 2024'),
('Quinua', 'Blanca', 'Verano 2024'),
('Trigo', 'Barbilla', 'Invierno 2024'),
('Cebada', 'Forrajera', 'Verano 2024'),
('Haba', 'Granada', 'Invierno 2024'),
('Arveja', 'Green', 'Verano 2024'),
('Oca', 'Rosada', 'Invierno 2024'),
('Mashua', 'Amarilla', 'Verano 2024'),
('Olluco', 'Lisa', 'Invierno 2024');

-- Insertar relaciones Cultivo-Terreno (N:N)
INSERT INTO CultivoTerreno (IDCultivo, IDTerreno) VALUES
(1, 1), (1, 2), (2, 3), (2, 4), (3, 5),
(3, 6), (4, 7), (4, 8), (5, 9), (5, 10),
(6, 1), (6, 3), (7, 2), (7, 4), (8, 5),
(8, 7), (9, 6), (9, 8), (10, 9), (10, 10);

-- Insertar 10 detalles de cultivo
INSERT INTO DetalleCultivo (IDCultivo, DtcResponsable, DtcFechaSiembra, DtcFechaCosecha, DtcObservaciones) VALUES
(1, 'Juan P�rez', '2024-01-15', '2024-05-20', 'Cultivo de ma�z con riego por goteo'),
(2, 'Mar�a G�mez', '2024-02-10', '2024-07-15', 'Papa sembrada en terreno con pendiente'),
(3, 'Carlos Rojas', '2024-01-20', '2024-06-10', 'Quinua org�nica sin pesticidas'),
(4, 'Ana Torres', '2024-02-05', '2024-07-20', 'Trigo para producci�n de harina'),
(5, 'Luis Mendoza', '2024-01-25', '2024-06-15', 'Cebada para alimentaci�n animal'),
(6, 'Rosa Vargas', '2024-02-15', '2024-07-25', 'Haba asociada con ma�z'),
(7, 'Pedro Castro', '2024-01-30', '2024-06-20', 'Arveja para consumo fresco'),
(8, 'Sof�a Ruiz', '2024-02-20', '2024-08-05', 'Oca con abono org�nico'),
(9, 'Miguel D�az', '2024-02-01', '2024-07-10', 'Mashua para procesamiento'),
(10, 'Luc�a Herrera', '2024-02-25', '2024-08-15', 'Olluco de variedad local');