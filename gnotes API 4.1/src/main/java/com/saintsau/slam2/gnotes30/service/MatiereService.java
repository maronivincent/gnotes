package com.saintsau.slam2.gnotes30.service;

import com.saintsau.slam2.gnotes30.entity.Matiere;
import com.saintsau.slam2.gnotes30.exeption.MatiereNotFoundException;
import com.saintsau.slam2.gnotes30.jpaRepository.MatiereRepository;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour gérer les opérations liées aux matières.
 * Cette classe permet de récupérer, ajouter, mettre à jour et supprimer des matières.
 */
@Service
public class MatiereService {

    private final MatiereRepository matiereRepository; // Repository pour accéder aux données des matières

    /**
     * Constructeur pour injecter les dépendances.
     *
     * @param matiereRepository Repository pour les matières
     */
    MatiereService(MatiereRepository matiereRepository) {
        super();
        this.matiereRepository = matiereRepository;
    }

    /**
     * Récupère tous les éléments de la table des matières.
     *
     * @return Liste de toutes les matières
     */
    public List<Matiere> findAllMatiere() {
        return (List<Matiere>) matiereRepository.findAll(); 
    }

    /**
     * Récupère une matière par son ID.
     * Lève une exception si la matière n'est pas trouvée.
     *
     * @param id L'ID de la matière à récupérer
     * @return La matière correspondant à l'ID
     * @throws MatiereNotFoundException Si la matière n'est pas trouvée
     */
    public Matiere getMatiereById(Long id) {
        return matiereRepository.findById(id)
                .orElseThrow(() -> new MatiereNotFoundException(id));
    }

    /**
     * Crée une nouvelle matière.
     *
     * @param matiere La matière à créer
     * @return La matière créée
     */
    public Matiere saveMatiere(Matiere matiere) {
        return matiereRepository.save(matiere);
    }

    /**
     * Met à jour une matière existante.
     * Lève une exception si la matière n'existe pas.
     *
     * @param id L'ID de la matière à mettre à jour
     * @param updatedMatiere L'objet Matiere avec les nouvelles valeurs
     * @return La matière mise à jour
     * @throws MatiereNotFoundException Si la matière n'est pas trouvée
     */
    public Matiere updateMatiere(Long id, Matiere updatedMatiere) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new MatiereNotFoundException(id));

        matiere.setIntitule(updatedMatiere.getIntitule());
        // Ajouter d'autres champs à mettre à jour ici si nécessaire
        return matiereRepository.save(matiere);
    }

    /**
     * Supprime une matière par son ID.
     * Lève une exception si la matière n'est pas trouvée.
     *
     * @param id L'ID de la matière à supprimer
     * @return La matière supprimée
     * @throws MatiereNotFoundException Si la matière n'est pas trouvée
     */
    public Matiere deleteMatiere(Long id) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new MatiereNotFoundException(id));
        matiereRepository.delete(matiere);

        return matiere;
    }
}
