package com.saintsau.slam2.gnotes30.exeption;

/**
 * Exception personnalisée pour indiquer qu'un étudiant n'a pas été trouvé.
 * Cette exception est lancée lorsqu'un étudiant avec un identifiant spécifique
 * n'a pas pu être localisé dans le système.
 * 
 * 
 */
public class EtudiantNotFoundException extends RuntimeException {
    
    /**
     * Constructeur pour l'exception EtudiantNotFoundException.
     * 
     * @param id L'identifiant de l'étudiant qui n'a pas été trouvé.
     */
    public EtudiantNotFoundException(Long id) {
        super("Could not find Etudiant with id: " + id);
    }
}
