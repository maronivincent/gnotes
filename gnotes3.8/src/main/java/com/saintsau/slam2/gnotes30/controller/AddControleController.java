package com.saintsau.slam2.gnotes30.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddControleController {

    @FXML
    private TextField intituleField;

    @FXML
    private TextField typeField;
    @FXML
    private TextField coefField;
    @FXML
    private TextField noteField;
    @FXML
    private TextField nameStudentField;

    @FXML
    private void saveControle() {
        String matiere = intituleField.getText();
        String type = typeField.getText();
        String coef = coefField.getText();
        String note = noteField.getText();
        String studentName = nameStudentField.getText();


        if (matiere.isEmpty() || type.isEmpty() || coef.isEmpty()|| note.isEmpty()|| studentName.isEmpty())  {
            System.out.println("Tous les champs doivent être remplis !");
            return;
        }

        // Logic to save the professor (e.g., write to a database or a file)
        System.out.println("Controle ajouté : " + matiere + ", Type : " + type + " , Coefficient : " + coef + " , Note : " + note + " , Eleve : " + studentName);

        // Optionally clear fields after saving
        intituleField.clear();
        typeField.clear();
        coefField.clear();
        noteField.clear();
        nameStudentField.clear();
    }
}
