package com.saintsau.slam2.gnotes30.controller;

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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginPageController {
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private Button loginButton;
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gnotesdb";
    private static final String DB_USER = "etudiant";
    private static final String DB_PASSWORD = "Bsio2024";
    
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
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            return rs.next(); // Returns true if a matching user is found
            
        } catch (SQLException e) {
            e.printStackTrace();
            errorLabel.setText("Database connection error.");
            errorLabel.setStyle("-fx-text-fill: red;");
            return false;
        }
    }
}
