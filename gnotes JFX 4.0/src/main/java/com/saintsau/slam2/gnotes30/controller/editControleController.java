package com.saintsau.slam2.gnotes30.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saintsau.slam2.gnotes30.entity.ControleDTO;
import com.saintsau.slam2.gnotes30.entity.Etudiant;
import com.saintsau.slam2.gnotes30.entity.Matiere;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class editControleController {

    private static final String API_BASE_URL = "http://localhost:8080";

    @FXML private ComboBox<Matiere> intituleCombo;
    @FXML private ComboBox<Etudiant> studentCombo;
    @FXML private TextField typeField;
    @FXML private TextField coefField;
    @FXML private TextField noteField;
    @FXML private Button saveButton;

    private ControleDTO currentControle;

    public void initData(ControleDTO controle) {
        this.currentControle = controle;
        if (controle == null || controle.getId() == null) {
            System.out.println("Error: Controle ID is missing");
            showAlert("Erreur", "Le contrôle n'a pas d'ID. Impossible de modifier.");
            return;
        }

        System.out.println("Initializing Controle with ID: " + controle.getId());

        loadMatieres();
        loadEtudiants();

        typeField.setText(controle.getType());
        coefField.setText(String.valueOf(controle.getCoefficient()));
        noteField.setText(String.valueOf(controle.getNote()));

        Platform.runLater(() -> {
            intituleCombo.getItems().stream()
                .filter(m -> m.getIntitule().equals(controle.getIntitule()))
                .findFirst()
                .ifPresent(intituleCombo::setValue);

            studentCombo.getItems().stream()
                .filter(e -> e.getNom().equals(controle.getNomEtudiant()))
                .findFirst()
                .ifPresent(studentCombo::setValue);
        });
    }
    @FXML
    private void saveChanges() {
        System.out.println("Saving changes...");

        Matiere selectedMatiere = intituleCombo.getValue();
        Etudiant selectedEtudiant = studentCombo.getValue();
        String type = typeField.getText().trim();
        String coefStr = coefField.getText().trim();
        String noteStr = noteField.getText().trim();

        if (selectedMatiere == null || selectedEtudiant == null || type.isEmpty() || coefStr.isEmpty() || noteStr.isEmpty()) {
            showAlert("Champs manquants", "Tous les champs doivent être remplis !");
            return;
        }

        double coefficient;
        double note;
        try {
            coefficient = Double.parseDouble(coefStr);
            note = Double.parseDouble(noteStr);
        } catch (NumberFormatException e) {
            showAlert("Format invalide", "Veuillez entrer des nombres valides pour le coefficient et la note.");
            return;
        }

        Map<String, Object> controleData = new HashMap<>();
        controleData.put("type", type);
        controleData.put("coefficient", coefficient);
        controleData.put("note", note);
        controleData.put("matiere", Map.of("id", selectedMatiere.getId()));
        controleData.put("etudiant", Map.of("numero", selectedEtudiant.getNumero()));

        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(controleData);
            System.out.println("PUT JSON: " + json);

            if (currentControle.getId() == null) {
                showAlert("Erreur", "L'ID du contrôle est manquant.");
                return;
            }
            System.out.println("Controle ID to update: " + currentControle.getId());

            URL url = new URL(API_BASE_URL + "/controles/" + currentControle.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
                System.out.println("Sending data to: " + url);
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            if (responseCode == 200 || responseCode == 204) {
                showAlert("Succès", "Contrôle modifié avec succès !");
                closeWindow();
            } else {
                showAlert("Erreur", "Erreur lors de la mise à jour : code " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d’envoyer la mise à jour.");
        }
    }

    private void loadMatieres() {
        System.out.println("Loading Matieres...");

        try {
            URL url = new URL(API_BASE_URL + "/matieres");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            String jsonStr = new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Received Matieres JSON: " + jsonStr);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonStr);
            JsonNode matieresNode = root.path("_embedded").path("matiereList");

            List<Matiere> matieres = mapper.readValue(matieresNode.toString(), new TypeReference<>() {});
            System.out.println("Loaded Matieres: " + matieres);
            intituleCombo.setItems(FXCollections.observableArrayList(matieres));

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger les matières.");
        }
    }

    private void loadEtudiants() {
        System.out.println("Loading Etudiants...");

        try {
            URL url = new URL(API_BASE_URL + "/etudiants");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            String jsonStr = new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Received Etudiants JSON: " + jsonStr);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonStr);
            JsonNode etudiantsNode = root.path("_embedded").path("etudiantList");

            List<Etudiant> etudiants = mapper.readValue(etudiantsNode.toString(), new TypeReference<>() {});
            System.out.println("Loaded Etudiants: " + etudiants);
            studentCombo.setItems(FXCollections.observableArrayList(etudiants));

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger les étudiants.");
        }
    }

    private void closeWindow() {
        System.out.println("Closing window...");
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        System.out.println("Showing Alert: " + title + " - " + message);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
