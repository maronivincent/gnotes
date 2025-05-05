package com.saintsau.slam2.gnotes30.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Représente une matière dans l'application. Une matière est liée à plusieurs contrôles
 * qui sont associés à des étudiants. Cette classe est mappée à la table "matieres" dans la base de données.
 * Elle permet de gérer les informations concernant la matière, telles que son intitulé,
 * ainsi que les contrôles associés à cette matière.
 */
@Entity
@Table(name = "matieres")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Matiere {

    /**
     * L'identifiant unique de la matière (clé primaire).
     */
    private Long id;

    /**
     * L'intitulé de la matière (par exemple, "Mathématiques", "Physique", etc.).
     */
    private String intitule;

    /**
     * L'ensemble des contrôles associés à cette matière.
     */
    private Set<Controle> controles;

    /**
     * Constructeur pour initialiser une matière avec son intitulé.
     * 
     * @param intitule L'intitulé de la matière
     */
    public Matiere(String intitule) {
        this.intitule = intitule;
    }

    /**
     * Constructeur par défaut, nécessaire pour JPA et Jackson.
     */
    public Matiere() {
    }

    /**
     * Getter pour l'identifiant de la matière.
     * 
     * @return L'identifiant de la matière
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Setter pour l'identifiant de la matière.
     * 
     * @param id L'identifiant de la matière
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter pour l'intitulé de la matière.
     * 
     * @return L'intitulé de la matière
     */
    @Column(name = "intitule")
    public String getIntitule() {
        return intitule;
    }

    /**
     * Setter pour l'intitulé de la matière.
     * 
     * @param intitule L'intitulé de la matière
     */
    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    /**
     * Getter pour l'ensemble des contrôles associés à cette matière.
     * 
     * @return L'ensemble des contrôles
     */
    @OneToMany(mappedBy = "matiere", cascade = CascadeType.ALL)
    @JsonManagedReference("matiere-controle")
    public Set<Controle> getControle() {
        return controles;
    }

    /**
     * Setter pour l'ensemble des contrôles associés à cette matière.
     * 
     * @param controle L'ensemble des contrôles
     */
    public void setControle(Set<Controle> controle) {
        this.controles = controle;
    }

    /**
     * Méthode toString qui retourne l'intitulé de la matière sous forme de chaîne.
     * 
     * @return L'intitulé de la matière
     */
    @Override
    public String toString() {
        return intitule;
    }
}
