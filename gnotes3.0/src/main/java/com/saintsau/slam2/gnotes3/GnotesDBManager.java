package com.saintsau.slam2.gnotes3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class GnotesDBManager {
	public void initialize() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("GnotesDB");
		EntityManager entityManager = factory.createEntityManager();

		entityManager.getTransaction().begin();
		// requête JPQL pour récupérer tous les étudiants dans la BD
		String jpql = "Select etudiant from Etudiant etudiant";
		Query query = entityManager.createQuery(jpql, Etudiant.class);

		// remove all etudiants
		@SuppressWarnings("unchecked")
		List<Etudiant> listEtudiants = (ArrayList<Etudiant>) (query.getResultList());
		for (Etudiant eutdiant : listEtudiants) {
			entityManager.remove(eutdiant);
		}

		// Cette requête depend très fortement de MySQL. A remplacer
		entityManager.createNativeQuery("ALTER TABLE etudiants AUTO_INCREMENT = 1").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE matieres AUTO_INCREMENT = 1").executeUpdate();

		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}

	public void insertDataInDB() {
		// Before
		// Dimitri
		Etudiant dimitri = new Etudiant("Dimitri");
		Matiere dimitrimaths = new Matiere("Maths", "Devoir ecrit", 3, dimitri);
		dimitrimaths.setNote(12.5f);
		Matiere dimitriphysique = new Matiere("Physique", "TP", 4, dimitri);
		dimitriphysique.setNote(8.5f);
		Matiere dimitrifrancais = new Matiere("Français", "Eval Orale", 2, dimitri);
		dimitrifrancais.setNote(16f);
		Matiere dimitrihistoire = new Matiere("Histoire", "Eval Orale", 2, dimitri);
		dimitrihistoire.setNote(12f);
		Matiere dimitrisvt = new Matiere("SVT", "TP", 3, dimitri);
		dimitrisvt.setNote(10);

		Set<Matiere> matieresDimitri = new HashSet<Matiere>();
		matieresDimitri.add(dimitrimaths);
		matieresDimitri.add(dimitriphysique);
		matieresDimitri.add(dimitrifrancais);
		matieresDimitri.add(dimitrihistoire);
		matieresDimitri.add(dimitrisvt);

		dimitri.setMatieres(matieresDimitri);

		// jean
		Etudiant jean = new Etudiant("jean");
		Matiere jeanmaths = new Matiere("Maths", "Devoir ecrit", 3, jean);
		jeanmaths.setNote(8.5f);
		Matiere jeanphysique = new Matiere("Physique", "TP", 4, jean);
		jeanphysique.setNote(14f);
		Matiere jeanfrancais = new Matiere("Français", "Eval Orale", 2, jean);
		jeanfrancais.setNote(12f);
		Matiere jeanhistoire = new Matiere("Histoire", "Eval Orale", 2, jean);
		jeanhistoire.setNote(16f);
		Matiere jeansvt = new Matiere("SVT", "TP", 3, jean);
		jeansvt.setNote(12f);

		Set<Matiere> matieresJean = new HashSet<Matiere>();
		matieresJean.add(jeanmaths);
		matieresJean.add(jeanphysique);
		matieresJean.add(jeanfrancais);
		matieresJean.add(jeanhistoire);
		matieresJean.add(jeansvt);

		jean.setMatieres(matieresJean);

		// Léa
		Etudiant lea = new Etudiant("lea");
		Matiere leamaths = new Matiere("Maths", "Devoir ecrit", 3, lea);
		leamaths.setNote(14f);
		Matiere leaphysique = new Matiere("Physique", "TP", 4, lea);
		leaphysique.setNote(12f);
		Matiere leafrancais = new Matiere("Français", "Eval Orale", 2, lea);
		leafrancais.setNote(12f);
		Matiere leahistoire = new Matiere("Histoire", "Eval Orale", 2, lea);
		leahistoire.setNote(9f);
		Matiere leasvt = new Matiere("SVT", "TP", 3, lea);
		leasvt.setNote(16f);

		Set<Matiere> matieresLea = new HashSet<Matiere>();
		matieresLea.add(leamaths);
		matieresLea.add(leaphysique);
		matieresLea.add(leafrancais);
		matieresLea.add(leahistoire);
		matieresLea.add(leasvt);

		lea.setMatieres(matieresLea);

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("GnotesDB");
		EntityManager entityManager = factory.createEntityManager();

		entityManager.getTransaction().begin();

		entityManager.persist(dimitri);
		entityManager.persist(jean);
		entityManager.persist(lea);

		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}

	public Etudiant getEtudiantInDB(int numero) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("GnotesDB");
		EntityManager entityManager = factory.createEntityManager();

		entityManager.getTransaction().begin();

		Etudiant etudiant = entityManager.find(Etudiant.class, numero);

		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();

		return etudiant;
	}
}
