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

/**
 * Contrôleur de la page de déconnexion.
 * Gère l'affichage des tables d'étudiants, professeurs et contrôles.
 */
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

    /**
     * Initialise les tables et charge les données des étudiants, professeurs et contrôles depuis l'API.
     */
    @FXML
    public void initialize() {
        setupTables();
        loadStudentsFromAPI();
        loadProfsFromAPI();
        loadControlesFromAPI();
        setupAutoRefresh();
    }

    /**
     * Configure les colonnes des tables pour afficher les données.
     */
    private void setupTables() {
        // Configuration des colonnes pour chaque table
        columnNomStudent.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnPrenomStudent.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        columnNomProfessor.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnMatiereControle.setCellValueFactory(new PropertyValueFactory<>("intitule"));
        columnCoefControle.setCellValueFactory(new PropertyValueFactory<>("coefficient"));
        columnTypeControle.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnNoteControle.setCellValueFactory(new PropertyValueFactory<>("note"));
        columnNomEtudiantControle.setCellValueFactory(new PropertyValueFactory<>("nomEtudiant"));
        columnDateControle.setCellValueFactory(new PropertyValueFactory<>("dateControle"));

        // Configuration des boutons d'action pour les étudiants
        columnActionsStudent.setCellFactory(param -> new TableCell<Etudiant, Void>() {
            private final Button modifyButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");

            {
                modifyButton.setOnAction(event -> {
                    Etudiant student = getTableView().getItems().get(getIndex());
                    modifyStudent(student);
                });

                deleteButton.setOnAction(event -> {
                    Etudiant student = getTableView().getItems().get(getIndex());
                    deleteStudent(student);
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Affichage des boutons d'action pour chaque étudiant
                    HBox hbox = new HBox(10, modifyButton, deleteButton);
                    setGraphic(hbox);
                }
            }
        });

        // Configuration des boutons d'action pour les professeurs et les contrôles
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

    /**
     * Ouvre la fenêtre de modification pour un étudiant.
     * @param student L'étudiant à modifier.
     */
    private void modifyStudent(Etudiant student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/sio2E4/editStudent.fxml"));
            Parent root = loader.load();

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

    /**
     * Supprime un étudiant via l'API.
     * @param student L'étudiant à supprimer.
     */
    private void deleteStudent(Etudiant student) {
        deleteFromAPI(API_BASE_URL + "/etudiants/" + student.getNumero(), this::loadStudentsFromAPI);
    }

    /**
     * Ouvre la fenêtre de modification pour un professeur.
     * @param professor Le professeur à modifier.
     */
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

    /**
     * Supprime un professeur via l'API.
     * @param professor Le professeur à supprimer.
     */
    private void deleteProfessor(User professor) {
        deleteFromAPI(API_BASE_URL + "/users/" + professor.getId(), this::loadProfsFromAPI);
    }

    /**
     * Ouvre la fenêtre de modification pour un contrôle.
     * @param controle Le contrôle à modifier.
     */
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

    /**
     * Supprime un contrôle via l'API.
     * @param controle Le contrôle à supprimer.
     */
    private void deleteControle(ControleDTO controle) {
        deleteFromAPI(API_BASE_URL + "/controles/" + controle.getId(), this::loadControlesFromAPI);
    }

    /**
     * Charge les étudiants depuis l'API.
     */
    private void loadStudentsFromAPI() {
        fetchData(API_BASE_URL + "/etudiants", studentTable, Etudiant.class);
    }

    /**
     * Charge les professeurs depuis l'API.
     */
    private void loadProfsFromAPI() {
        fetchData(API_BASE_URL + "/users", professorTable, User.class);
    }

    /**
     * Charge les contrôles depuis l'API.
     */
    private void loadControlesFromAPI() {
        // Implémentation pour charger les contrôles depuis l'API
    }

    /**
     * Supprime des données via l'API.
     * @param urlString L'URL pour la suppression.
     * @param onSuccess Action à exécuter après la suppression réussie.
     */
    private void deleteFromAPI(String urlString, Runnable onSuccess) {
        // Implémentation pour la suppression des données via l'API
    }

    /**
     * Configure un rafraîchissement automatique toutes les 10 secondes.
     */
    private void setupAutoRefresh() {
        // Implémentation pour rafraîchir les données automatiquement
    }

    /**
     * Effectue une requête GET vers une URL et charge les données dans une TableView.
     * @param <T> Le type des objets à charger.
     * @param urlString L'URL à interroger.
     * @param tableView La TableView où afficher les données.
     * @param clazz La classe des objets à charger.
     */
    private <T> void fetchData(String urlString, TableView<T> tableView, Class<T> clazz) {
        // Implémentation pour charger des données depuis l'API
    }

    /**
     * Navigue vers la page de connexion.
     */
    @FXML
    private void GoToLog() {
        navigateTo("/org/openjfx/sio2E4/loginPage.fxml");
    }

    /**
     * Ouvre la fenêtre pour ajouter un professeur.
     */
    @FXML
    private void goToAddProfessorScreen() {
        openWindow("/org/openjfx/sio2E4/addProf.fxml", "Ajouter un Professeur");
    }

    /**
     * Ouvre la fenêtre pour ajouter un étudiant.
     */
    @FXML
    private void goToAddStudentScreen() {
        openWindow("/org/openjfx/sio2E4/addStudent.fxml", "Ajouter un Eleve");
    }

    /**
     * Ouvre la fenêtre pour ajouter un contrôle.
     */
    @FXML
    private void goToAddControleScreen() {
        openWindow("/org/openjfx/sio2E4/addControle.fxml", "Ajouter un Controle");
    }

    /**
     * Navigue vers une nouvelle page en chargeant le fichier FXML.
     * @param fxmlPath Le chemin du fichier FXML à charger.
     */
    private void navigateTo(String fxmlPath) {
        // Implémentation de la navigation
    }

    /**
     * Ouvre une nouvelle fenêtre avec une scène spécifiée par le fichier FXML.
     * @param fxmlPath Le chemin du fichier FXML.
     * @param title Le titre de la fenêtre.
     */
    private void openWindow(String fxmlPath, String title) {
        // Implémentation pour ouvrir une nouvelle fenêtre
    }
}
