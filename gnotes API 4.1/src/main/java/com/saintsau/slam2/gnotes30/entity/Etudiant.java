package com.saintsau.slam2.gnotes30.entity;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * Représente un étudiant dans l'application.
 * Chaque étudiant possède un nom, un prénom, un numéro d'identification et un ensemble de contrôles qu'il a passés.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "etudiants")
public class Etudiant {

    private String nom;              // Nom de l'étudiant
    private String prenom;           // Prénom de l'étudiant
    private Long numero;             // Numéro unique d'identification de l'étudiant
    private Set<Controle> controles; // Ensemble des contrôles passés par l'étudiant

    /**
     * Constructeur complet pour initialiser un étudiant avec tous les attributs.
     * 
     * @param nom Nom de l'étudiant
     * @param prenom Prénom de l'étudiant
     * @param numero Numéro d'identification de l'étudiant
     * @param controles Ensemble de contrôles associés à l'étudiant
     */
    public Etudiant(String nom, String prenom, Long numero, Set<Controle> controles) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.controles = controles;
    }

    /**
     * Constructeur pour initialiser un étudiant avec son nom, prénom et ses contrôles.
     * 
     * @param nom Nom de l'étudiant
     * @param prenom Prénom de l'étudiant
     * @param controles Ensemble de contrôles associés à l'étudiant
     */
    public Etudiant(String nom, String prenom, Set<Controle> controles) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.controles = controles;
    }

    /**
     * Constructeur par défaut pour créer un objet d'étudiant sans valeurs initiales.
     */
    public Etudiant() {}

    /**
     * Récupère le nom de l'étudiant.
     * 
     * @return Le nom de l'étudiant
     */
    @Column(name = "nom")
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de l'étudiant.
     * 
     * @param nom Le nom de l'étudiant
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Récupère le prénom de l'étudiant.
     * 
     * @return Le prénom de l'étudiant
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Définit le prénom de l'étudiant.
     * 
     * @param prenom Le prénom de l'étudiant
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Récupère le numéro unique d'identification de l'étudiant.
     * 
     * @return Le numéro d'identification de l'étudiant
     */
    @Id
    @Column(name = "numero")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getNumero() {
        return numero;
    }

    /**
     * Définit le numéro d'identification de l'étudiant.
     * 
     * @param numero Le numéro d'identification de l'étudiant
     */
    public void setNumero(Long numero) {
        this.numero = numero;
    }

    /**
     * Récupère l'ensemble des contrôles associés à l'étudiant.
     * 
     * @return L'ensemble des contrôles associés à l'étudiant
     */
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    @JsonManagedReference("etudiant-controle")
    public Set<Controle> getControles() {
        return controles;
    }

    /**
     * Définit l'ensemble des contrôles associés à l'étudiant.
     * 
     * @param controles L'ensemble des contrôles associés à l'étudiant
     */
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
