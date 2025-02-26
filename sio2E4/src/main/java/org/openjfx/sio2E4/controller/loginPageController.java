package org.openjfx.sio2E4.controller;

import java.io.IOException;
import java.util.List;

import org.openjfx.sio2E4.User;

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

public class loginPageController {
    private List<User> users;
    private UserLoader userLoader = new UserLoader();
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private Button loginButton;
    
    @FXML
    public void initialize() {
        users = userLoader.loadUsersFromFile();
    }
    
    @FXML
    private void GoToHome() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        // Validation des informations de connexion
        boolean isAuthenticated = false;
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                isAuthenticated = true;
                break;
            }
        }
        
        if (isAuthenticated) {
            // Authentification réussie, rediriger vers la page d'accueil
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/sio2E4/homePage.fxml"));
                
                Parent root = loader.load();
                
                Stage stage = (Stage) loginButton.getScene().getWindow();
             // Récupérer la taille de l'écran
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();
                
                Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
                
                
                stage.setScene(scene);
                
                // Afficher la fenêtre
                stage.show();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Afficher un message d'erreur si l'authentification échoue
            errorLabel.setText("Identifiant ou mot de passe incorrect !");
            errorLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
