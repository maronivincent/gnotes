package com.saintsau.slam2.gnotes30.exeption;

/**
 * Exception personnalisée pour indiquer qu'une matière avec un identifiant donné
 * n'a pas pu être trouvée dans la base de données.
 * Cette exception est lancée lorsqu'une tentative d'accès à une matière échoue,
 * généralement à cause d'un identifiant incorrect ou inexistant.
 * 
 *
 */
public class MatiereNotFoundException extends RuntimeException {

    /**
     * Constructeur de l'exception {@link MatiereNotFoundException}.
     * Il prend un identifiant de matière (idMatiere) et crée un message d'erreur
     * indiquant que la matière associée à cet identifiant n'a pas été trouvée.
     *
     * @param idMatiere L'identifiant de la matière qui n'a pas été trouvée.
     */
    public MatiereNotFoundException(Long idMatiere) {
        super("Could not find Matiere with id: " + idMatiere);
    }
}
