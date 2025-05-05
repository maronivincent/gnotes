package com.saintsau.slam2.gnotes30.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "etudiants")
public class Etudiant {
	private String nom;
	private String prenom;
	private Long numero;
	private Set<Controle> controles;

	public Etudiant(String nom, String prenom, Long numero, Set<Controle> controles) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.numero = numero;
		this.controles = controles;
	}

	public Etudiant(String nom, String prenom, Set<Controle> controles) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.controles = controles;
	}
	
	public Etudiant(String nom, String prenom) {
		super();
		this.nom = nom;
		this.prenom = prenom;
	}
	public Etudiant() {
		
	}

	@Column(name = "nom")
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Id
	@Column(name = "numero")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	@OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
	@JsonManagedReference("etudiant-controle")
	//	@JoinColumn(name = "etudiant_numero", nullable = false)
	public Set<Controle> getControles() {
		return controles;
	}

	public void setControles(Set<Controle> controles) {
		this.controles = controles;
	}
}

//	@Override
//	public String toString() {
//		return "Etudiant [nom=" + this.nom + ", numero=" + this.numero + ", moyenne=" + this.calculerMoyenne() + "]";
//	}
	
	

//	@Override
//	public int hashCode() {
//		return Objects.hash(matieres, nom, numero);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Etudiant other = (Etudiant) obj;
//		return Objects.equals(matieres, other.matieres) && Objects.equals(nom, other.nom)
//				&& Objects.equals(numero, other.numero);
//	}

//	public float calculerMoyenne() {
//		int sumCoef = 0;
//		float sumMatiere = 0;
//
//		for (Matiere matier : matieres) {
//			float notexCoef;
//			Matiere matiere = matier;
//			notexCoef = matiere.getNote() * matiere.getCoef();
//			sumCoef = sumCoef + matiere.getCoef();
//			sumMatiere = sumMatiere + notexCoef;
//		}
//		// pour avoir 2 chiffres après la virgule
//		return Math.round((sumMatiere / sumCoef) * 100) / 100;
//	}
//
//	public String afficherMatieres() {
//		StringBuilder phrase = new StringBuilder();
//		phrase.append("Matières suivies par ");
//		phrase.append(this.nom);
//		phrase.append(" :");
//		/*
//		 * Ou une autre manière simple (mais au niveau performance StringBuilder est
//		 * très rapide) String pharse2 = "Matières suivies par "; pharse2 = pharse2 +
//		 * this.nom + " :";
//		 */
//		ArrayList<String> listMatieres = new ArrayList<String>();
//
//		for (Matiere matiere : this.matieres) {
//			listMatieres.add(" -" + matiere.getIntitule() + " (" + matiere.getType() + ")");
//		}
//
//		Collections.sort(listMatieres); // N'oubliez pas d'ordonner la liste car une collection de type Set l'ordre
//										// d'affichag est toujours alléatoire
//
//		return phrase.append(listMatieres).toString();
//	}
//
//	public static ArrayList<Etudiant> majorDePromotion(List<Etudiant> etudiants) {
//		float moyenneMajor = 0;
//		ArrayList<Etudiant> majors = new ArrayList<Etudiant>();
//		for (Etudiant etudiant : etudiants) {
//			float moyenneEtudiant = etudiant.calculerMoyenne();
//
//			if (moyenneMajor < moyenneEtudiant) {
//				majors.clear();
//				majors.add(etudiant);
//			} else if (moyenneMajor == moyenneEtudiant) {
//				majors.add(etudiant);
//			}
//		}
//
//		return majors;
//	}
//}
