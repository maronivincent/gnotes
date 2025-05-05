package com.saintsau.slam2.gnotes30.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Représente un utilisateur dans l'application. Cette classe est mappée à la table "users" dans la base de données.
 * Un utilisateur possède un identifiant unique, un nom d'utilisateur, un mot de passe et un statut d'activation.
 * Cette classe est utilisée pour gérer l'authentification et les informations des utilisateurs.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
public class User {

    /**
     * L'identifiant unique de l'utilisateur (clé primaire).
     */
    private Long id;

    /**
     * Le nom d'utilisateur de l'utilisateur. Il doit être unique dans l'application.
     */
    private String username;

    /**
     * Le mot de passe de l'utilisateur. Il est stocké de manière sécurisée.
     */
    private String password;

    /**
     * Statut activé de l'utilisateur. Indique si le compte est actif (true) ou désactivé (false).
     */
    private boolean enabled;

    // Constructeurs

    /**
     * Constructeur par défaut, nécessaire pour JPA et Jackson.
     */
    public User() {
        super();
    }

    /**
     * Constructeur pour initialiser un utilisateur avec un nom d'utilisateur, un mot de passe et un statut d'activation.
     * 
     * @param username Le nom d'utilisateur
     * @param password Le mot de passe
     * @param enabled Le statut d'activation de l'utilisateur
     */
    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }
    
    /**
     * Constructeur pour initialiser un utilisateur avec seulement un nom d'utilisateur.
     * 
     * @param username Le nom d'utilisateur
     */
    public User(String username) {
        this.username = username;
    }

    // Getters et Setters

    /**
     * Getter pour l'identifiant de l'utilisateur.
     * 
     * @return L'identifiant de l'utilisateur
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Setter pour l'identifiant de l'utilisateur.
     * 
     * @param id L'identifiant de l'utilisateur
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter pour le nom d'utilisateur.
     * 
     * @return Le nom d'utilisateur
     */
    @Column(name = "username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    /**
     * Setter pour le nom d'utilisateur.
     * 
     * @param username Le nom d'utilisateur
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter pour le mot de passe.
     * 
     * @return Le mot de passe
     */
    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    /**
     * Setter pour le mot de passe.
     * 
     * @param password Le mot de passe
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter pour le statut d'activation de l'utilisateur.
     * 
     * @return Le statut d'activation de l'utilisateur (true si activé, false si désactivé)
     */
    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Setter pour le statut d'activation de l'utilisateur.
     * 
     * @param enabled Le statut d'activation de l'utilisateur
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Méthode toString qui retourne une chaîne représentant l'utilisateur.
     * 
     * @return Une chaîne représentant l'utilisateur sous la forme : "User{id=ID, username='username', enabled=true/false}"
     */
    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', enabled=" + enabled + "}";
    }
}
