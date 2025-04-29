package com.saintsau.slam2.gnotes30.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saintsau.slam2.gnotes30.entity.Etudiant;
import com.saintsau.slam2.gnotes30.entity.Matiere;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AddControleController {

    private final String API_BASE_URL = "http://localhost:8080";

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

    private void loadMatieres() {
        try {
            URL url = new URL(API_BASE_URL + "/matieres");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            StringBuilder jsonStr = new StringBuilder();
            try (Scanner scanner = new Scanner(conn.getInputStream())) {
                while (scanner.hasNext()) {
                    jsonStr.append(scanner.nextLine());
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            List<Matiere> matieres = mapper.readValue(jsonStr.toString(), new TypeReference<List<Matiere>>() {});
            intituleCombo.setItems(FXCollections.observableArrayList(matieres));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEtudiants() {
        try {
            URL url = new URL(API_BASE_URL + "/etudiants");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            StringBuilder jsonStr = new StringBuilder();
            try (Scanner scanner = new Scanner(conn.getInputStream())) {
                while (scanner.hasNext()) {
                    jsonStr.append(scanner.nextLine());
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            List<Etudiant> etudiants = mapper.readValue(jsonStr.toString(), new TypeReference<List<Etudiant>>() {});
            studentCombo.setItems(FXCollections.observableArrayList(etudiants));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveControle() {
        Matiere selectedMatiere = intituleCombo.getValue();
        Etudiant selectedEtudiant = studentCombo.getValue();
        String type = typeField.getText();
        String coef = coefField.getText();
        String note = noteField.getText();

        if (selectedMatiere == null || selectedEtudiant == null || type.isEmpty() || coef.isEmpty() || note.isEmpty()) {
            System.out.println("Tous les champs doivent être remplis !");
            return;
        }

        try {
            URL url = new URL(API_BASE_URL + "/controles");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            Map<String, Object> controleData = new HashMap<>();
            controleData.put("type", type);
            controleData.put("coefficient", Double.parseDouble(coef));
            controleData.put("note", Double.parseDouble(note));
            controleData.put("matiere", selectedMatiere); // assuming it's a nested object
            controleData.put("etudiant", selectedEtudiant); // assuming it's a nested object

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(controleData);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 201 || responseCode == 200) {
                System.out.println("Contrôle ajouté avec succès !");
                typeField.clear();
                coefField.clear();
                noteField.clear();
                intituleCombo.getSelectionModel().clearSelection();
                studentCombo.getSelectionModel().clearSelection();
            } else {
                System.out.println("Erreur: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
