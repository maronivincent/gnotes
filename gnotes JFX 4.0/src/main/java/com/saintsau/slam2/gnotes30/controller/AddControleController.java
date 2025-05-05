package com.saintsau.slam2.gnotes30.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saintsau.slam2.gnotes30.entity.Etudiant;
import com.saintsau.slam2.gnotes30.entity.Matiere;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddControleController {

    private static final String API_BASE_URL = "http://localhost:8080";

    @FXML
    private ComboBox<Matiere> intituleCombo;

    @FXML
    private ComboBox<Etudiant> studentCombo;

    @FXML
    private TextField typeField;

    @FXML
    private TextField coefField;

    @FXML
    private TextField noteField;

    @FXML
    public void initialize() {
        loadMatieres();
        loadEtudiants();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadMatieres() {
        try {
            URL url = new URL(API_BASE_URL + "/matieres");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            String jsonStr = new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonStr);
            JsonNode matieresNode = root.path("_embedded").path("matiereList");

            List<Matiere> matieres = mapper.readValue(
                    matieresNode.toString(),
                    new TypeReference<List<Matiere>>() {
                    });

            intituleCombo.setItems(FXCollections.observableArrayList(matieres));

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger les matières.");
        }
    }

    private void loadEtudiants() {
        try {
            URL url = new URL(API_BASE_URL + "/etudiants");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            String jsonStr = new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonStr);
            JsonNode etudiantsNode = root.path("_embedded").path("etudiantList");

            List<Etudiant> etudiants = mapper.readValue(
                    etudiantsNode.toString(),
                    new TypeReference<List<Etudiant>>() {
                    });

            studentCombo.setItems(FXCollections.observableArrayList(etudiants));

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger les étudiants.");
        }
    }

    @FXML
    private void saveControle() {
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

        // Prepare the JSON for debugging
        Map<String, Object> controleData = new HashMap<>();
        controleData.put("type", type);
        controleData.put("coefficient", coefficient);
        controleData.put("note", note);
        controleData.put("matiere", Map.of("id", selectedMatiere.getId()));
        controleData.put("etudiant", Map.of("numero", selectedEtudiant.getNumero()));

        // Print the JSON for debugging
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(controleData);
            System.out.println("POST JSON: " + json);  // Print the JSON to the console for debugging
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de convertir les données en JSON.");
        }

        // Send the POST request
        try {
            URL url = new URL(API_BASE_URL + "/controles");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Send the JSON data
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(controleData);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 201 || responseCode == 200) {
                showAlert("Succès", "Contrôle ajouté avec succès !");
                typeField.clear();
                coefField.clear();
                noteField.clear();
                intituleCombo.getSelectionModel().clearSelection();
                studentCombo.getSelectionModel().clearSelection();
            } else {
                showAlert("Erreur", "Erreur lors de l'envoi : code " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'enregistrer le contrôle.");
        }
    }
}
