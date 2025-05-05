package com.saintsau.slam2.gnotes30.entity;

import jakarta.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Représente un contrôle (examen, devoir, oral) réalisé par un étudiant dans une matière spécifique.
 */
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

    /**
     * Obtient l'identifiant du contrôle.
     * @return L'identifiant du contrôle
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant du contrôle.
     * @param id L'identifiant du contrôle
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtient l'étudiant associé à ce contrôle.
     * @return L'étudiant associé au contrôle
     */
    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    @JsonBackReference(value = "etudiant-controle")
    public Etudiant getEtudiant() {
        return etudiant;
    }

    /**
     * Définit l'étudiant associé à ce contrôle.
     * @param etudiant L'étudiant à associer au contrôle
     */
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    /**
     * Obtient le professeur associé à ce contrôle.
     * @return Le professeur associé au contrôle
     */
    @ManyToOne
    @JoinColumn(name = "professeur_id", nullable = false)
    @JsonBackReference(value = "professeur-controle")
    public User getProfesseur() {
        return professeur;
    }

    /**
     * Définit le professeur associé à ce contrôle.
     * @param professeur Le professeur à associer au contrôle
     */
    public void setProfesseur(User professeur) {
        this.professeur = professeur;
    }

    /**
     * Obtient la matière associée à ce contrôle.
     * @return La matière du contrôle
     */
    @ManyToOne
    @JoinColumn(name = "matiere_id", nullable = false)
    @JsonBackReference(value = "matiere-controle")
    public Matiere getMatiere() {
        return matiere;
    }

    /**
     * Définit la matière associée à ce contrôle.
     * @param matiere La matière à associer au contrôle
     */
    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    /**
     * Obtient le coefficient du contrôle.
     * @return Le coefficient du contrôle
     */
    @Column(name = "coefficient", nullable = false)
    public int getCoefficient() {
        return coefficient;
    }

    /**
     * Définit le coefficient du contrôle.
     * @param coefficient Le coefficient du contrôle
     */
    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    /**
     * Obtient le type de contrôle (DS, Oral, TP).
     * @return Le type de contrôle
     */
    @Column(name = "type", nullable = false)
    public String getType() {
        return type;
    }

    /**
     * Définit le type de contrôle.
     * @param type Le type de contrôle (DS, Oral, TP)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtient la note obtenue lors du contrôle.
     * @return La note du contrôle
     */
    @Column(name = "note", nullable = false)
    public float getNote() {
        return note;
    }

    /**
     * Définit la note obtenue lors du contrôle.
     * @param note La note à attribuer au contrôle
     */
    public void setNote(float note) {
        this.note = note;
    }

    /**
     * Obtient la date du contrôle.
     * @return La date du contrôle
     */
    @Column(name = "date_controle", nullable = false)
    @Temporal(TemporalType.DATE)
    public Date getDateControle() {
        return dateControle;
    }

    /**
     * Définit la date du contrôle.
     * @param dateControle La date du contrôle
     */
    public void setDateControle(Date dateControle) {
        this.dateControle = dateControle;
    }
}
