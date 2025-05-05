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

public class editStudentController {

    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private Button saveButton;

    private Etudiant currentStudent;

    public void initData(Etudiant student) {
        this.currentStudent = student;
        nameField.setText(student.getNom());
        surnameField.setText(student.getPrenom());
    }

    @FXML
    private void saveChanges() {
        String name = nameField.getText();
        String surname = surnameField.getText();

        if (name.isEmpty() || surname.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        currentStudent.setNom(name);
        currentStudent.setPrenom(surname);

        // Send the update request asynchronously
        sendUpdateRequest(currentStudent);

        // Close window
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private void sendUpdateRequest(Etudiant student) {
        try {
            // Construct JSON body
            ObjectMapper mapper = new ObjectMapper();
            String jsonBody = mapper.writeValueAsString(student);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/etudiants/" + student.getNumero())) // Assuming you use an ID for URL
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

            // Send the request asynchronously
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    int statusCode = response.statusCode();
                    String responseBody = response.body();

                    if (statusCode == 200) { // 200 OK - Updated successfully
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

    private void showAlert(String title, String message) {
        javafx.application.Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
