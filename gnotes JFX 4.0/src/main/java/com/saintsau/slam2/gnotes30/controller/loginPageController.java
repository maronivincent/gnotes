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

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Embedded<T> {
            @JsonProperty("userList")
            private List<T> users;

            public List<T> getItems() {
                return users;
            }

            public void setUsers(List<T> users) {
                this.users = users;
            }
        }
    }
}
