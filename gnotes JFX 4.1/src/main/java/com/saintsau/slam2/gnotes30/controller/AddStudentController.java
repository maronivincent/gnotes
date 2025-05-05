package com.saintsau.slam2.gnotes30.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Controller pour la vue d'ajout d'un étudiant.
 * Cette classe gère la soumission du formulaire pour ajouter un étudiant dans le système.
 * Elle prend en charge la collecte des informations de l'étudiant (nom, prénom),
 * et les envoie à l'API via une requête HTTP POST.
 */
public class AddStudentController {

    @FXML
    private TextField nameField;  // Champ pour entrer le nom de l'étudiant

    @FXML
    private TextField surnameField;  // Champ pour entrer le prénom de l'étudiant

    /**
     * Méthode appelée lors du clic sur le bouton pour ajouter un étudiant.
     * Cette méthode valide les champs de saisie et envoie une requête POST avec les données de l'étudiant
     * pour l'enregistrer dans le système.
     */
    @FXML
    private void saveStudent() {
        // Récupérer les valeurs des champs de texte
        String name = nameField.getText();
        String surname = surnameField.getText();

        // Vérifier que les champs ne sont pas vides
        if (name.isEmpty() || surname.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        try {
            // Créer le corps JSON à envoyer dans la requête POST
            String jsonBody = String.format("{\"nom\": \"%s\", \"prenom\": \"%s\", \"controles\": []}", name, surname);

            // Créer un client HTTP pour envoyer la requête
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/etudiants")) // URL de l'API pour ajouter un étudiant
                .header("Content-Type", "application/json") // Type de contenu JSON
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)) // Corps de la requête
                .build();

            // Envoyer la requête de manière asynchrone
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenAccept(response -> {
                int statusCode = response.statusCode();  // Code de statut de la réponse
                String responseBody = response.body();  // Corps de la réponse

                // Vérifier si l'étudiant a été ajouté avec succès (code 201)
                if (statusCode == 201) {
                    System.out.println("Étudiant ajouté avec succès !");
                    clearFields();  // Réinitialiser les champs du formulaire
                    showAlert("Succès", "L'étudiant a été ajouté avec succès.");
                } else {
                    System.err.println("Erreur d'enregistrement : " + responseBody);
                    showAlert("Erreur", "Impossible d'enregistrer l'étudiant. Code erreur: " + statusCode);
                }
            })
            .exceptionally(ex -> {
                ex.printStackTrace();  // Afficher la trace de l'exception
                showAlert("Erreur", "Une erreur s'est produite lors de la communication avec le serveur.");
                return null;
            });

        } catch (Exception e) {
            // Gérer les exceptions éventuelles et afficher un message d'erreur
            e.printStackTrace();
            showAlert("Erreur", "Une erreur s'est produite lors de la communication avec le serveur.");
        }
    }

    /**
     * Réinitialise les champs de saisie du formulaire.
     */
    private void clearFields() {
        nameField.clear();
        surnameField.clear();
    }

    /**
     * Affiche une alerte avec un titre et un message.
     * 
     * @param title Le titre de l'alerte.
     * @param message Le message à afficher dans l'alerte.
     */
    private void showAlert(String title, String message) {
        javafx.application.Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
