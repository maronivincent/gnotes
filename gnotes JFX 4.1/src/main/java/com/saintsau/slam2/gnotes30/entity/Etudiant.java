package com.saintsau.slam2.gnotes30.entity;

import java.util.Set;

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

/**
 * Représente un étudiant dans l'application. Un étudiant peut avoir plusieurs contrôles associés à lui.
 * Cette classe est mappée à la table "etudiants" dans la base de données.
 * Elle permet de gérer les informations d'un étudiant, telles que son nom, son prénom,
 * son numéro d'identification, ainsi que les contrôles qui lui sont associés.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "etudiants")
public class Etudiant {

    /**
     * Le nom de l'étudiant.
     */
    private String nom;

    /**
     * Le prénom de l'étudiant.
     */
    private String prenom;

    /**
     * Le numéro d'identification unique de l'étudiant.
     */
    private Long numero;

    /**
     * L'ensemble des contrôles associés à cet étudiant.
     */
    private Set<Controle> controles;

    /**
     * Constructeur complet pour initialiser tous les attributs de l'étudiant.
     * 
     * @param nom       Le nom de l'étudiant
     * @param prenom    Le prénom de l'étudiant
     * @param numero    Le numéro d'identification de l'étudiant
     * @param controles L'ensemble des contrôles associés à cet étudiant
     */
    public Etudiant(String nom, String prenom, Long numero, Set<Controle> controles) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.controles = controles;
    }

    /**
     * Constructeur pour initialiser l'étudiant sans son numéro d'identification.
     * 
     * @param nom       Le nom de l'étudiant
     * @param prenom    Le prénom de l'étudiant
     * @param controles L'ensemble des contrôles associés à cet étudiant
     */
    public Etudiant(String nom, String prenom, Set<Controle> controles) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.controles = controles;
    }

    /**
     * Constructeur par défaut sans paramètres, utile pour les frameworks comme JPA et Jackson.
     */
    public Etudiant() {
    }

    /**
     * Getter pour le nom de l'étudiant.
     * 
     * @return Le nom de l'étudiant
     */
    @Column(name = "nom")
    public String getNom() {
        return nom;
    }

    /**
     * Setter pour le nom de l'étudiant.
     * 
     * @param nom Le nom de l'étudiant
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter pour le prénom de l'étudiant.
     * 
     * @return Le prénom de l'étudiant
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Setter pour le prénom de l'étudiant.
     * 
     * @param prenom Le prénom de l'étudiant
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Getter pour le numéro d'identification de l'étudiant.
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
     * Setter pour le numéro d'identification de l'étudiant.
     * 
     * @param numero Le numéro d'identification de l'étudiant
     */
    public void setNumero(Long numero) {
        this.numero = numero;
    }

    /**
     * Getter pour l'ensemble des contrôles associés à cet étudiant.
     * 
     * @return L'ensemble des contrôles
     */
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    @JsonManagedReference("etudiant-controle")
    public Set<Controle> getControles() {
        return controles;
    }

    /**
     * Setter pour l'ensemble des contrôles associés à cet étudiant.
     * 
     * @param controles L'ensemble des contrôles
     */
    public void setControles(Set<Controle> controles) {
        this.controles = controles;
    }

    /**
     * Méthode toString qui retourne le prénom et le nom de l'étudiant sous forme de chaîne.
     * 
     * @return Le prénom et le nom de l'étudiant
     */
    @Override
    public String toString() {
        return prenom + " " + nom;
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
