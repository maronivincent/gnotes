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

import com.saintsau.slam2.gnotes30.entity.Matiere;
import com.saintsau.slam2.gnotes30.service.MatiereService;

@RestController
/**
 * Contrôleur pour gérer les opérations CRUD sur les matières (Matieres).
 * Fournit des endpoints pour ajouter, récupérer, modifier et supprimer des matières.
 */
public class MatiereController {
    private final MatiereService matiereService;

    /**
     * Constructeur pour initialiser le service des matières.
     * @param matiereService Service de gestion des matières.
     */
    public MatiereController(MatiereService matiereService) {
        super();
        this.matiereService = matiereService;
    }

    /**
     * Crée une nouvelle matière et l'ajoute à la base de données.
     * @param matiere L'objet matière à ajouter.
     * @return L'entité matière ajoutée avec des liens HATEOAS.
     */
    @PostMapping("/matieres")
    EntityModel<Matiere> newMatiere(@RequestBody Matiere matiere) {
        Matiere savedMatiere = matiereService.saveMatiere(matiere);

        return EntityModel.of(savedMatiere,
                linkTo(methodOn(MatiereController.class).getMatiereById(savedMatiere.getId())).withSelfRel(),
                linkTo(methodOn(MatiereController.class).all()).withRel("matieres"));
    }

    /**
     * Récupère la liste de toutes les matières.
     * @return Une collection d'entités matière avec des liens HATEOAS.
     */
    @GetMapping("/matieres")
    CollectionModel<EntityModel<Matiere>> all() {
        List<EntityModel<Matiere>> matieres = StreamSupport.stream(matiereService.findAllMatiere().spliterator(), false)
                .map((Matiere matiere) -> EntityModel.of(matiere,
                        linkTo(methodOn(MatiereController.class).getMatiereById(matiere.getId())).withSelfRel()))    
                .collect(Collectors.toList());

        return CollectionModel.of(matieres,
                linkTo(methodOn(MatiereController.class).all()).withSelfRel());
    }

    /**
     * Récupère une matière spécifique par son ID.
     * @param id L'ID de la matière à récupérer.
     * @return L'entité matière avec des liens HATEOAS.
     */
    @GetMapping("/matieres/{id}")
    EntityModel<Matiere> getMatiereById(@PathVariable Long id) {
        Matiere matiere = matiereService.getMatiereById(id);
        
        return EntityModel.of(matiere,
                linkTo(methodOn(MatiereController.class).getMatiereById(id)).withSelfRel(),
                linkTo(methodOn(MatiereController.class).all()).withRel("matieres"));
    }

    /**
     * Met à jour une matière existante par son ID.
     * @param id L'ID de la matière à modifier.
     * @param updatedMatiere L'objet matière mis à jour.
     * @return L'entité matière mise à jour avec des liens HATEOAS.
     */
    @PutMapping("/matieres/{id}")
    public EntityModel<Matiere> updateMatiere(@PathVariable Long id, @RequestBody Matiere updatedMatiere) {
        Matiere matiere = matiereService.updateMatiere(id, updatedMatiere);

        return EntityModel.of(matiere,
                linkTo(methodOn(MatiereController.class).getMatiereById(matiere.getId())).withSelfRel(),
                linkTo(methodOn(MatiereController.class).all()).withRel("matieres"));
    }

    /**
     * Supprime une matière par son ID.
     * @param id L'ID de la matière à supprimer.
     * @return Une réponse contenant un message de confirmation et la matière supprimée avec des liens HATEOAS.
     */
    @DeleteMapping("/matieres/{id}")
    EntityModel<Map<String, Object>> deleteMatiere(@PathVariable Long id) {
        Matiere deletedMatiere = matiereService.deleteMatiere(id);
        
        String message = "Matière supprimée: " + deletedMatiere.getIntitule();
        
        Map<String, Object> reponse = new HashMap<>();
        reponse.put("message", message);
        reponse.put("matiere", deletedMatiere);
        
        return EntityModel.of(reponse,
                linkTo(methodOn(MatiereController.class).all()).withRel("matieres"));
    }
}
