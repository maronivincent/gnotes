package com.saintsau.slam2.gnotes30.entity;

import jakarta.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Entité représentant un contrôle réalisé par un étudiant.
 * Contient des informations sur l'étudiant, le professeur, la matière, le coefficient, le type de contrôle, la note, et la date du contrôle.
 */
@Entity
@Table(name = "controles")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Controle {

    private Long id;              // Clé primaire du contrôle
    private Etudiant etudiant;    // Référence à l'étudiant ayant passé le contrôle
    private User professeur;      // Référence au professeur ayant donné le contrôle
    private Matiere matiere;      // Référence à la matière du contrôle
    private int coefficient;      // Coefficient du contrôle
    private String type;          // Type de contrôle (Ex: DS, Oral, TP)
    private float note;           // Note obtenue par l'étudiant
    private Date dateControle;    // Date à laquelle le contrôle a eu lieu

    /**
     * Récupère l'identifiant du contrôle.
     * 
     * @return L'identifiant du contrôle
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Modifie l'identifiant du contrôle.
     * 
     * @param id L'identifiant à attribuer au contrôle
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Récupère l'étudiant ayant passé le contrôle.
     * 
     * @return L'étudiant lié à ce contrôle
     */
    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    @JsonBackReference(value = "etudiant-controle")  // Empêche la sérialisation de l'étudiant pour éviter les références circulaires
    public Etudiant getEtudiant() {
        return etudiant;
    }

    /**
     * Modifie l'étudiant ayant passé le contrôle.
     * 
     * @param etudiant L'étudiant à associer au contrôle
     */
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    /**
     * Récupère le professeur ayant donné le contrôle.
     * 
     * @return Le professeur lié à ce contrôle
     */
    @ManyToOne
    @JoinColumn(name = "professeur_id", nullable = true)
    @JsonBackReference(value = "professeur-controle")  // Empêche la sérialisation du professeur pour éviter les références circulaires
    public User getProfesseur() {
        return professeur;
    }

    /**
     * Modifie le professeur ayant donné le contrôle.
     * 
     * @param professeur Le professeur à associer au contrôle
     */
    public void setProfesseur(User professeur) {
        this.professeur = professeur;
    }

    /**
     * Récupère la matière du contrôle.
     * 
     * @return La matière liée à ce contrôle
     */
    @ManyToOne
    @JoinColumn(name = "matiere_id", nullable = false)
    @JsonBackReference(value = "matiere-controle")  // Empêche la sérialisation de la matière pour éviter les références circulaires
    public Matiere getMatiere() {
        return matiere;
    }

    /**
     * Modifie la matière du contrôle.
     * 
     * @param matiere La matière à associer au contrôle
     */
    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    /**
     * Récupère le coefficient du contrôle.
     * 
     * @return Le coefficient du contrôle
     */
    @Column(name = "coefficient", nullable = false)
    public int getCoefficient() {
        return coefficient;
    }

    /**
     * Modifie le coefficient du contrôle.
     * 
     * @param coefficient Le coefficient à attribuer au contrôle
     */
    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    /**
     * Récupère le type de contrôle (par exemple : DS, Oral, TP).
     * 
     * @return Le type de contrôle
     */
    @Column(name = "type", nullable = false)
    public String getType() {
        return type;
    }

    /**
     * Modifie le type du contrôle.
     * 
     * @param type Le type à attribuer au contrôle (par exemple : "Examen", "Devoir", "Oral")
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Récupère la note obtenue par l'étudiant lors du contrôle.
     * 
     * @return La note obtenue
     */
    @Column(name = "note", nullable = false)
    public float getNote() {
        return note;
    }

    /**
     * Modifie la note obtenue par l'étudiant lors du contrôle.
     * 
     * @param note La note à attribuer à l'étudiant
     */
    public void setNote(float note) {
        this.note = note;
    }

    /**
     * Récupère la date à laquelle le contrôle a eu lieu.
     * 
     * @return La date du contrôle
     */
    @Column(name = "date_controle", nullable = true)
    @Temporal(TemporalType.DATE)
    public Date getDateControle() {
        return dateControle;
    }

    /**
     * Modifie la date à laquelle le contrôle a eu lieu.
     * 
     * @param dateControle La date à attribuer au contrôle
     */
    public void setDateControle(Date dateControle) {
        this.dateControle = dateControle;
    }
}
