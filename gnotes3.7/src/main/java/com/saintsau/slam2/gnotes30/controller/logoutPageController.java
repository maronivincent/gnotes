package com.saintsau.slam2.gnotes30.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saintsau.slam2.gnotes30.entity.Etudiant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    private final String API_BASE_URL = "http://localhost:8080";  // Change if needed

    @FXML
    public void initialize() {
        setupTables();
        loadStudentsFromAPI();
    }

    private void setupTables() {
        columnNomStudent.setCellValueFactory(new PropertyValueFactory<>("nom"));
    }

    private void loadStudentsFromAPI() {
        fetchData(API_BASE_URL + "/etudiants", studentTable);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    // Static inner class for API response
    public static class ApiResponse {
        private Embedded _embedded;

        // Getter and Setter
        public Embedded get_embedded() {
            return _embedded;
        }

        public void set_embedded(Embedded _embedded) {
            this._embedded = _embedded;
        }

        // Static inner class to hold list of Etudiant
        public static class Embedded {
            private List<Etudiant> etudiantList;

            // Getter and Setter for etudiantList
            public List<Etudiant> getEtudiantList() {
                return etudiantList;
            }

            public void setEtudiantList(List<Etudiant> etudiantList) {
                this.etudiantList = etudiantList;
            }
        }
    }

    // Fetch and deserialize data from API
    private void fetchData(String urlString, TableView<Etudiant> tableView) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("HTTP Error: " + responseCode);
                return;
            }

            StringBuilder jsonStr = new StringBuilder();
            try (Scanner scanner = new Scanner(conn.getInputStream())) {
                while (scanner.hasNext()) {
                    jsonStr.append(scanner.nextLine());
                }
            }

            // Create ObjectMapper instance
            ObjectMapper mapper = new ObjectMapper();

            // Deserialize the response into ApiResponse class
            ApiResponse response = mapper.readValue(jsonStr.toString(), ApiResponse.class);

            // Debugging: Print the list of students fetched
            if (response.get_embedded() != null && response.get_embedded().getEtudiantList() != null) {
                List<Etudiant> students = response.get_embedded().getEtudiantList();
                System.out.println("Fetched " + students.size() + " students.");
                tableView.setItems(FXCollections.observableArrayList(students));
            } else {
                System.out.println("No students found or error in response.");
            }

        } catch (Exception e) {
            System.err.println("Error fetching data from API: " + urlString);
            e.printStackTrace();
        }
    }

    @FXML
    private void GoToLog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/sio2E4/loginPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) actiontarget.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToAddProfessorScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/sio2E4/addProf.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter un Professeur");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToAddStudentScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/sio2E4/addStudent.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter un Eleve");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToAddControleScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/sio2E4/addControle.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter un Matiere");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
