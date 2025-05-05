package com.saintsau.slam2.gnotes30.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.saintsau.slam2.gnotes30.entity.Etudiant;
import com.saintsau.slam2.gnotes30.entity.Matiere;
import com.saintsau.slam2.gnotes30.exeption.EtudiantNotFoundException;
import com.saintsau.slam2.gnotes30.exeption.MatiereNotFoundException;
import com.saintsau.slam2.gnotes30.jpaRepository.EtudiantRepository;



@Service
public class EtudiantService {
	
	 private final EtudiantRepository etudiantRepository;

	 public EtudiantRepository getEtudiantRepository() {
		return etudiantRepository;
	}

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
		    return etudiantRepository.findById(id)
		            .orElseThrow(() -> new EtudiantNotFoundException(id));
		}

     public Etudiant deleteById(Long id) {
    	    // Retrieve the Etudiant to be deleted
    	    Etudiant etudiant = etudiantRepository.findById(id)
    	    		.orElseThrow(() -> new EtudiantNotFoundException(id));
    	    
    	    // Delete the Etudiant
    	    etudiantRepository.deleteById(id);
    	    
    	    // Return the deleted Etudiant
    	    return etudiant;
    	}
     
     public Etudiant updateEtudiant(Long id, Etudiant updatedEtudiant) {
         Etudiant etudiant = etudiantRepository.findById(id)
             .orElseThrow(() -> new EtudiantNotFoundException(id));
         
         // Update fields as needed (e.g., name, age, etc.)
         etudiant.setNom(updatedEtudiant.getNom());
      
         return etudiantRepository.save(etudiant);
     }
}
