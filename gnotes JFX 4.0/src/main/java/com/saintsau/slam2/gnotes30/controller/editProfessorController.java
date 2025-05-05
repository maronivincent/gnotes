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

public class editProfessorController {

    @FXML private TextField nameField;
    @FXML private TextField passwordField; // Add password field
    @FXML private Button saveButton;

    private User currentProfessor;

    public void initData(User professor) {
        this.currentProfessor = professor;
        nameField.setText(professor.getUsername());
        passwordField.setText(professor.getPassword()); // Set password field
    }

    @FXML
    private void saveChanges() {
        currentProfessor.setUsername(nameField.getText());
        currentProfessor.setPassword(passwordField.getText()); // Update password

        // Optional: send update to backend
        sendUpdateRequest(currentProfessor);

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private void sendUpdateRequest(User professor) {
        try {
            URL url = new URL("http://localhost:8080/users/" + professor.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(professor);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }

            System.out.println("Updated professor: " + conn.getResponseCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
