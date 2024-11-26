package com.saintsau.slam2.gnotes3.dto;

public class EtudiantDTO {
    private int numero;
    private String nom;

    public EtudiantDTO(int numero, String nom) {
        this.numero = numero;
        this.nom = nom;
    }

    // Getters and setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
