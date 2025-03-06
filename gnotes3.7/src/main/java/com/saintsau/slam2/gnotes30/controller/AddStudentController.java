package com.saintsau.slam2.gnotes30.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddStudentController {

    @FXML
    private TextField nameField;



    @FXML
    private void saveStudent() {
        String name = nameField.getText();

        if (name.isEmpty()) {
            System.out.println("Tous les champs doivent être remplis !");
            return;
        }

        // Logic to save the Student (e.g., write to a database or a file)
        System.out.println("Eleves ajouté : " + name);

        // Optionally clear fields after saving
        nameField.clear();
    }
}
