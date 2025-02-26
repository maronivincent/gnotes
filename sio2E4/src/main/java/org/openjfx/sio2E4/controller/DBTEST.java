package org.openjfx.sio2E4.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBTEST {
    public static void main(String[] args) {
        try {
            // Charger le driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // URL de connexion à la base de données
            String url = "jdbc:mysql://localhost:3306/nom_de_votre_base";
            String username = "votre_utilisateur";
            String password = "etudiant";

            // Établir la connexion
            Connection connection = DriverManager.getConnection(url, username, password);
            
            // Créer une requête
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM utilisateurs");

            // Afficher les résultats
            while (resultSet.next()) {
                System.out.println("Utilisateur : " + resultSet.getString("username"));
            }

            // Fermer la connexion
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
