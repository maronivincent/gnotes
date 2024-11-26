package com.saintsau.slam2.gnotes3;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "matieres")
public class Matiere {
	private int id;
	private String intitule;
	private float note;
	private int coef;
	private String type;

	private Etudiant etudiant;

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Matiere(String intitule, int coef) {
		super();
		this.intitule = intitule;
		this.coef = coef;
	}
	
	public Matiere(String intitule, int coef, Etudiant etudiant) {
		super();
		this.intitule = intitule;
		this.coef = coef;
		this.etudiant = etudiant;
	}
	
	public Matiere(String intitule,String type, int coef, Etudiant etudiant) {
		super();
		this.intitule = intitule;
		this.type = type;
		this.coef = coef;
		this.etudiant = etudiant;
	}

	public Matiere(String intitule, int coef, String type) {
		super();
		this.intitule = intitule;
		this.coef = coef;
		this.type = type;
	}
	
	public Matiere() {
		
	}

	@Column(name = "intitule")
	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	@Column(name = "note")
	public float getNote() {
		return note;
	}

	public void setNote(float note) {
		this.note = note;
	}

	@Column(name = "coefficient")
	public int getCoef() {
		return coef;
	}

	public void setCoef(int coef) {
		this.coef = coef;
	}

	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "etudiant_numero")
	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	@Override
	public String toString() {
		return "La Matiere " + intitule + " de type " + type + " est de coef= " + coef;
	}

}
