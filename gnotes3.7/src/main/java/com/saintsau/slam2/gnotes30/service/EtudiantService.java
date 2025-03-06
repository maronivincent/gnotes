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

	 
	 public Etudiant saveMatiereByEtudiant(Long idEtudiant, Set<Matiere>matieres) {
		 
		 Etudiant etudiant = etudiantRepository.findById(idEtudiant)
				 .orElseThrow(() -> new EtudiantNotFoundException(idEtudiant));
         
         for(Matiere matiere : matieres){
                 matiere.setEtudiant(etudiant);
         
         etudiant.setMatieres(matieres);
         
         
         }
         return etudiantRepository.save(etudiant);
	}
         
	 public Set<Matiere> findAllMatiereByEtudiant(long idEtudiant){
         Etudiant etudiant = etudiantRepository.findById(idEtudiant)
        		 .orElseThrow(() -> new EtudiantNotFoundException(idEtudiant));
         return etudiant.getMatieres();
	 }
	 
     public Matiere findMatiereById(long idEtudiant, long idMatiere) {
         Etudiant etudiant = etudiantRepository.findById(idEtudiant)
        		 .orElseThrow(() -> new EtudiantNotFoundException(idEtudiant));
         Set<Matiere> matieres = etudiant.getMatieres();
         Matiere matiereWithId = null;
         for(Matiere matiere : matieres){
                 if(matiere.getId() == idMatiere) {
                         matiereWithId = matiere;
                         return matiereWithId;
                 }
         }
         throw new MatiereNotFoundException(idMatiere);
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
     
     public Etudiant updateMatiere(Long idEtudiant, Long idMatiere, Matiere updatedMatiere) {
         Etudiant etudiant = etudiantRepository.findById(idEtudiant)
             .orElseThrow(() -> new EtudiantNotFoundException(idEtudiant));
         
         // Find the specific Matiere to update
         Matiere matiereToUpdate = null;
         for (Matiere matiere : etudiant.getMatieres()) {
             if (matiere.getId() == idMatiere) {
                 matiereToUpdate = matiere;
                 break;
             }
         }
         
         if (matiereToUpdate == null) {
             throw new MatiereNotFoundException(idMatiere);
         }

         // Update fields of the Matiere
         	matiereToUpdate.setIntitule(updatedMatiere.getIntitule());
         	matiereToUpdate.setType(updatedMatiere.getType());
         	matiereToUpdate.setCoef(updatedMatiere.getCoef());
         	matiereToUpdate.setNote(updatedMatiere.getNote());
         // Add other fields to update as necessary
         
         return etudiantRepository.save(etudiant);
     }
     
     public Etudiant deleteMatiere(Long idEtudiant, Long idMatiere) {
         Etudiant etudiant = etudiantRepository.findById(idEtudiant)
             .orElseThrow(() -> new EtudiantNotFoundException(idEtudiant));
         
         Matiere matiereToRemove = null;
         for (Matiere matiere : etudiant.getMatieres()) {
             if (matiere.getId() == idMatiere) {
                 matiereToRemove = matiere;
                 break;
             }
         }
         
         if (matiereToRemove == null) {
             throw new MatiereNotFoundException(idMatiere);
         }
         
         etudiant.getMatieres().remove(matiereToRemove); // Remove Matiere from Etudiant's set
         return etudiantRepository.save(etudiant);
     }
}
