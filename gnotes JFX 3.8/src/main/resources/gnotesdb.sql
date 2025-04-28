-- Utilisation de la base de données
USE gnotesdb;

-- Suppression des tables existantes
DROP TABLE IF EXISTS controles;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS matieres;
DROP TABLE IF EXISTS etudiants;

-- Table 'etudiants'
CREATE TABLE etudiants (
    numero INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL
);

-- Table 'matieres'
CREATE TABLE matieres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    intitule VARCHAR(100) NOT NULL
);

-- Table 'users' (Professeurs et Admins)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(500) NOT NULL,
    enabled BOOLEAN NOT NULL
);

-- Table 'authorities' pour gérer les rôles des utilisateurs
CREATE TABLE authorities (
    user_id INT NOT NULL,
    authority VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, authority),
    CONSTRAINT fk_authorities_users FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Table 'controles' avec contraintes renforcées
CREATE TABLE controles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    etudiant_id INT NOT NULL,
    professeur_id INT NOT NULL, -- Utilisation de user_id pour référencer le professeur
    matiere_id INT NOT NULL,
    coefficient INT NOT NULL CHECK (coefficient > 0), -- Coefficient doit être positif
    type VARCHAR(50) NOT NULL, -- Correction ici, 'String' devient 'VARCHAR(50)'
    note FLOAT NOT NULL CHECK (note BETWEEN 0 AND 20), -- Note limitée entre 0 et 20 
    date_controle DATE NOT NULL,
    FOREIGN KEY (etudiant_id) REFERENCES etudiants(numero) ON DELETE CASCADE,
    FOREIGN KEY (professeur_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (matiere_id) REFERENCES matieres(id) ON DELETE CASCADE
);

-- Insertion des étudiants
INSERT INTO etudiants (prenom, nom) VALUES
('Dimitri', 'Dupont'),
('Jean', 'Martin'),
('Lea', 'Lemoine');

-- Insertion des matières
INSERT INTO matieres (intitule) VALUES
('Mathématiques'),
('Physique'),
('Français'),
('Histoire'),
('SVT');

-- Insertion des utilisateurs (professeurs et administrateurs)
INSERT INTO users (username, password, enabled) VALUES
('admin', 'admin', TRUE), -- Admin
('enseignant1', '{noop}prof1.', TRUE), -- Professeur 1
('enseignant2', '{noop}prof2.', TRUE); -- Professeur 2

-- Assignation des rôles aux utilisateurs
INSERT INTO authorities (user_id, authority) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_ENSEIGNANT'),
(3, 'ROLE_ENSEIGNANT');

-- Insertion des contrôles avec les professeurs identifiés par user_id
INSERT INTO controles (etudiant_id, professeur_id, matiere_id, coefficient, type, note, date_controle) VALUES
(1, 2, 1, 3, 'DS', 12.5, '2025-02-25'),
(1, 2, 2, 4, 'DS', 8.5, '2025-03-01'),
(2, 3, 3, 2, 'Oral', 16.0, '2025-03-05'),
(3, 3, 4, 2, 'DS', 12.0, '2025-03-10');