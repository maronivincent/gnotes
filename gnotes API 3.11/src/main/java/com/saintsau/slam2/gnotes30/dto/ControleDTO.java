package com.saintsau.slam2.gnotes30.dto;

import java.util.Date;

public class ControleDTO {
	private Long id;
    private String intitule;        // from Matiere
    private int coefficient;
    private String type;
    private float note;
    private String nomEtudiant;     // from Etudiant
    private Date dateControle;      // from Controle

    // Constructors
    public ControleDTO() {}

    public ControleDTO(Long id, String intitule, int coefficient, String type, float note, String nomEtudiant, Date dateControle) {
        this.intitule = intitule;
        this.coefficient = coefficient;
        this.type = type;
        this.note = note;
        this.nomEtudiant = nomEtudiant;
        this.dateControle = dateControle;
    }

    // Getters and Setters
    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public String getNomEtudiant() {
        return nomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    public Date getDateControle() {
        return dateControle;
    }

    public void setDateControle(Date dateControle) {
        this.dateControle = dateControle;
    }
}
