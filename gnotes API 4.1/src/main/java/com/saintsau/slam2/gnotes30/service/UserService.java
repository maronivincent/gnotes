package com.saintsau.slam2.gnotes30.service;

import com.saintsau.slam2.gnotes30.entity.User;
import com.saintsau.slam2.gnotes30.exeption.UserNotFoundExeption;
import com.saintsau.slam2.gnotes30.jpaRepository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour gérer les utilisateurs.
 * Cette classe contient des méthodes pour récupérer, ajouter, mettre à jour et supprimer des utilisateurs.
 */
@Service
public class UserService {

    private final UserRepository userRepository; // Repository pour accéder aux données des utilisateurs

    /**
     * Constructeur pour injecter les dépendances.
     *
     * @param userRepository Repository pour les utilisateurs
     */
    public UserService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    /**
     * Récupère tous les utilisateurs.
     *
     * @return Liste de tous les utilisateurs
     */
    public List<User> findAllUser() {
        return (List<User>) userRepository.findAll(); // Utilise CrudRepository, il peut être converti en List
    }

    /**
     * Récupère un utilisateur par son ID.
     * Lève une exception si l'utilisateur n'est pas trouvé.
     *
     * @param id L'ID de l'utilisateur à récupérer
     * @return L'utilisateur correspondant à l'ID
     * @throws UserNotFoundExeption Si l'utilisateur n'est pas trouvé
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundExeption(id));
    }

    /**
     * Crée un nouvel utilisateur.
     *
     * @param user L'utilisateur à créer
     * @return L'utilisateur créé
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Met à jour un utilisateur existant.
     * Lève une exception si l'utilisateur n'existe pas.
     *
     * @param id L'ID de l'utilisateur à mettre à jour
     * @param updatedUser L'utilisateur avec les nouvelles valeurs
     * @return L'utilisateur mis à jour
     * @throws UserNotFoundExeption Si l'utilisateur n'est pas trouvé
     */
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundExeption(id));
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setEnabled(updatedUser.isEnabled());
        return userRepository.save(user);
    }

    /**
     * Supprime un utilisateur par son ID.
     * Lève une exception si l'utilisateur n'est pas trouvé.
     *
     * @param id L'ID de l'utilisateur à supprimer
     * @return L'utilisateur supprimé
     * @throws UserNotFoundExeption Si l'utilisateur n'est pas trouvé
     */
    public User deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundExeption(id));

        userRepository.deleteById(id);
        return user;
    }
}
