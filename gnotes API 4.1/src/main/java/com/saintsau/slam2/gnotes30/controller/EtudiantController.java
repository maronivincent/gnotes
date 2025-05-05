package com.saintsau.slam2.gnotes30.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saintsau.slam2.gnotes30.entity.Etudiant;
import com.saintsau.slam2.gnotes30.service.EtudiantService;

@RestController
/**
 * Contrôleur pour gérer les opérations CRUD sur les étudiants (Etudiants).
 * Fournit des endpoints pour ajouter, récupérer, modifier et supprimer des étudiants.
 */
public class EtudiantController {
    private final EtudiantService etudiantService;

    /**
     * Constructeur pour initialiser le service d'étudiants.
     * @param etudiantService Service de gestion des étudiants.
     */
    public EtudiantController(EtudiantService etudiantService) {
        super();
        this.etudiantService = etudiantService;
    }

    /**
     * Crée un nouveau étudiant et l'ajoute à la base de données.
     * @param etudiant L'objet étudiant à ajouter.
     * @return La réponse avec l'étudiant ajouté et un lien HATEOAS.
     */
    @PostMapping("/etudiants")
    public ResponseEntity<EntityModel<Etudiant>> newEtudiant(@RequestBody Etudiant etudiant) {
        Etudiant savedEtudiant = etudiantService.saveEtudiant(etudiant);

        EntityModel<Etudiant> entityModel = EntityModel.of(savedEtudiant,
                linkTo(methodOn(EtudiantController.class).one(savedEtudiant.getNumero())).withSelfRel(),
                linkTo(methodOn(EtudiantController.class).all()).withRel("etudiants"));
        
        // Retourne l'EntityModel avec le statut 201 Created et l'en-tête Location
        return ResponseEntity
                .created(URI.create(entityModel.getRequiredLink("self").getHref())) // Location header
                .body(entityModel); // Le corps contient la représentation de la ressource
    }

    /**
     * Récupère la liste de tous les étudiants.
     * @return Une collection d'entités étudiant avec des liens HATEOAS.
     */
    @GetMapping("/etudiants")
    CollectionModel<EntityModel<Etudiant>> all() {
        List<EntityModel<Etudiant>> etudiants = StreamSupport.stream(etudiantService.findAllEtudiant().spliterator(), false)
                .map((Etudiant etudiant) -> EntityModel.of(etudiant,
                        linkTo(methodOn(EtudiantController.class).one(etudiant.getNumero())).withSelfRel()))    
                .collect(Collectors.toList());

        return CollectionModel.of(etudiants, 
                linkTo(methodOn(EtudiantController.class).all()).withSelfRel());
    }

    /**
     * Récupère un étudiant spécifique par son ID.
     * @param id L'ID de l'étudiant à récupérer.
     * @return L'entité étudiant avec des liens HATEOAS.
     */
    @GetMapping("/etudiants/{id}")
    EntityModel<Etudiant> one(@PathVariable Long id) {
        Etudiant etudiant = etudiantService.findEtudiantById(id);
      
        return EntityModel.of(etudiant,
                linkTo(methodOn(EtudiantController.class).one(id)).withSelfRel(),
                linkTo(methodOn(EtudiantController.class).all()).withRel("etudiants"));
    }

    /**
     * Met à jour un étudiant existant par son ID.
     * @param id L'ID de l'étudiant à modifier.
     * @param updatedEtudiant L'objet étudiant mis à jour.
     * @return L'entité étudiant mise à jour avec des liens HATEOAS.
     */
    @PutMapping("/etudiants/{id}")
    public EntityModel<Etudiant> updateEtudiant(@PathVariable Long id, @RequestBody Etudiant updatedEtudiant) {
        // Appel du service pour mettre à jour l'étudiant
        Etudiant etudiant = etudiantService.updateEtudiant(id, updatedEtudiant);
        
        // Retourne l'étudiant mis à jour dans un EntityModel avec des liens HATEOAS
        return EntityModel.of(etudiant,
                linkTo(methodOn(EtudiantController.class).one(etudiant.getNumero())).withSelfRel(),
                linkTo(methodOn(EtudiantController.class).all()).withRel("etudiants"));
    } 

    /**
     * Supprime un étudiant par son ID.
     * @param id L'ID de l'étudiant à supprimer.
     * @return Une réponse contenant un message de confirmation et l'étudiant supprimé avec des liens HATEOAS.
     */
    @DeleteMapping("/etudiants/{id}")
    EntityModel<Map<String, Object>> deleteEtudiant(@PathVariable Long id) {
        // Trouver l'étudiant par ID ou générer une exception
        Etudiant deletedEtudiant = etudiantService.deleteById(id);

        // Création du message de réponse
        String message = "Etudiant supprimé: " + deletedEtudiant.getNom();

        // Préparer la réponse sous forme de carte
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("etudiant", deletedEtudiant);  // Vous pouvez inclure d'autres détails sur l'étudiant si nécessaire

        // Retourner la réponse avec des liens HATEOAS
        return EntityModel.of(response,
                linkTo(methodOn(EtudiantController.class).all()).withRel("etudiants"));
    }
}
