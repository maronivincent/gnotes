package com.saintsau.slam2.gnotes30.entity;

public class ControleDTO {
	private Long id; // Add this field
    private String intitule;
    private int coefficient;
    private String type;
    private float note;
    private String nomEtudiant;
    private String dateControle;

    // Getters & setters
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

    public String getDateControle() { return dateControle; }
    public void setDateControle(String dateControle) { this.dateControle = dateControle; }
}
