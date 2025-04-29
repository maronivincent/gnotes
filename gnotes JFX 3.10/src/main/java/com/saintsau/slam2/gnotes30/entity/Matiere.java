package com.saintsau.slam2.gnotes30.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "matieres")
public class Matiere {

    private Long id;            // Clé primaire
    private String intitule;    // Intitulé de la matière
  
    private Set<Controle> controles;

    // Constructeurs
    public Matiere(String intitule) {
        this.intitule = intitule;
        
    }

    public Matiere() {
    }

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

    @Column(name = "intitule")
    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    @OneToMany(mappedBy = "matiere", cascade = CascadeType.ALL)
    @JsonManagedReference("matiere-controle")
    //@JoinColumn(name = "controle_id") // Clé étrangère vers l'entité Controle
    public Set<Controle> getControle() {
        return controles;
    }

    public void setControle(Set<Controle> controle) {
        this.controles = controle;
    }

}
