package com.saintsau.slam2.gnotes3.dto;

public class MatiereDTO {
    private int id;
    private String intitule;
    private String type;
    private float note;
    private float coefficient;

    public MatiereDTO(int id, String intitule, String type, float note, float coefficient) {
        this.id = id;
        this.intitule = intitule;
        this.type = type;
        this.note = note;
        this.coefficient = coefficient;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
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

    public float getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }
}
