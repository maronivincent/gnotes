package com.saintsau.slam2.gnotes30.controller;

import com.saintsau.slam2.gnotes30.entity.User;
import com.saintsau.slam2.gnotes30.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Contrôleur pour gérer les opérations CRUD sur les utilisateurs (Users).
 * Fournit des endpoints pour ajouter, récupérer, modifier et supprimer des utilisateurs.
 */
@RestController
public class UserController {
    private final UserService userService;

    /**
     * Constructeur pour initialiser le service des utilisateurs.
     * @param userService Service de gestion des utilisateurs.
     */
    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    /**
     * Crée un nouvel utilisateur et l'ajoute à la base de données.
     * @param user L'utilisateur à ajouter.
     * @return L'entité utilisateur ajoutée avec des liens HATEOAS.
     */
    @PostMapping("/users")
    public EntityModel<User> newUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);

        return EntityModel.of(savedUser,
                linkTo(methodOn(UserController.class).getUserById(savedUser.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }

    /**
     * Récupère la liste de tous les utilisateurs.
     * @return Une collection d'entités utilisateur avec des liens HATEOAS.
     */
    @GetMapping("/users")
    CollectionModel<EntityModel<User>> all() {
        List<EntityModel<User>> users = StreamSupport.stream(userService.findAllUser().spliterator(), false)
                .map((User user) -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    /**
     * Récupère un utilisateur spécifique par son ID.
     * @param id L'ID de l'utilisateur à récupérer.
     * @return L'entité utilisateur avec des liens HATEOAS.
     */
    @GetMapping("/users/{id}")
    EntityModel<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }

    /**
     * Met à jour un utilisateur existant par son ID.
     * @param id L'ID de l'utilisateur à modifier.
     * @param updatedUser L'objet utilisateur mis à jour.
     * @return L'entité utilisateur mise à jour avec des liens HATEOAS.
     */
    @PutMapping("/users/{id}")
    public EntityModel<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);

        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }

    /**
     * Supprime un utilisateur par son ID.
     * @param id L'ID de l'utilisateur à supprimer.
     * @return Une réponse contenant un message de confirmation et l'utilisateur supprimé avec des liens HATEOAS.
     */
    @DeleteMapping("/users/{id}")
    EntityModel<Map<String, Object>> deleteUser(@PathVariable Long id) {
        User deletedUser = userService.deleteUser(id);
        
        String message = "Utilisateur supprimé: " + deletedUser.getUsername();
        
        Map<String, Object> reponse = new HashMap<>();
        reponse.put("message", message);
        reponse.put("user", deletedUser);
        
        return EntityModel.of(reponse,
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }
}
