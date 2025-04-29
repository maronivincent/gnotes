package com.saintsau.slam2.gnotes30.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saintsau.slam2.gnotes30.entity.Etudiant;
import com.saintsau.slam2.gnotes30.entity.User;
import com.saintsau.slam2.gnotes30.entity.Controle;
import com.saintsau.slam2.gnotes30.entity.ControleDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class logoutPageController {
    
    @FXML
    private Button actiontarget;
    
    @FXML
    private TableView<Etudiant> studentTable;
    @FXML
    private TableColumn<Etudiant, String> columnNomStudent;
    @FXML
    private TableColumn<Etudiant, String> columnPrenomStudent;
    
    @FXML
    private TableView<User> professorTable;
    @FXML
    private TableColumn<User, String> columnNomProfessor;
    
    @FXML
    private TableView<ControleDTO> controleTable;
    @FXML
    private TableColumn<Controle, String> columnMatiereControle;
    @FXML
    private TableColumn<Controle, String> columnCoefControle;
    @FXML
    private TableColumn<Controle, String> columnTypeControle;
    @FXML
    private TableColumn<Controle, String> columnNoteControle;
    @FXML
    private TableColumn<Controle, String> columnNomEtudiantControle;
    @FXML
    private TableColumn<Controle, String> columnDateControle;
    
    private final String API_BASE_URL = "http://localhost:8080";
    
    @FXML
    public void initialize() {
        setupTables();
        loadStudentsFromAPI();
        loadProfsFromAPI();
        loadControlesFromAPI();
    }
    
    private void setupTables() {
        columnNomStudent.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnPrenomStudent.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        
        columnNomProfessor.setCellValueFactory(new PropertyValueFactory<>("username"));
        
        columnMatiereControle.setCellValueFactory(new PropertyValueFactory<>("intitule"));
        columnCoefControle.setCellValueFactory(new PropertyValueFactory<>("coefficient"));
        columnTypeControle.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnNoteControle.setCellValueFactory(new PropertyValueFactory<>("note"));
        columnNomEtudiantControle.setCellValueFactory(new PropertyValueFactory<>("nomEtudiant"));
        columnDateControle.setCellValueFactory(new PropertyValueFactory<>("dateControle"));

    }
    
    private void loadStudentsFromAPI() {
        fetchData(API_BASE_URL + "/etudiants", studentTable, Etudiant.class);
    }

    private void loadProfsFromAPI() {
        fetchData(API_BASE_URL + "/users", professorTable, User.class);
    }

    private void loadControlesFromAPI() {
        try {
            URL url = new URL(API_BASE_URL + "/controles/dto");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.out.println("HTTP Error: " + conn.getResponseCode());
                return;
            }

            StringBuilder jsonStr = new StringBuilder();
            try (Scanner scanner = new Scanner(conn.getInputStream())) {
                while (scanner.hasNext()) {
                    jsonStr.append(scanner.nextLine());
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            List<ControleDTO> controleList = mapper.readValue(
                jsonStr.toString(),
                mapper.getTypeFactory().constructCollectionType(List.class, ControleDTO.class)
            );

            controleTable.setItems(FXCollections.observableArrayList(controleList));
        } catch (Exception e) {
            System.err.println("Error loading /controles/dto:");
            e.printStackTrace();
        }
    }



    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ApiResponse<T> {
        @JsonProperty("_embedded")
        private Embedded<T> embedded;

        public Embedded<T> getEmbedded() {
            return embedded;
        }

        public void setEmbedded(Embedded<T> embedded) {
            this.embedded = embedded;
        }

        public static class Embedded<T> {
            @JsonProperty("etudiantList")
            private List<T> etudiants;

            @JsonProperty("userList")
            private List<T> users;

            @JsonProperty("controleList")
            private List<T> controles;

            public List<T> getItems() {
                if (etudiants != null) return etudiants;
                if (users != null) return users;
                if (controles != null) return controles;
                return null;
            }
        }
    }


    
    private <T> void fetchData(String urlString, TableView<T> tableView, Class<T> clazz) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.out.println("HTTP Error: " + conn.getResponseCode());
                return;
            }

            StringBuilder jsonStr = new StringBuilder();
            try (Scanner scanner = new Scanner(conn.getInputStream())) {
                while (scanner.hasNext()) {
                    jsonStr.append(scanner.nextLine());
                }
            }

            // Print API response for debugging
            System.out.println("Response from " + urlString + ": " + jsonStr.toString());

            ObjectMapper mapper = new ObjectMapper();
            ApiResponse<T> response = mapper.readValue(jsonStr.toString(),
                mapper.getTypeFactory().constructParametricType(ApiResponse.class, clazz));

            if (response.getEmbedded() != null && response.getEmbedded().getItems() != null) {
                tableView.setItems(FXCollections.observableArrayList(response.getEmbedded().getItems()));
            } else {
                System.out.println("No data found for: " + urlString);
            }
        } catch (Exception e) {
            System.err.println("Error fetching data from API: " + urlString);
            e.printStackTrace();
        }
    }



    @FXML
    private void GoToLog() {
        navigateTo("/org/openjfx/sio2E4/loginPage.fxml");
    }
    
    @FXML
    private void goToAddProfessorScreen() {
        openWindow("/org/openjfx/sio2E4/addProf.fxml", "Ajouter un Professeur");
    }
    
    @FXML
    private void goToAddStudentScreen() {
        openWindow("/org/openjfx/sio2E4/addStudent.fxml", "Ajouter un Eleve");
    }
    
    @FXML
    private void goToAddControleScreen() {
        openWindow("/org/openjfx/sio2E4/addControle.fxml", "Ajouter un Controle");
    }
    
    private void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) actiontarget.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void openWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            Scene scene = new Scene(root, 400, 400);
            stage.setScene(scene);
            stage.setMinWidth(400);
            stage.setMinHeight(400);
            stage.setMaxWidth(400);
            stage.setMaxHeight(400);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
