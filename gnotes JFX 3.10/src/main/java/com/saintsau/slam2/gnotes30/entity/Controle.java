package com.saintsau.slam2.gnotes30.entity;

import jakarta.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "controles")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Controle {

    private Long id;              // Clé primaire
    private Etudiant etudiant;    // Référence à l'étudiant
    private User professeur;      // Référence au professeur (utilisateur)
    private Matiere matiere;      // Référence à la matière
    private int coefficient;      // Coefficient du contrôle
    private String type;          // Type de contrôle (DS, Oral, TP)
    private float note;           // Note obtenue
    private Date dateControle;    // Date du contrôle

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

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    @JsonBackReference(value = "etudiant-controle")
    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    @ManyToOne
    @JoinColumn(name = "professeur_id", nullable = false)
    @JsonBackReference(value = "professeur-controle")  // Prevents serialization of the professor to avoid a circular reference
    public User getProfesseur() {
        return professeur;
    }

    public void setProfesseur(User professeur) {
        this.professeur = professeur;
    }

    @ManyToOne
    @JoinColumn(name = "matiere_id", nullable = false)
    @JsonBackReference(value = "matiere-controle")
    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    @Column(name = "coefficient", nullable = false)
    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    @Column(name = "type", nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "note", nullable = false)
    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    @Column(name = "date_controle", nullable = false)
    @Temporal(TemporalType.DATE)
    public Date getDateControle() {
        return dateControle;
    }

    public void setDateControle(Date dateControle) {
        this.dateControle = dateControle;
    }
}
