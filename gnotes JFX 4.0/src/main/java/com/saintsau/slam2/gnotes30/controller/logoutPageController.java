package com.saintsau.slam2.gnotes30.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saintsau.slam2.gnotes30.entity.Controle;
import com.saintsau.slam2.gnotes30.entity.ControleDTO;
import com.saintsau.slam2.gnotes30.entity.Etudiant;
import com.saintsau.slam2.gnotes30.entity.User;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private TableColumn<Etudiant, Void> columnActionsStudent;
    @FXML
    private TableColumn<User, Void> columnActionsProfessor;
    @FXML
    private TableColumn<ControleDTO, Void> columnActionsControle;

    @FXML
    public void initialize() {
        setupTables();
        loadStudentsFromAPI();
        loadProfsFromAPI();
        loadControlesFromAPI();
        
        setupAutoRefresh();
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

        // Set up actions column for students
        columnActionsStudent.setCellFactory(param -> new TableCell<Etudiant, Void>() {
            private final Button modifyButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");

            {
                modifyButton.setOnAction(event -> {
                    Etudiant student = getTableView().getItems().get(getIndex());
                    // Handle modify action (open modify screen or edit directly)
                    modifyStudent(student);
                });
                
                deleteButton.setOnAction(event -> {
                    Etudiant student = getTableView().getItems().get(getIndex());
                    // Handle delete action
                    deleteStudent(student);
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Create a layout for buttons
                    HBox hbox = new HBox(10, modifyButton, deleteButton);
                    setGraphic(hbox);
                }
            }
        });

        // Repeat similar setup for professors and controls
        columnActionsProfessor.setCellFactory(param -> new TableCell<User, Void>() {
            private final Button modifyButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");

            {
                modifyButton.setOnAction(event -> {
                    User professor = getTableView().getItems().get(getIndex());
                    modifyProfessor(professor);
                });
                
                deleteButton.setOnAction(event -> {
                    User professor = getTableView().getItems().get(getIndex());
                    deleteProfessor(professor);
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10, modifyButton, deleteButton);
                    setGraphic(hbox);
                }
            }
        });

        columnActionsControle.setCellFactory(param -> new TableCell<ControleDTO, Void>() {
            private final Button modifyButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");

            {
                modifyButton.setOnAction(event -> {
                    ControleDTO controle = getTableView().getItems().get(getIndex());
                    modifyControle(controle);
                });

                deleteButton.setOnAction(event -> {
                    ControleDTO controle = getTableView().getItems().get(getIndex());
                    deleteControle(controle);
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10, modifyButton, deleteButton);
                    setGraphic(hbox);
                }
            }
        });
    }

    private void modifyStudent(Etudiant student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/sio2E4/editStudent.fxml"));
            Parent root = loader.load();

            // Get controller and pass student
            editStudentController controller = loader.getController();
            controller.initData(student);

            Stage stage = new Stage();
            stage.setTitle("Modifier l'étudiant");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void deleteStudent(Etudiant student) {
        deleteFromAPI(API_BASE_URL + "/etudiants/" + student.getNumero(), this::loadStudentsFromAPI);
    }


    private void modifyProfessor(User professor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/sio2E4/editProfessor.fxml"));
            Parent root = loader.load();

            editProfessorController controller = loader.getController();
            controller.initData(professor);

            Stage stage = new Stage();
            stage.setTitle("Modifier le professeur");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void deleteProfessor(User professor) {
        deleteFromAPI(API_BASE_URL + "/users/" + professor.getId(), this::loadProfsFromAPI);
    }


    private void modifyControle(ControleDTO controle) {
        if (controle == null || controle.getId() == null) {
            System.out.println("Error: Controle has no ID, cannot modify.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/sio2E4/editControle.fxml"));
            Parent root = loader.load();

            editControleController controller = loader.getController();
            controller.initData(controle);

            Stage stage = new Stage();
            stage.setTitle("Modifier le contrôle");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void deleteControle(ControleDTO controle) {
        deleteFromAPI(API_BASE_URL + "/controles/" + controle.getId(), this::loadControlesFromAPI);
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

            System.out.println("API Response: " + jsonStr.toString());  // Add logging here to check the response

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

    
    private void deleteFromAPI(String urlString, Runnable onSuccess) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200 || responseCode == 204) {
                System.out.println("Deleted successfully from: " + urlString);
                onSuccess.run(); // Refresh the table after successful delete
            } else {
                StringBuilder errorResponse = new StringBuilder();
                try (Scanner scanner = new Scanner(conn.getErrorStream())) {
                    while (scanner.hasNext()) {
                        errorResponse.append(scanner.nextLine());
                    }
                }
                System.out.println("Failed to delete. HTTP error code: " + responseCode + " Response: " + errorResponse.toString());
            }
        } catch (Exception e) {
            System.err.println("Error deleting from API: " + urlString);
            e.printStackTrace();
        }
    }



    private void setupAutoRefresh() {
        // Create a Timeline to refresh every 10 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            loadStudentsFromAPI();    // Refresh students
            loadProfsFromAPI();       // Refresh professors
            loadControlesFromAPI();   // Refresh controles
        }));

        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
        timeline.play();  // Start the timeline
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
