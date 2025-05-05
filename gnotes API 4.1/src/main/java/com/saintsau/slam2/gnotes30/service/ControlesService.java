package com.saintsau.slam2.gnotes30.service;

import com.saintsau.slam2.gnotes30.dto.ControleDTO;
import com.saintsau.slam2.gnotes30.entity.Controle;
import com.saintsau.slam2.gnotes30.entity.User; // Import pour User
import com.saintsau.slam2.gnotes30.exeption.ControleNotFoundExeption;
import com.saintsau.slam2.gnotes30.jpaRepository.ControleRepository;
import com.saintsau.slam2.gnotes30.jpaRepository.UserRepository; // Import pour UserRepository
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour gérer les opérations liées aux contrôles.
 * Cette classe permet de récupérer, ajouter, mettre à jour et supprimer des contrôles.
 */
@Service
public class ControlesService {

   private final ControleRepository controleRepository; // Repository pour accéder aux données de Controle
   private final UserRepository userRepository; // Repository pour accéder aux utilisateurs

   /**
    * Constructeur pour injecter les dépendances.
    *
    * @param controleRepository Repository pour les contrôles
    * @param userRepository Repository pour les utilisateurs
    */
   ControlesService(ControleRepository controleRepository, UserRepository userRepository) {
    	super();
    	this.controleRepository = controleRepository;
    	this.userRepository = userRepository;
   }

   /**
    * Récupère tous les contrôles.
    * 
    * @return Liste de tous les contrôles
    */
   public List<Controle> getAllControles() {
        return (List<Controle>) controleRepository.findAll();
   }
    
   /**
    * Récupère un contrôle par son ID.
    * Lève une exception si le contrôle n'est pas trouvé.
    *
    * @param id L'ID du contrôle à récupérer
    * @return Le contrôle correspondant à l'ID
    * @throws ControleNotFoundExeption Si aucun contrôle n'est trouvé avec cet ID
    */
   public Controle getContoleById(Long id) {
	   return controleRepository.findById(id)
			   .orElseThrow(() -> new ControleNotFoundExeption(id));
   }

   /**
    * Sauvegarde un nouveau contrôle dans la base de données.
    * 
    * @param controle Le contrôle à sauvegarder
    * @return Le contrôle enregistré
    */
   public Controle saveControle(Controle controle) {
       return controleRepository.save(controle);
   }

   /**
    * Met à jour un contrôle existant avec de nouvelles valeurs.
    * Lève une exception si le contrôle à mettre à jour n'existe pas.
    * 
    * @param id L'ID du contrôle à mettre à jour
    * @param updatedControle Le contrôle avec les nouvelles informations
    * @return Le contrôle mis à jour
    * @throws ControleNotFoundExeption Si le contrôle n'existe pas
    */
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

   /**
    * Supprime un contrôle par son ID.
    * Lève une exception si le contrôle n'existe pas.
    * 
    * @param id L'ID du contrôle à supprimer
    * @return Null si le contrôle est supprimé avec succès
    * @throws ControleNotFoundExeption Si le contrôle n'existe pas
    */
   public Controle deleteControle(Long id) {
	    if (!controleRepository.existsById(id)) {
	        throw new ControleNotFoundExeption(id);
	    }
	    
	    controleRepository.deleteById(id);
	    return null;  // Retourne null après la suppression
	}

   /**
    * Récupère tous les contrôles et les transforme en objets ControleDTO.
    * Cela permet de ne renvoyer que les informations essentielles.
    * 
    * @return Liste des DTOs (ControleDTO) représentant les contrôles
    */
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
