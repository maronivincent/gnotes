package com.saintsau.slam2.gnotes30.service;

import com.saintsau.slam2.gnotes30.dto.ControleDTO;
import com.saintsau.slam2.gnotes30.entity.Controle;
import com.saintsau.slam2.gnotes30.entity.User; // Add this import for User
import com.saintsau.slam2.gnotes30.exeption.ControleNotFoundExeption;
import com.saintsau.slam2.gnotes30.jpaRepository.ControleRepository;
import com.saintsau.slam2.gnotes30.jpaRepository.UserRepository; // Add UserRepository
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ControlesService {

   private final ControleRepository controleRepository;
   private final UserRepository userRepository; // Inject UserRepository

   public ControleRepository getControleRepository() {
    	return controleRepository;
   }
    
   ControlesService(ControleRepository controleRepository, UserRepository userRepository) {
    	super();
    	this.controleRepository = controleRepository;
    	this.userRepository = userRepository;
   }
    
   public List<Controle> getAllControles() {
        return (List<Controle>) controleRepository.findAll();
   }
    
   public Controle getContoleById(Long id) {
	   return controleRepository.findById(id)
			   .orElseThrow(() -> new ControleNotFoundExeption(id));
   }

   // Updated saveControle method
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
	    // Attempt to delete by ID directly
	    if (!controleRepository.existsById(id)) {
	        throw new ControleNotFoundExeption(id);
	    }
	    
	    // If exists, delete the Controle
	    controleRepository.deleteById(id);
	    
	    // Return a message or an object confirming deletion (or null if no content is needed)
	    return null;  // You can return null or an object if needed
	}


   public List<ControleDTO> getAllControleDTOs() {
	    return ((Collection<Controle>) controleRepository.findAll()).stream()
	        .map(controle -> new ControleDTO(
	        	controle.getId(),
	            controle.getMatiere().getIntitule(),
	            controle.getCoefficient(),
	            controle.getType(),
	            controle.getNote(),
	            controle.getEtudiant().getNom(),
	            controle.getDateControle()
	        ))
	        .collect(Collectors.toList());
	}
}
