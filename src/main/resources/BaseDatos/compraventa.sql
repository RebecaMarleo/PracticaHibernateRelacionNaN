DROP DATABASE IF EXISTS CompraventaVideojuegos;
CREATE DATABASE CompraventaVideojuegos;
USE CompraventaVideojuegos;

CREATE TABLE Cliente(
    idCliente INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    correo VARCHAR(50),
    nombre VARCHAR(100)
);

CREATE TABLE Empleado(
    idEmpleado INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    correo VARCHAR(50),
    contrasena VARCHAR(256),
    nombre VARCHAR(100)
);

CREATE TABLE Juego(
    idJuego INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100),
    precioVenta DECIMAL(10, 2),
    precioCompra DECIMAL(10, 2),
    pegi VARCHAR(7)
);

CREATE TABLE Transaccion(
    idTransaccion INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    idCliente INT UNSIGNED,
    idEmpleado INT UNSIGNED,
    total DECIMAL(10, 2),
    tipo VARCHAR(6),
    FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente) ON UPDATE CASCADE,
    FOREIGN KEY (idEmpleado) REFERENCES Empleado(idEmpleado) ON UPDATE CASCADE
);

CREATE TABLE Juego_Transaccion(
    idJuegoTransaccion INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    idTransaccion INT UNSIGNED,
    idJuego INT UNSIGNED,
    cantidad INT,
    FOREIGN KEY (idTransaccion) REFERENCES Transaccion(idTransaccion) ON DELETE CASCADE,
    FOREIGN KEY (idJuego) REFERENCES Juego(idJuego) ON UPDATE CASCADE
);

CREATE TABLE Etiqueta(
    idEtiqueta INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30)
);

CREATE TABLE Etiqueta_Juego(
    idJuego INT UNSIGNED,
    idEtiqueta INT UNSIGNED,
    FOREIGN KEY (idJuego) REFERENCES Juego(idJuego) ON UPDATE CASCADE,
    FOREIGN KEY (idEtiqueta) REFERENCES Etiqueta(idEtiqueta) ON UPDATE CASCADE
);

INSERT INTO Cliente VALUES
(1, "sandra@gmail.com", "Sandra Diaz Bear"),
(2, "pedro@hotmail.com", "Pedro García López"),
(3, "juan@outlook.es", "Juan Gómez Alario"),
(4, "maria@live.com", "María Molinero Sánchez"),
(5, "ana@icloud.com", "Ana Martín López"),
(6, "angel@educa.jcyl.es", "Ángel Hernández Garrido");

INSERT INTO Empleado VALUES
(1, "rebeca@gmail.com", SHA2("Rebeca",256), "Rebeca Martínez de León"),
(2, "mateo@hotmail.com", SHA2("Mateo",256), "Mateo Pérez García"),
(3, "valeria@outlook.es", SHA2("Valeria",256), "Valeria Gutiérrez Escribano");

INSERT INTO Juego VALUES
(1, "Leyendas Pokémon: Z-A", 47.99, 23.99, "PEGI 7"),
(2, "Super Mario: Odyssey", 35.99, 17.99, "PEGI 7"),
(3, "Xenoblade Chronicles 2", 32.99, 16.49, "PEGI 12"),
(4, "The Legend of Zelda: Breath of the Wild", 59.99, 29.99, "PEGI 12"),
(5, "The Legend of Zelda: Tears of the Kingdom", 69.99, 34.99, "PEGI 12"),
(6, "Pokémon Diamante Brillante", 24.99, 12.49, "PEGI 7"),
(7, "Minecraft: Bedrock Edition", 19.99, 9.99, "PEGI 3"),
(8, "Astral Chain", 19.99, 9.99, "PEGI 16"),
(9, "Final Fantasy VII Remake", 49.99, 24.99, "PEGI 16"),
(10, "Kingdom Hearts III", 35.99, 17.99, "PEGI 12"),
(11, "Mario Kart World", 89.99, 44.99, "PEGI 7");

INSERT INTO Transaccion VALUES
(1, 1, 1, 95.98, "Venta"),
(2, 1, 1, 54.46, "Compra"),
(3, 2, 1, 79.98, "Compra"),
(4, 3, 3, 149.97, "Venta"),
(5, 3, 2, 79.98, "Venta"),
(6, 3, 1, 59.98, "Compra"),
(7, 4, 2, 69.99, "Venta"),
(8, 5, 2, 105.46, "Compra"),
(9, 5, 3, 99.96, "Venta");

INSERT INTO Juego_Transaccion VALUES
(1, 1, 1, 2),
(2, 2, 2, 1),
(3, 2, 3, 1),
(4, 2, 7, 2),
(5, 3, 11, 1),
(6, 3, 5, 1),
(7, 4, 9, 3),
(8, 5, 4, 1),
(9, 5, 7, 1),
(10, 6, 4, 2),
(11, 7, 5, 1),
(12, 8, 4, 1),
(13, 8, 6, 1),
(14, 8, 10, 1),
(15, 8, 11, 1),
(16, 9, 5, 1),
(17, 9, 8, 3);

INSERT INTO Etiqueta VALUES
(1, "Terror"),
(2, "Rpg"),
(3, "Jrpg"),
(4, "Acción"),
(5, "Aventura"),
(6, "Carreras"),
(7, "Estrategia"),
(8, "Multijugador"),
(9, "Sandbox"),
(10, "Mundo abierto");

INSERT INTO Etiqueta_Juego VALUES
(1, 3),
(1, 5),
(1, 7),
(1, 10),
(2, 4),
(2, 5),
(2, 10),
(3, 3),
(3, 4),
(3, 5),
(3, 10),
(4, 3),
(4, 4),
(4, 5),
(4, 10),
(5, 3),
(5, 4),
(5, 5),
(5, 10),
(6, 3),
(6, 5),
(7, 5),
(7, 8),
(7, 9),
(7, 10),
(8, 3),
(8, 4),
(8, 8),
(9, 3),
(9, 4),
(9, 5),
(10, 3),
(10, 5),
(11, 6);