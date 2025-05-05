package com.saintsau.slam2.gnotes30.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saintsau.slam2.gnotes30.entity.Etudiant;

/**
 * Controller pour la vue de modification des informations d'un étudiant.
 * Cette classe permet de modifier le nom et le prénom d'un étudiant.
 * Une fois les modifications effectuées, elles sont envoyées au serveur via une requête HTTP PUT.
 */
public class editStudentController {

    @FXML private TextField nameField;    // Champ de texte pour le nom de l'étudiant
    @FXML private TextField surnameField; // Champ de texte pour le prénom de l'étudiant
    @FXML private Button saveButton;      // Bouton pour enregistrer les modifications

    private Etudiant currentStudent;      // Objet représentant l'étudiant à modifier

    /**
     * Initialise les données de l'étudiant à modifier.
     * Cette méthode est appelée pour pré-remplir les champs du formulaire avec les données actuelles de l'étudiant.
     *
     * @param student L'objet Etudiant représentant l'étudiant à modifier.
     */
    public void initData(Etudiant student) {
        this.currentStudent = student;
        nameField.setText(student.getNom());      // Remplir le champ du nom
        surnameField.setText(student.getPrenom()); // Remplir le champ du prénom
    }

    /**
     * Enregistre les modifications apportées à l'étudiant.
     * Cette méthode met à jour les informations de l'étudiant et envoie les modifications au serveur via une requête PUT.
     * Si les champs sont vides, un message d'erreur est affiché.
     */
    @FXML
    private void saveChanges() {
        String name = nameField.getText();
        String surname = surnameField.getText();

        if (name.isEmpty() || surname.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        currentStudent.setNom(name);    // Mettre à jour le nom de l'étudiant
        currentStudent.setPrenom(surname); // Mettre à jour le prénom de l'étudiant

        // Envoi de la requête de mise à jour de manière asynchrone
        sendUpdateRequest(currentStudent);

        // Fermeture de la fenêtre après la mise à jour
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Envoie une requête PUT au serveur pour mettre à jour les informations de l'étudiant.
     * Cette méthode utilise une requête HTTP asynchrone pour envoyer les données mises à jour au serveur.
     *
     * @param student L'objet Etudiant contenant les informations mises à jour.
     */
    private void sendUpdateRequest(Etudiant student) {
        try {
            // Construire le corps JSON de la requête
            ObjectMapper mapper = new ObjectMapper();
            String jsonBody = mapper.writeValueAsString(student);

            // Créer et configurer la requête HTTP PUT
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/etudiants/" + student.getNumero())) // URL de l'étudiant à mettre à jour
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

            // Envoi de la requête de manière asynchrone
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    int statusCode = response.statusCode();
                    String responseBody = response.body();

                    if (statusCode == 200) { // 200 OK - Mise à jour réussie
                        System.out.println("Étudiant mis à jour avec succès !");
                        showAlert("Succès", "L'étudiant a été mis à jour avec succès.");
                    } else {
                        System.err.println("Erreur de mise à jour : " + responseBody);
                        showAlert("Erreur", "Impossible de mettre à jour l'étudiant. Code erreur: " + statusCode);
                    }
                })
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    showAlert("Erreur", "Une erreur s'est produite lors de la communication avec le serveur.");
                    return null;
                });

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur s'est produite lors de la communication avec le serveur.");
        }
    }

    /**
     * Affiche une alerte avec un titre et un message spécifiés.
     * Cette méthode est utilisée pour informer l'utilisateur en cas d'erreur ou de succès.
     *
     * @param title   Le titre de l'alerte.
     * @param message Le message de l'alerte.
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
