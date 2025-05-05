package com.saintsau.slam2.gnotes30.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

/**
 * Représente une matière dans l'application.
 * Chaque matière possède un intitulé et un ensemble de contrôles associés.
 */
@Entity
@Table(name = "matieres")
public class Matiere {

    private Long id;              // Clé primaire de la matière
    private String intitule;      // Intitulé de la matière (par exemple, "Mathématiques", "Physique", etc.)
    private Set<Controle> controles; // Ensemble des contrôles associés à la matière

    /**
     * Constructeur pour initialiser une matière avec son intitulé.
     * 
     * @param intitule L'intitulé de la matière
     */
    public Matiere(String intitule) {
        this.intitule = intitule;
    }

    /**
     * Constructeur par défaut pour créer un objet Matiere sans valeurs initiales.
     */
    public Matiere() {}

    /**
     * Récupère l'ID de la matière.
     * 
     * @return L'ID de la matière
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Définit l'ID de la matière.
     * 
     * @param id L'ID de la matière
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Récupère l'intitulé de la matière.
     * 
     * @return L'intitulé de la matière
     */
    @Column(name = "intitule")
    public String getIntitule() {
        return intitule;
    }

    /**
     * Définit l'intitulé de la matière.
     * 
     * @param intitule L'intitulé de la matière
     */
    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    /**
     * Récupère l'ensemble des contrôles associés à cette matière.
     * 
     * @return L'ensemble des contrôles associés à la matière
     */
    @OneToMany(mappedBy = "matiere", cascade = CascadeType.ALL)
    @JsonManagedReference("matiere-controle")
    public Set<Controle> getControle() {
        return controles;
    }

    /**
     * Définit l'ensemble des contrôles associés à cette matière.
     * 
     * @param controle L'ensemble des contrôles associés à la matière
     */
    public void setControle(Set<Controle> controle) {
        this.controles = controle;
    }
}
