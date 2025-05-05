package com.saintsau.slam2.gnotes30.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe pour gérer les exceptions globales dans l'application.
 * Cette classe est annotée avec {@link RestControllerAdvice} et elle permet de capturer
 * différentes exceptions lancées dans les contrôleurs pour fournir une réponse personnalisée.
 * Elle définit des gestionnaires pour les exceptions spécifiques et génériques.
 * 
 * Les exceptions gérées incluent :
 * <ul>
 *     <li>{@link EtudiantNotFoundException} : Pour gérer les erreurs liées aux étudiants non trouvés.</li>
 *     <li>{@link MatiereNotFoundException} : Pour gérer les erreurs liées aux matières non trouvées.</li>
 *     <li>{@link Exception} : Pour gérer toute autre erreur inattendue.</li>
 * </ul>
 * 
 * 
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestionnaire d'exception pour {@link EtudiantNotFoundException}.
     * Cette méthode intercepte les exceptions lorsque l'étudiant n'est pas trouvé et
     * renvoie une réponse avec un statut HTTP 404 (Not Found).
     *
     * @param ex L'exception {@link EtudiantNotFoundException} à gérer.
     * @return Un {@link Map} contenant un message d'erreur.
     */
    @ExceptionHandler(EtudiantNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleEtudiantNotFoundException(EtudiantNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }

    /**
     * Gestionnaire d'exception pour {@link MatiereNotFoundException}.
     * Cette méthode intercepte les exceptions lorsque la matière n'est pas trouvée et
     * renvoie une réponse avec un statut HTTP 404 (Not Found).
     *
     * @param ex L'exception {@link MatiereNotFoundException} à gérer.
     * @return Un {@link Map} contenant un message d'erreur.
     */
    @ExceptionHandler(MatiereNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleMatiereNotFoundException(MatiereNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }

    /**
     * Gestionnaire d'exception générique pour toutes les autres exceptions non spécifiées.
     * Cette méthode intercepte toute exception non spécifique et renvoie une réponse avec un statut
     * HTTP 500 (Internal Server Error).
     *
     * @param ex L'exception générique à gérer.
     * @return Un {@link Map} contenant un message d'erreur générique.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleGeneralException(Exception ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "An unexpected error occurred: " + ex.getMessage());
        return errorMap;
    }
}
