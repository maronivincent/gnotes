package com.saintsau.slam2.gnotes30.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saintsau.slam2.gnotes30.entity.Controle;
import com.saintsau.slam2.gnotes30.service.ControlesService;
import com.saintsau.slam2.gnotes30.dto.ControleDTO;


@RestController
/**
 * Contrôleur pour gérer les opérations CRUD sur les contrôles (Controles).
 * Fournit des endpoints pour ajouter, récupérer, modifier et supprimer des contrôles.
 */
public class ControlesController {
    
    private final ControlesService controleService;

    /**
     * Constructeur pour initialiser le service de contrôles.
     * @param controleService Service de gestion des contrôles.
     */
    ControlesController(ControlesService controleService) {
        this.controleService = controleService;
    }

    /**
     * Crée un nouveau contrôle et l'ajoute à un étudiant.
     * @param controle L'objet contrôle à ajouter.
     * @return L'entité contrôle ajoutée avec des liens HATEOAS.
     */
    @PostMapping("/controles")
    public EntityModel<Controle> newControle(@RequestBody Controle controle) {
        Controle savedControle = controleService.saveControle(controle);
        
        return EntityModel.of(savedControle,
                linkTo(methodOn(ControlesController.class).getControleById(savedControle.getId())).withSelfRel(),
                linkTo(methodOn(ControlesController.class).all()).withRel("controles"));
    }

    /**
     * Récupère la liste de tous les contrôles.
     * @return Collection des entités contrôle avec des liens HATEOAS.
     */
    @GetMapping("/controles")
    CollectionModel<EntityModel<Controle>> all() {
        List<EntityModel<Controle>> controles = StreamSupport.stream(controleService.getAllControles().spliterator(), false)
                .map((Controle controle) -> EntityModel.of(controle,
                        linkTo(methodOn(ControlesController.class).getControleById(controle.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(controles,
                linkTo(methodOn(ControlesController.class).all()).withSelfRel());
    }

    /**
     * Récupère la liste de tous les contrôles sous forme de DTO.
     * @return Liste des DTO de contrôle.
     */
    @GetMapping("/controles/dto")
    public List<ControleDTO> getAllControleDTOs() {
        return controleService.getAllControleDTOs();
    }

    /**
     * Récupère un contrôle spécifique par son ID.
     * @param id L'ID du contrôle à récupérer.
     * @return L'entité contrôle avec des liens HATEOAS.
     */
    @GetMapping("/controles/{id}")
    EntityModel<Controle> getControleById(@PathVariable Long id) {
        Controle controle = controleService.getContoleById(id);
        
        return EntityModel.of(controle,
                linkTo(methodOn(ControlesController.class).getControleById(id)).withSelfRel(),
                linkTo(methodOn(ControlesController.class).all()).withRel("controles"));
    }

    /**
     * Met à jour un contrôle existant par son ID.
     * @param id L'ID du contrôle à modifier.
     * @param updatedControle L'objet contrôle mis à jour.
     * @return L'entité contrôle mise à jour avec des liens HATEOAS.
     */
    @PutMapping("/controles/{id}")
    public EntityModel<Controle> updateControle(@PathVariable Long id, @RequestBody Controle updatedControle) {
        Controle controle = controleService.updateControle(id, updatedControle);

        return EntityModel.of(controle,
                linkTo(methodOn(ControlesController.class).getControleById(controle.getId())).withSelfRel(),
                linkTo(methodOn(ControlesController.class).all()).withRel("controles"));
    }

    /**
     * Supprime un contrôle par son ID.
     * @param id L'ID du contrôle à supprimer.
     * @return Une réponse contenant un message de confirmation et le contrôle supprimé avec des liens HATEOAS.
     */
    @DeleteMapping("/controles/{id}")
    EntityModel<Map<String, Object>> deleteControle(@PathVariable Long id) {
        Controle deletedControle = controleService.deleteControle(id);
        
        String message = "Controle supprimé: ";
        
        Map<String, Object> reponse = new HashMap<>();
        reponse.put("message", message);
        reponse.put("controle", deletedControle);
        
        return EntityModel.of(reponse,
                linkTo(methodOn(ControlesController.class).all()).withRel("controles"));
    }
}
