use gnotesdb;
Drop table matieres;
Drop table etudiants;
Drop table users;
Drop table authorities;

CREATE TABLE etudiants (
    numero INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE matieres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    intitule VARCHAR(100) NOT NULL,
    coefficient INT NOT NULL,
    type VARCHAR(100),  -- For example: "Oral", "DS", "TP"
    note FLOAT NOT NULL,
    etudiant_numero INT,  -- Assuming this is the foreign key
    FOREIGN KEY (etudiant_numero) REFERENCES etudiants(numero)  -- Foreign key constraint
);

CREATE TABLE users (
 username varchar(50) NOT NULL,
 password varchar(500) NOT NULL,
 enabled BOOLEAN NOT NULL,
 newPassword varchar(500),
 change_password int DEFAULT 0,
 failed_attempt int DEFAULT 0,
 lock_time timestamp null,
 PRIMARY KEY (username)
 )ENGINE=InnoDB;

CREATE TABLE authorities (
 username varchar(50) NOT NULL,
 authority varchar(50) NOT NULL,
 PRIMARY KEY (username),
CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
 )ENGINE=InnoDB;

CREATE TABLE groupes (
 id BIGINT(255),
 group_name varchar(50) not null,
 PRIMARY KEY (id)
 )ENGINE=InnoDB;

CREATE TABLE group_authorities (
 group_id BIGINT(255) NOT NULL,
 authority varchar(50) NOT NULL,
 CONSTRAINT fk_group_authorities_group FOREIGN KEY(group_id) REFERENCES groupes(id)
 )ENGINE=InnoDB;

CREATE TABLE group_members (
 id BIGINT(255) AUTO_INCREMENT,
 username varchar(50) NOT NULL,
 group_id BIGINT(255) NOT NULL,
 CONSTRAINT fk_group_members_group FOREIGN KEY(group_id) REFERENCES groupes(id),
 PRIMARY KEY (id)
 )ENGINE=InnoDB;
 
 CREATE table logs (
 
 id BIGINT(255),
 date_log timestamp,
 add_ip varchar (16),
 user_action varchar(20),
 username varchar(50),
  CONSTRAINT fk_username FOREIGN KEY(username) REFERENCES users(username),
 PRIMARY KEY (id)
 )ENGINE=InnoDB;

INSERT INTO etudiants (nom) VALUES
('Dimitri'),
('Jean'),
('Lea');


INSERT INTO matieres (intitule, coefficient, type, note, etudiant_numero) VALUES
('Maths', 3, 'DS', 12.5, 1),
('Physique', 4, 'DS', 8.5 , 1),
('Français', 2, 'Oral', 16.0, 1),
('Histoire', 2, 'DS', 12.0, 1),
('SVT', 3, 'TP', 10.0, 1),
('Maths', 3, 'DS', 8.5, 2),
('Physique', 4, 'DS', 14.0, 2),
('Français', 2, 'Oral', 12.0, 2),
('Histoire', 2, 'DS', 16.0, 2),
('SVT', 3, 'TP', 12.0, 2),
('Maths', 3, 'DS', 14.0, 3),
('Physique', 4, 'DS', 12.0, 3),
('Français', 2, 'Oral', 12.0, 3),
('Histoire', 2, 'DS', 9.0, 3),
('SVT', 3, 'TP', 16.0, 3);

INSERT INTO users (username, password, enabled, newPassword, change_password, failed_attempt, lock_time) 
VALUES 
('admin', '{noop}admin.', TRUE, NULL, 0, 0, NULL),
('enseignant', '{noop}prof.', TRUE, NULL, 0, 0, NULL);

-- Assign roles to users
INSERT INTO authorities (username, authority) 
VALUES 
('admin', 'ROLE_ADMIN'),
('enseignant', 'ROLE_ENSEIGNANT');
