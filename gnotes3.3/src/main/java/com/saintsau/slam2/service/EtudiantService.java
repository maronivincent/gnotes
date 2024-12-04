package com.saintsau.slam2.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.saintsau.slam2.entity.Etudiant;
import com.saintsau.slam2.entity.Matiere;
import com.saintsau.slam2.jpaRepository.EtudiantRepository;

@Service
public class EtudiantService {
	
	 private final EtudiantRepository etudiantRepository;

	 EtudiantService(EtudiantRepository etudiantRepository){
		 super();
		 this.etudiantRepository = etudiantRepository;
	 }
	 
	 public Etudiant saveEtudiant(Etudiant etudiant) {
		
		return etudiantRepository.save(etudiant); 
	 }
	 
	 public List<Etudiant> findAllEtudiant() {
			
			return (List<Etudiant>) etudiantRepository.findAll(); 
		 }
	 
	 public Etudiant findEtudiantById(Long id) {
			
			return etudiantRepository.findById(id).get(); 
		 }
	 
	 public Etudiant saveMatiereByEtudiant(Long idEtudiant, Set<Matiere>matieres) {
		 
		 Etudiant etudiant = etudiantRepository.findById(idEtudiant).get();
         
         for(Matiere matiere : matieres){
                 matiere.setEtudiant(etudiant);
         
         etudiant.setMatieres(matieres);
         
         
         }
         return etudiantRepository.save(etudiant);
	}
         
	 public Set<Matiere> findAllMatiereByEtudiant(long idEtudiant){
         Etudiant etudiant = etudiantRepository.findById(idEtudiant).get();
         return etudiant.getMatieres();
	 }
	 
     public Matiere findMatiereById(long idEtudiant, long idMatiere) {
         Etudiant etudiant = etudiantRepository.findById(idEtudiant).get();
         Set<Matiere> matieres = etudiant.getMatieres();
         Matiere matiereWithId = null;
         for(Matiere matiere : matieres){
                 if(matiere.getId() == idMatiere) {
                         matiereWithId = matiere;
                         break;
                 }
         }
         return matiereWithId;
 }


}
