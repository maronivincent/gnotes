package com.saintsau.slam2.gnotes30.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saintsau.slam2.gnotes30.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Contrôleur pour la page de connexion.
 * Gère l'authentification de l'utilisateur et la redirection vers la page d'accueil.
 */
public class loginPageController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    private static final String API_URL = "http://localhost:8080/users";

    /**
     * Gère l'action de connexion. Vérifie les identifiants de l'utilisateur
     * et charge la page d'accueil si l'authentification est réussie.
     */
    @FXML
    private void GoToHome() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticateUser(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/sio2E4/homePage.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) loginButton.getScene().getWindow();

                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();

                Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Identifiant ou mot de passe incorrect !");
            errorLabel.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Vérifie les identifiants de l'utilisateur en les comparant aux données de l'API.
     * @param username Le nom d'utilisateur.
     * @param password Le mot de passe.
     * @return true si l'utilisateur est authentifié avec succès, sinon false.
     */
    private boolean authenticateUser(String username, String password) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                errorLabel.setText("Erreur lors de la connexion à l'API.");
                errorLabel.setStyle("-fx-text-fill: red;");
                return false;
            }

            StringBuilder jsonStr = new StringBuilder();
            try (Scanner scanner = new Scanner(conn.getInputStream())) {
                while (scanner.hasNext()) {
                    jsonStr.append(scanner.nextLine());
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            ApiResponse<User> response = mapper.readValue(
                jsonStr.toString(),
                mapper.getTypeFactory().constructParametricType(ApiResponse.class, User.class)
            );

            List<User> users = response.getEmbedded() != null ? response.getEmbedded().getItems() : null;

            if (users != null) {
                for (User user : users) {
                    String dbPassword = user.getPassword();
                    if (dbPassword != null && dbPassword.startsWith("{noop}")) {
                        dbPassword = dbPassword.substring(6); // Remove {noop}
                    }

                    if (user.getUsername().equals(username) && dbPassword.equals(password)) {
                        return true;
                    }
                }
            }

            return false;

        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Erreur de connexion à l'API.");
            errorLabel.setStyle("-fx-text-fill: red;");
            return false;
        }
    }

    /**
     * Réponse générique d'une API pour un utilisateur.
     * Utilisée pour désérialiser la réponse JSON de l'API.
     * @param <T> Type générique.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ApiResponse<T> {
        
        @JsonProperty("_embedded")
        private Embedded<T> embedded;

        /**
         * Retourne l'objet encapsulé dans la réponse API.
         * @return L'objet encapsulé dans la réponse API.
         */
        public Embedded<T> getEmbedded() {
            return embedded;
        }

        public void setEmbedded(Embedded<T> embedded) {
            this.embedded = embedded;
        }

        /**
         * Classe interne pour les objets imbriqués de la réponse API.
         * @param <T> Type générique.
         */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Embedded<T> {
            @JsonProperty("userList")
            private List<T> users;

            /**
             * Retourne la liste des utilisateurs.
             * @return Liste des utilisateurs.
             */
            public List<T> getItems() {
                return users;
            }

            public void setUsers(List<T> users) {
                this.users = users;
            }
        }
    }
}
