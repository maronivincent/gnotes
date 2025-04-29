package com.saintsau.slam2.gnotes30.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AddStudentController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private void saveStudent() {
        String name = nameField.getText();
        String surname = surnameField.getText();

        if (name.isEmpty() || surname.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        try {
            // Construct the JSON body
        	 String jsonBody = String.format("{\"nom\": \"%s\", \"prenom\": \"%s\", \"controles\": []}", name, surname);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/etudiants")) // The API URL
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

            // Send the request asynchronously
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenAccept(response -> {
                int statusCode = response.statusCode();
                String responseBody = response.body();
                
                if (statusCode == 201) {  // 201 Created
                    System.out.println("Étudiant ajouté avec succès !");
                    clearFields();
                    showAlert("Succès", "L'étudiant a été ajouté avec succès.");
                } else {
                    System.err.println("Erreur d'enregistrement : " + responseBody);
                    showAlert("Erreur", "Impossible d'enregistrer l'étudiant. Code erreur: " + statusCode);
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

    private void clearFields() {
        nameField.clear();
        surnameField.clear();
    }

    private void showAlert(String title, String message) {
        javafx.application.Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
