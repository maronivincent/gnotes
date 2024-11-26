package com.saintsau.slam2.gnotes3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.saintsau.slam2.gnotes3.Etudiant;
import com.saintsau.slam2.gnotes3.Matiere;

class EtudiantTest {

	@Test
	void testAjouterMatiere() {
		// Before
		HashSet<Matiere> matieres = new HashSet<Matiere>();
		Etudiant jean = new Etudiant("Jean", 1, matieres);
		Matiere info = new Matiere("Informatique", 12);
		float note = 17f;

		// Methode à tester ajouterMatiere()
		jean.ajouterMatiere(info, note);

		// Assert
		assertEquals(true, jean.getMatieres().contains(info));
		assertEquals(17, jean.getMatieres().iterator().next().getNote()); // pour avoir le premier élément dans une
																			// collection Set : il faut passer par
																			// .iterator().next(). Il suffit de laisser
																			// aider par Eclipse
	}

	@Test
	void testCalculerMoyenne() {

	}

	@Test
	void testAfficherMatieres() {
		// Before
		HashSet<Matiere> matieres = new HashSet<Matiere>();
		Matiere jeanAnglais = new Matiere("Anglais", 2, "Oral");
		matieres.add(jeanAnglais);
		Matiere jeanMaths = new Matiere("Maths", 3, "DS");
		matieres.add(jeanMaths);
		Matiere jeanInformatiques = new Matiere("Informatiques", 3, "TP");
		matieres.add(jeanInformatiques);

		Etudiant jean = new Etudiant("Jean", 1, matieres);

		// Méthode à tester afficherMatieres()
		String atester = jean.afficherMatieres();

		// Assert
		assertEquals("Matières suivies par Jean :[ -Anglais (Oral),  -Informatiques (TP),  -Maths (DS)]", atester);

	}

	@Test
	void testMajorDePromotion() {
		// Before
		// Dimitri
		Matiere dimitrimaths = new Matiere("Maths", 3);
		dimitrimaths.setNote(12.5f);
		Matiere dimitriphysique = new Matiere("Physique", 4);
		dimitriphysique.setNote(8.5f);
		Matiere dimitrifrancais = new Matiere("Français", 2);
		dimitrifrancais.setNote(16f);
		Matiere dimitrihistoire = new Matiere("Histoire", 2);
		dimitrihistoire.setNote(12f);
		Matiere dimitrisvt = new Matiere("SVT", 3);
		dimitrisvt.setNote(10);

		Set<Matiere> matieresDimitri = new HashSet<Matiere>();
		matieresDimitri.add(dimitrimaths);
		matieresDimitri.add(dimitriphysique);
		matieresDimitri.add(dimitrifrancais);
		matieresDimitri.add(dimitrihistoire);
		matieresDimitri.add(dimitrisvt);

		Etudiant dimitri = new Etudiant("Dimitri", 1, matieresDimitri);

		// jean
		Matiere jeanmaths = new Matiere("Maths", 3);
		jeanmaths.setNote(8.5f);
		Matiere jeanphysique = new Matiere("Physique", 4);
		jeanphysique.setNote(14f);
		Matiere jeanfrancais = new Matiere("Français", 2);
		jeanfrancais.setNote(12f);
		Matiere jeanhistoire = new Matiere("Histoire", 2);
		jeanhistoire.setNote(16f);
		Matiere jeansvt = new Matiere("SVT", 3);
		jeansvt.setNote(12f);

		Set<Matiere> matieresJean = new HashSet<Matiere>();
		matieresJean.add(jeanmaths);
		matieresJean.add(jeanphysique);
		matieresJean.add(jeanfrancais);
		matieresJean.add(jeanhistoire);
		matieresJean.add(jeansvt);

		Etudiant jean = new Etudiant("jean", 1, matieresJean);

		// Léa
		Matiere leamaths = new Matiere("Maths", 3);
		leamaths.setNote(14f);
		Matiere leaphysique = new Matiere("Physique", 4);
		leaphysique.setNote(12f);
		Matiere leafrancais = new Matiere("Français", 2);
		leafrancais.setNote(12f);
		Matiere leahistoire = new Matiere("Histoire", 2);
		leahistoire.setNote(9f);
		Matiere leasvt = new Matiere("SVT", 3);
		leasvt.setNote(16f);

		Set<Matiere> matieresLea = new HashSet<Matiere>();
		matieresLea.add(leamaths);
		matieresLea.add(leaphysique);
		matieresLea.add(leafrancais);
		matieresLea.add(leahistoire);
		matieresLea.add(leasvt);

		Etudiant lea = new Etudiant("lea", 1, matieresLea);

		// Methode à tester majorDePromotion()
		ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
		etudiants.add(dimitri);
		etudiants.add(lea);
		etudiants.add(jean);
		ArrayList<Etudiant> majors = Etudiant.majorDePromotion(etudiants);

//		System.out.println(majors.toString());
		// expect
		ArrayList<Etudiant> expected = new ArrayList<Etudiant>();
		expected.add(jean);

		// Assert
		assertEquals(expected.toString(), majors.toString());

	}

}
