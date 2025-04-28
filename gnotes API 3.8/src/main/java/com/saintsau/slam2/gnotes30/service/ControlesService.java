package com.saintsau.slam2.gnotes30.service;

import com.saintsau.slam2.gnotes30.entity.Controle;
import com.saintsau.slam2.gnotes30.entity.Etudiant;
import com.saintsau.slam2.gnotes30.entity.Matiere;
import com.saintsau.slam2.gnotes30.exeption.ControleNotFoundExeption;
import com.saintsau.slam2.gnotes30.exeption.EtudiantNotFoundException;
import com.saintsau.slam2.gnotes30.exeption.MatiereNotFoundException;
import com.saintsau.slam2.gnotes30.jpaRepository.ControleRepository;
import com.saintsau.slam2.gnotes30.jpaRepository.EtudiantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ControlesService {

   private final ControleRepository controleRepository;

   public ControleRepository getControleRepository() {
    	return controleRepository;
    	
   }
    
   ControlesService(ControleRepository controleRepository){
    	super();
    	this.controleRepository = controleRepository;
    	
   }
    
   
   public List<Controle> getAllControles() {
        return (List<Controle>) controleRepository.findAll();
   }
    
   public Controle getContoleById(Long id) {
	   return controleRepository.findById(id)
			   .orElseThrow(() -> new ControleNotFoundExeption(id));
   }
   
   // POST: Create a new Matiere
   public Controle saveControle(Controle controle) {
       return controleRepository.save(controle);
   }
   
   public Controle updateControle(Long id, Controle updatedControle) {
	   Controle controle = controleRepository.findById(id)
			   .orElseThrow(() -> new ControleNotFoundExeption(id));
	   controle.setEtudiant(updatedControle.getEtudiant());
       controle.setProfesseur(updatedControle.getProfesseur());
       controle.setMatiere(updatedControle.getMatiere());
       controle.setCoefficient(updatedControle.getCoefficient());
       controle.setType(updatedControle.getType());
       controle.setNote(updatedControle.getNote());
       controle.setDateControle(updatedControle.getDateControle());
       
       return controleRepository.save(controle);
   }
   
   public Controle deleteControle(Long id) {
	   Controle controle = controleRepository.findById(id)
			   .orElseThrow(() -> new ControleNotFoundExeption(id));
	   controleRepository.deleteById(id);
	   
	   return controle;
   }
}

