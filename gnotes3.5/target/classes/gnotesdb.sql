Drop table matieres;
drop table etudiants;

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

