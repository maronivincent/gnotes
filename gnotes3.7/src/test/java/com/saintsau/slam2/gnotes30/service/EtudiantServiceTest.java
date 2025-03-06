package com.saintsau.slam2.gnotes30.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.saintsau.slam2.gnotes30.entity.Etudiant;
import com.saintsau.slam2.gnotes30.entity.Matiere;
import com.saintsau.slam2.gnotes30.service.EtudiantService;

@SpringBootTest
public class EtudiantServiceTest {
	
	
	@Autowired
	EtudiantService etudiantService;
	
//	// Before
//    // Dimitri
//    Etudiant dimitri = new Etudiant("Dimitri");
//    Matiere dimitrimaths = new Matiere("Maths", "Devoir ecrit", 3, dimitri);
//    dimitrimaths.setNote(12.5f);
//    Matiere dimitriphysique = new Matiere("Physique", "TP", 4, dimitri);
//    dimitriphysique.setNote(8.5f);
//    Matiere dimitrifrancais = new Matiere("Français", "Eval Orale", 2, dimitri);
//    dimitrifrancais.setNote(16f);
//    Matiere dimitrihistoire = new Matiere("Histoire", "Eval Orale", 2, dimitri);
//    dimitrihistoire.setNote(12f);
//    Matiere dimitrisvt = new Matiere("SVT", "TP", 3, dimitri);
//    dimitrisvt.setNote(10);
//
//    Set<Matiere> matieresDimitri = new HashSet<Matiere>();
//    matieresDimitri.add(dimitrimaths);
//    matieresDimitri.add(dimitriphysique);
//    matieresDimitri.add(dimitrifrancais);
//    matieresDimitri.add(dimitrihistoire);
//    matieresDimitri.add(dimitrisvt);
//
//    dimitri.setMatieres(matieresDimitri);
//
//    // jean
//    Etudiant jean = new Etudiant("jean");
//    Matiere jeanmaths = new Matiere("Maths", "Devoir ecrit", 3, jean);
//    jeanmaths.setNote(8.5f);
//    Matiere jeanphysique = new Matiere("Physique", "TP", 4, jean);
//    jeanphysique.setNote(14f);
//    Matiere jeanfrancais = new Matiere("Français", "Eval Orale", 2, jean);
//    jeanfrancais.setNote(12f);
//    Matiere jeanhistoire = new Matiere("Histoire", "Eval Orale", 2, jean);
//    jeanhistoire.setNote(16f);
//    Matiere jeansvt = new Matiere("SVT", "TP", 3, jean);
//    jeansvt.setNote(12f);
//
//    Set<Matiere> matieresJean = new HashSet<Matiere>();
//    matieresJean.add(jeanmaths);
//    matieresJean.add(jeanphysique);
//    matieresJean.add(jeanfrancais);
//    matieresJean.add(jeanhistoire);
//    matieresJean.add(jeansvt);
//
//    jean.setMatieres(matieresJean);
//
//    // Léa
//    Etudiant lea = new Etudiant("lea");
//    Matiere leamaths = new Matiere("Maths", "Devoir ecrit", 3, lea);
//    leamaths.setNote(14f);
//    Matiere leaphysique = new Matiere("Physique", "TP", 4, lea);
//    leaphysique.setNote(12f);
//    Matiere leafrancais = new Matiere("Français", "Eval Orale", 2, lea);
//    leafrancais.setNote(12f);
//    Matiere leahistoire = new Matiere("Histoire", "Eval Orale", 2, lea);
//    leahistoire.setNote(9f);
//    Matiere leasvt = new Matiere("SVT", "TP", 3, lea);
//    leasvt.setNote(16f);
//
//    Set<Matiere> matieresLea = new HashSet<Matiere>();
//    matieresLea.add(leamaths);
//    matieresLea.add(leaphysique);
//    matieresLea.add(leafrancais);
//    matieresLea.add(leahistoire);
//    matieresLea.add(leasvt);
//
//    lea.setMatieres(matieresLea);

	@BeforeEach
	
	void testInitialize() {
		etudiantService.getEtudiantRepository().deleteAll();
	}
	
	@Test
	public void testSaveEtudiant() {
		
		//Before
		Etudiant dimitri = new Etudiant("Dimitri");
		
		//Expected
		String nomDimitri = dimitri.getNom();
		
		assertThat(nomDimitri.contains(etudiantService.saveEtudiant(dimitri).getNom()));
	}
}
