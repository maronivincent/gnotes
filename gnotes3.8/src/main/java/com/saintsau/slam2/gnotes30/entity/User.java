package com.saintsau.slam2.gnotes30.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
public class User {

    private Long id;           // Clé primaire
    private String username;   // Nom d'utilisateur
    private String password;   // Mot de passe
    private boolean enabled;   // Statut activé de l'utilisateur

    private Set<Controle> controles;  // Liste des contrôles associés au professeur (foreign key)

    // Constructeurs
    public User() {
    	super();
    }

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }
    
    public User(String username) {
        this.username = username;
    }
    
    public User(String username,Set<Controle> controles) {
        this.username = username;
        this.controles = controles;
    }

    // Getters et Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @OneToMany(mappedBy = "professeur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JsonBackReference
    public Set<Controle> getControles() {
        return controles;
    }

    public void setControles(Set<Controle> controles) {
        this.controles = controles;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', enabled=" + enabled + "}";
    }
}
