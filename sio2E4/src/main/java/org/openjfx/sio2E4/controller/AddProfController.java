package org.openjfx.sio2E4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddProfController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField subjectField;

    @FXML
    private void saveProfessor() {
        String name = nameField.getText();
        String subject = subjectField.getText();

        if (name.isEmpty() || subject.isEmpty()) {
            System.out.println("Tous les champs doivent être remplis !");
            return;
        }

        // Logic to save the professor (e.g., write to a database or a file)
        System.out.println("Professeur ajouté : " + name + ", Matière : " + subject);

        // Optionally clear fields after saving
        nameField.clear();
        subjectField.clear();
    }
}
