DROP DATABASE IF EXISTS CompraventaVideojuegos;
CREATE DATABASE CompraventaVideojuegos;
USE CompraventaVideojuegos;

CREATE TABLE Cliente(
    idCliente INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    correo VARCHAR(50),
    contrasena VARCHAR(256),
    nombre VARCHAR(100)
);

CREATE TABLE Empleado(
    idEmpleado INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    correo VARCHAR(50),
    contrasena VARCHAR(256),
    nombre VARCHAR(100)
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

CREATE TABLE Juego(
    idJuego INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100),
    precio DECIMAL(10, 2),
    pegi VARCHAR(7)
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