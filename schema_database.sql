CREATE DATABASE IF NOT EXISTS rubrica;
USE rubrica;
DROP TABLE IF EXISTS Contatti;
DROP TABLE IF EXISTS Utenti;

CREATE TABLE Utenti (
	username VARCHAR(50) PRIMARY KEY,
	password VARCHAR(255) NOT NULL
);

CREATE TABLE Contatti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    owner_username VARCHAR(50),
    nome VARCHAR(100),
    cognome VARCHAR(100),
    indirizzo VARCHAR(255),
    telefono VARCHAR(20),
    eta INT,
    FOREIGN KEY (owner_username) REFERENCES Utenti(username) ON DELETE CASCADE
);

INSERT INTO Utenti (username, password) VALUES ('admin', 'adminpass');
INSERT INTO Utenti (username, password) VALUES ('user1', 'user1pass');
INSERT INTO Utenti (username, password) VALUES ('user2', 'user2pass');

INSERT INTO Contatti (owner_username, nome, cognome, indirizzo, telefono, eta) VALUES ('admin', 'Mario', 'Rossi', 'Via Roma 1, Milano', '1234567890', 30);
INSERT INTO Contatti (owner_username, nome, cognome, indirizzo, telefono, eta) VALUES ('user1', 'Luca', 'Bianchi', 'Via Milano 2, Roma', '0987654321', 25);
INSERT INTO Contatti (owner_username, nome, cognome, indirizzo, telefono, eta) VALUES ('user2', 'Anna', 'Verdi', 'Via Napoli 3, Torino', '1122334455', 28);
INSERT INTO Contatti (owner_username, nome, cognome, indirizzo, telefono, eta) VALUES ('admin', 'Giulia', 'Neri', 'Via Firenze 4, Napoli', '5566778899', 32);
INSERT INTO Contatti (owner_username, nome, cognome, indirizzo, telefono, eta) VALUES ('user1', 'Marco', 'Gialli', 'Via Venezia 5, Bologna', '6677889900', 27);
INSERT INTO Contatti (owner_username, nome, cognome, indirizzo, telefono, eta) VALUES ('user2', 'Sara', 'Blu', 'Via Genova 6, Palermo', '7788990011', 29);

