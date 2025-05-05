package com.saintsau.slam2.gnotes30.controller;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller pour la vue d'ajout d'un professeur.
 * Cette classe gère la soumission du formulaire pour ajouter un professeur dans le système.
 * Elle prend en charge la collecte des informations du professeur (nom, mot de passe),
 * et les envoie à l'API via une requête HTTP POST.
 */
public class AddProfController {

    @FXML
    private TextField nameField;  // Champ pour entrer le nom d'utilisateur (nom du professeur)

    @FXML
    private TextField passwordField;  // Champ pour entrer le mot de passe du professeur

    /**
     * Méthode appelée lors du clic sur le bouton pour ajouter un professeur.
     * Cette méthode valide les champs de saisie et envoie une requête POST avec les données du professeur
     * pour l'enregistrer dans le système.
     */
    @FXML
    private void saveProfessor() {
        // Récupérer les valeurs des champs de texte
        String name = nameField.getText();
        String password = passwordField.getText();

        // Vérifier que les champs ne sont pas vides
        if (name.isEmpty() || password.isEmpty()) {
            System.out.println("Tous les champs doivent être remplis !");
            return;
        }

        try {
            // Créer la connexion HTTP vers l'API pour ajouter un professeur
            URL url = new URL("http://localhost:8080/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Créer la charge utile JSON avec le nom d'utilisateur et le mot de passe
            Map<String, String> jsonMap = new HashMap<>();
            jsonMap.put("username", name);
            jsonMap.put("password", password);

            // Convertir la charge utile en JSON
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(jsonMap);

            // Envoyer les données JSON dans la requête POST
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Vérifier la réponse du serveur
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Professeur ajouté avec succès !");
                // Réinitialiser les champs de texte
                nameField.clear();
                passwordField.clear();
            } else {
                System.out.println("Erreur lors de l'ajout du professeur : Code " + responseCode);
            }

        } catch (Exception e) {
            // Gérer les exceptions en cas d'erreur de connexion ou d'échec de la requête
            System.err.println("Erreur lors de la connexion à l'API.");
            e.printStackTrace();
        }
    }
}
