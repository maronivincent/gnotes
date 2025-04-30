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

public class AddProfController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private void saveProfessor() {
    	 String name = nameField.getText();
    	    String password = passwordField.getText();

    	    if (name.isEmpty() || password.isEmpty()) {
    	        System.out.println("Tous les champs doivent être remplis !");
    	        return;
    	    }

    	    try {
    	        URL url = new URL("http://localhost:8080/users");
    	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    	        conn.setRequestMethod("POST");
    	        conn.setRequestProperty("Content-Type", "application/json; utf-8");
    	        conn.setDoOutput(true);

    	        // Create JSON payload
    	        Map<String, String> jsonMap = new HashMap<>();
    	        jsonMap.put("username", name);
    	        jsonMap.put("password", password);

    	        ObjectMapper mapper = new ObjectMapper();
    	        String json = mapper.writeValueAsString(jsonMap);

    	        try (OutputStream os = conn.getOutputStream()) {
    	            byte[] input = json.getBytes(StandardCharsets.UTF_8);
    	            os.write(input, 0, input.length);
    	        }

    	        int responseCode = conn.getResponseCode();
    	        if (responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_OK) {
    	            System.out.println("Professeur ajouté avec succès !");
    	            nameField.clear();
    	            passwordField.clear();
    	        } else {
    	            System.out.println("Erreur lors de l'ajout du professeur : Code " + responseCode);
    	        }

    	    } catch (Exception e) {
    	        System.err.println("Erreur lors de la connexion à l'API.");
    	        e.printStackTrace();
    	    }
    	}
}
