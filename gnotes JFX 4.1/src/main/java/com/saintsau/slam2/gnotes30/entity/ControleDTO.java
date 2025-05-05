package com.saintsau.slam2.gnotes30.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Data Transfer Object (DTO) pour un contrôle (examen, devoir, etc.).
 * Utilisé pour transférer les informations d'un contrôle entre les couches de l'application.
 */
public class ControleDTO {
    
    private Long id;              // Identifiant du contrôle
    private String intitule;      // Intitulé du contrôle
    private int coefficient;      // Coefficient du contrôle
    private String type;          // Type du contrôle (DS, Oral, TP)
    private float note;           // Note obtenue lors du contrôle
    private String nomEtudiant;   // Nom de l'étudiant ayant passé le contrôle
    private Date dateControle;    // Date du contrôle

    /**
     * Constructeur par défaut nécessaire pour Jackson (sérialisation/désérialisation JSON).
     */
    public ControleDTO() {}

    /**
     * Constructeur avec paramètres pour initialiser un objet ControleDTO avec des valeurs.
     * @param id L'identifiant du contrôle
     * @param intitule L'intitulé du contrôle
     * @param coefficient Le coefficient du contrôle
     * @param type Le type du contrôle (DS, Oral, TP)
     * @param note La note obtenue lors du contrôle
     * @param nomEtudiant Le nom de l'étudiant ayant passé le contrôle
     * @param dateControle La date du contrôle
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

    /**
     * Obtient l'identifiant du contrôle.
     * @return L'identifiant du contrôle
     */
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
     * Obtient l'intitulé du contrôle.
     * @return L'intitulé du contrôle
     */
    public String getIntitule() {
        return intitule;
    }

    /**
     * Définit l'intitulé du contrôle.
     * @param intitule L'intitulé du contrôle
     */
    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    /**
     * Obtient le coefficient du contrôle.
     * @return Le coefficient du contrôle
     */
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
     * Obtient le type du contrôle (DS, Oral, TP).
     * @return Le type du contrôle
     */
    public String getType() {
        return type;
    }

    /**
     * Définit le type du contrôle.
     * @param type Le type du contrôle (DS, Oral, TP)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtient la note obtenue lors du contrôle.
     * @return La note obtenue lors du contrôle
     */
    public float getNote() {
        return note;
    }

    /**
     * Définit la note obtenue lors du contrôle.
     * @param note La note obtenue lors du contrôle
     */
    public void setNote(float note) {
        this.note = note;
    }

    /**
     * Obtient le nom de l'étudiant ayant passé le contrôle.
     * @return Le nom de l'étudiant
     */
    public String getNomEtudiant() {
        return nomEtudiant;
    }

    /**
     * Définit le nom de l'étudiant ayant passé le contrôle.
     * @param nomEtudiant Le nom de l'étudiant
     */
    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    /**
     * Obtient la date du contrôle.
     * @return La date du contrôle
     */
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
