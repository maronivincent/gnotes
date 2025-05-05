package com.saintsau.slam2.gnotes30.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DTO (Data Transfer Object) représentant un contrôle d'un étudiant.
 * Ce DTO est utilisé pour transférer les informations d'un contrôle spécifique dans l'application.
 */
public class ControleDTO {
    private Long id;                // Identifiant unique du contrôle
    private String intitule;        // Titre du contrôle (par exemple, "Mathématiques - Exam 1")
    private int coefficient;        // Coefficient du contrôle (par exemple, 2, 3)
    private String type;            // Type du contrôle (par exemple, "Examen", "Devoir", etc.)
    private float note;             // Note obtenue par l'étudiant
    private String nomEtudiant;     // Nom de l'étudiant ayant passé le contrôle
    private Date dateControle;      // Date à laquelle le contrôle a eu lieu

    /**
     * Constructeur par défaut requis par Jackson pour la sérialisation/désérialisation JSON.
     */
    public ControleDTO() {}

    /**
     * Constructeur de la classe ControleDTO avec tous les attributs.
     * 
     * @param id L'identifiant du contrôle
     * @param intitule Le titre du contrôle
     * @param coefficient Le coefficient du contrôle
     * @param type Le type du contrôle (par exemple "Examen")
     * @param note La note obtenue par l'étudiant
     * @param nomEtudiant Le nom de l'étudiant ayant passé le contrôle
     * @param dateControle La date à laquelle le contrôle a eu lieu
     */
    public ControleDTO(Long id, String intitule, int coefficient, String type, float note, String nomEtudiant, Date dateControle) {
        this.id = id;
        this.intitule = intitule;
        this.coefficient = coefficient;
        this.type = type;
        this.note = note;
        this.nomEtudiant = nomEtudiant;
        this.dateControle = dateControle;
    }

    // Getters et Setters

    /**
     * Récupère l'identifiant du contrôle.
     * 
     * @return L'identifiant du contrôle.
     */
    public Long getId() { return id; }

    /**
     * Modifie l'identifiant du contrôle.
     * 
     * @param id L'identifiant à attribuer au contrôle.
     */
    public void setId(Long id) { this.id = id; }
    
    /**
     * Récupère le titre du contrôle.
     * 
     * @return Le titre du contrôle.
     */
    public String getIntitule() { return intitule; }

    /**
     * Modifie le titre du contrôle.
     * 
     * @param intitule Le titre à attribuer au contrôle.
     */
    public void setIntitule(String intitule) { this.intitule = intitule; }

    /**
     * Récupère le coefficient du contrôle.
     * 
     * @return Le coefficient du contrôle.
     */
    public int getCoefficient() { return coefficient; }

    /**
     * Modifie le coefficient du contrôle.
     * 
     * @param coefficient Le coefficient à attribuer au contrôle.
     */
    public void setCoefficient(int coefficient) { this.coefficient = coefficient; }

    /**
     * Récupère le type du contrôle.
     * 
     * @return Le type du contrôle.
     */
    public String getType() { return type; }

    /**
     * Modifie le type du contrôle.
     * 
     * @param type Le type à attribuer au contrôle.
     */
    public void setType(String type) { this.type = type; }

    /**
     * Récupère la note obtenue par l'étudiant lors du contrôle.
     * 
     * @return La note obtenue.
     */
    public float getNote() { return note; }

    /**
     * Modifie la note obtenue par l'étudiant lors du contrôle.
     * 
     * @param note La note à attribuer à l'étudiant.
     */
    public void setNote(float note) { this.note = note; }

    /**
     * Récupère le nom de l'étudiant ayant passé le contrôle.
     * 
     * @return Le nom de l'étudiant.
     */
    public String getNomEtudiant() { return nomEtudiant; }

    /**
     * Modifie le nom de l'étudiant ayant passé le contrôle.
     * 
     * @param nomEtudiant Le nom à attribuer à l'étudiant.
     */
    public void setNomEtudiant(String nomEtudiant) { this.nomEtudiant = nomEtudiant; }

    /**
     * Récupère la date à laquelle le contrôle a eu lieu.
     * 
     * @return La date du contrôle.
     */
    public Date getDateControle() { return dateControle; }

    /**
     * Modifie la date à laquelle le contrôle a eu lieu.
     * 
     * @param dateControle La date à attribuer au contrôle.
     */
    public void setDateControle(Date dateControle) { this.dateControle = dateControle; }
}
