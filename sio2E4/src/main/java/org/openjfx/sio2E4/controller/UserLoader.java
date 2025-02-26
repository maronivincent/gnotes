package org.openjfx.sio2E4.controller;

import org.openjfx.sio2E4.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.InputStream;
import java.util.List;

public class UserLoader {
    public List<User> loadUsersFromFile() {
        List<User> users = null;
        try {
            // Charger le fichier JSON depuis le même dossier que le contrôleur
            InputStream inputStream = getClass().getResourceAsStream("users.json");

            // Utiliser Jackson pour mapper le contenu JSON dans une liste d'objets User
            ObjectMapper objectMapper = new ObjectMapper();
            UserListWrapper userListWrapper = objectMapper.readValue(inputStream, UserListWrapper.class);
            users = userListWrapper.getUsers();

            // Afficher les utilisateurs chargés (débogage)
            System.out.println("Utilisateurs chargés : " + users);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
