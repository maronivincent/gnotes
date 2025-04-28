package com.saintsau.slam2.gnotes30.controller;

import com.saintsau.slam2.gnotes30.entity.User;
import com.saintsau.slam2.gnotes30.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
            super();
        this.userService = userService;
    }

    // POST: Create a new User
    @PostMapping("/users")
    public EntityModel<User> newUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);

        return EntityModel.of(savedUser,
                linkTo(methodOn(UserController.class).getUserById(savedUser.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }

    // GET: Retrieve all Users
    @GetMapping("/users")
    CollectionModel<EntityModel<User>> all() {
        List<EntityModel<User>> users = StreamSupport.stream(userService.findAllUser().spliterator(), false)
                .map((User user) -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    // GET: Retrieve a single User by ID
    @GetMapping("/users/{id}")
    EntityModel<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }

    // PUT: Update an existing User by ID
    @PutMapping("/users/{id}")
    public EntityModel<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);

        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }

    // DELETE: Delete a User by ID
    @DeleteMapping("/users/{id}")
    EntityModel<Map<String, Object>> deleteUser(@PathVariable Long id) {
        User deletedUser = userService.deleteUser(id);
        
        String message = "Utilisateur supprimé: " + deletedUser.getUsername();
        
        Map<String, Object> reponse = new HashMap<>();
        reponse.put("message", message);
        reponse.put("etudiant", deletedUser);
        
        return EntityModel.of(reponse,
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }
}


