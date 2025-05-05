package com.saintsau.slam2.gnotes30.entity;

import jakarta.persistence.*;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Représente un utilisateur dans l'application. 
 * Un utilisateur peut être un professeur et est associé à plusieurs contrôles.
 * 
 * Cette classe gère les informations de connexion et de sécurité pour l'utilisateur,
 * ainsi que sa relation avec les contrôles qu'il a assignés.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
public class User {

    private Long id;               // Clé primaire de l'utilisateur
    private String username;       // Nom d'utilisateur
    private String password;       // Mot de passe
    private boolean enabled;       // Statut activé de l'utilisateur

    private Set<Controle> controles; // Liste des contrôles associés à cet utilisateur (professeur)

    /**
     * Constructeur par défaut pour l'entité User.
     * Nécessaire pour la création d'instances sans initialisation de valeurs.
     */
    public User() {
        super();
    }

    /**
     * Constructeur pour initialiser un utilisateur avec un nom d'utilisateur, un mot de passe et un statut activé.
     * 
     * @param username Nom d'utilisateur
     * @param password Mot de passe
     * @param enabled Statut activé de l'utilisateur
     */
    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }
    
    /**
     * Constructeur pour initialiser un utilisateur avec un nom d'utilisateur.
     * 
     * @param username Nom d'utilisateur
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * Constructeur pour initialiser un utilisateur avec un nom d'utilisateur et un ensemble de contrôles.
     * 
     * @param username Nom d'utilisateur
     * @param controles Ensemble des contrôles associés à cet utilisateur
     */
    public User(String username, Set<Controle> controles) {
        this.username = username;
        this.controles = controles;
    }

    /**
     * Récupère l'ID de l'utilisateur.
     * 
     * @return L'ID de l'utilisateur
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Définit l'ID de l'utilisateur.
     * 
     * @param id L'ID de l'utilisateur
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Récupère le nom d'utilisateur.
     * 
     * @return Le nom d'utilisateur
     */
    @Column(name = "username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    /**
     * Définit le nom d'utilisateur.
     * 
     * @param username Le nom d'utilisateur
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Récupère le mot de passe de l'utilisateur.
     * 
     * @return Le mot de passe de l'utilisateur
     */
    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe de l'utilisateur.
     * 
     * @param password Le mot de passe de l'utilisateur
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Récupère le statut activé de l'utilisateur.
     * 
     * @return Le statut activé de l'utilisateur
     */
    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Définit le statut activé de l'utilisateur.
     * 
     * @param enabled Le statut activé de l'utilisateur
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Récupère l'ensemble des contrôles associés à ce professeur.
     * 
     * @return L'ensemble des contrôles associés à l'utilisateur (professeur)
     */
    @OneToMany(mappedBy = "professeur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("professeur-controle")
    public Set<Controle> getControles() {
        return controles;
    }

    /**
     * Définit l'ensemble des contrôles associés à ce professeur.
     * 
     * @param controles L'ensemble des contrôles associés à l'utilisateur (professeur)
     */
    public void setControles(Set<Controle> controles) {
        this.controles = controles;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'utilisateur.
     * 
     * @return La représentation sous forme de chaîne de caractères de l'utilisateur
     */
    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', enabled=" + enabled + "}";
    }
}
