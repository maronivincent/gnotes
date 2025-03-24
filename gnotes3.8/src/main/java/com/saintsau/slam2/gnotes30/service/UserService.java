package com.saintsau.slam2.gnotes30.service;

import com.saintsau.slam2.gnotes30.entity.User;
import com.saintsau.slam2.gnotes30.exeption.UserNotFoundExeption;
import com.saintsau.slam2.gnotes30.jpaRepository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    
    public UserRepository getUserRepository() {
    	return userRepository;
    }
    
    UserService (UserRepository userRepository) {
    	super();
    	this.userRepository = userRepository;
    }
    
    // Récupérer tous les utilisateurs
    public List<User> findAllUser() {
        return (List<User>) userRepository.findAll(); // Utilise CrudRepository, il peut être converti en List
    }
    
 // Récupérer un utilisateur par ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
        		.orElseThrow(() -> new UserNotFoundExeption(id));
    }

    // Créer un nouvel utilisateur
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Mettre à jour un utilisateur
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundExeption(id));
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setEnabled(updatedUser.isEnabled());
        return userRepository.save(user);
    }

    
    public User deleteUser(Long id) {
        User User = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundExeption(id));
       
        userRepository.deleteById(id);
        return User;
        
    }
	    
}


