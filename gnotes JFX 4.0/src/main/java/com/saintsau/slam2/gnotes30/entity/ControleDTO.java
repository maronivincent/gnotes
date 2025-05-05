package com.saintsau.slam2.gnotes30.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ControleDTO {
    private Long id;
    private String intitule;
    private int coefficient;
    private String type;
    private float note;
    private String nomEtudiant;
    private Date dateControle;  // Change to Date type
    
    // Default constructor required for Jackson
    public ControleDTO() {}

    public ControleDTO(Long id, String intitule, int coefficient, String type, float note, String nomEtudiant, Date dateControle) {
        this.id = id;
        this.intitule = intitule;
        this.coefficient = coefficient;
        this.type = type;
        this.note = note;
        this.nomEtudiant = nomEtudiant;
        this.dateControle = dateControle;
    }

    // Getters & setters for Date
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }

    public int getCoefficient() { return coefficient; }
    public void setCoefficient(int coefficient) { this.coefficient = coefficient; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public float getNote() { return note; }
    public void setNote(float note) { this.note = note; }

    public String getNomEtudiant() { return nomEtudiant; }
    public void setNomEtudiant(String nomEtudiant) { this.nomEtudiant = nomEtudiant; }

    public Date getDateControle() { return dateControle; }
    public void setDateControle(Date dateControle) { this.dateControle = dateControle; }
}
