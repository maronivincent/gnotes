package com.saintsau.slam2.gnotes30.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saintsau.slam2.gnotes30.entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Controller pour la vue de modification des informations d'un professeur.
 * Cette classe permet de modifier le nom d'utilisateur et le mot de passe d'un professeur.
 * Une fois les modifications effectuées, elles sont envoyées au serveur pour mise à jour via une requête PUT.
 */
public class editProfessorController {

    @FXML private TextField nameField;      // Champ de texte pour le nom d'utilisateur
    @FXML private TextField passwordField;  // Champ de texte pour le mot de passe
    @FXML private Button saveButton;        // Bouton pour enregistrer les modifications

    private User currentProfessor;  // Objet représentant le professeur à modifier

    /**
     * Initialise les données du professeur à modifier.
     * Cette méthode est appelée pour pré-remplir les champs du formulaire avec les données actuelles du professeur.
     *
     * @param professor L'objet User représentant le professeur à modifier.
     */
    public void initData(User professor) {
        this.currentProfessor = professor;
        nameField.setText(professor.getUsername());  // Remplir le champ de nom d'utilisateur
        passwordField.setText(professor.getPassword()); // Remplir le champ de mot de passe
    }

    /**
     * Enregistre les modifications apportées au professeur.
     * Cette méthode met à jour les informations du professeur et envoie les modifications au serveur via une requête PUT.
     */
    @FXML
    private void saveChanges() {
        currentProfessor.setUsername(nameField.getText());  // Mettre à jour le nom d'utilisateur
        currentProfessor.setPassword(passwordField.getText()); // Mettre à jour le mot de passe

        // Envoyer les modifications au serveur
        sendUpdateRequest(currentProfessor);

        // Fermer la fenêtre après la mise à jour
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Envoie une requête PUT au serveur pour mettre à jour les informations du professeur.
     *
     * @param professor L'objet User contenant les informations mises à jour du professeur.
     */
    private void sendUpdateRequest(User professor) {
        try {
            // Créer l'URL pour l'API de mise à jour du professeur
            URL url = new URL("http://localhost:8080/users/" + professor.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Convertir l'objet professeur en JSON
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(professor);

            // Envoyer les données JSON dans le corps de la requête
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }

            // Afficher le code de réponse de la requête HTTP
            System.out.println("Updated professor: " + conn.getResponseCode());

        } catch (Exception e) {
            // Gérer les exceptions et afficher la trace de l'erreur
            e.printStackTrace();
        }
    }
}
