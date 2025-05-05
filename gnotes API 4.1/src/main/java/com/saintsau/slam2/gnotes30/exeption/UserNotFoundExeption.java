package com.saintsau.slam2.gnotes30.exeption;

/**
 * Exception personnalisée pour indiquer qu'un utilisateur avec un identifiant donné
 * n'a pas pu être trouvé dans la base de données ou l'application.
 * Cette exception est généralement lancée lorsqu'une tentative d'accès à un utilisateur échoue,
 * en raison d'un identifiant incorrect ou inexistant.
 * 
 *
 */
public class UserNotFoundExeption extends RuntimeException {
    
    /**
     * Constructeur de l'exception {@link UserNotFoundExeption}.
     * Il prend un identifiant d'utilisateur (id) et génère un message d'erreur
     * précisant que l'utilisateur associé à cet identifiant n'a pas été trouvé.
     *
     * @param id L'identifiant de l'utilisateur qui n'a pas été trouvé.
     */
    public UserNotFoundExeption(Long id) {
        super("Could not find User with id: " + id);
    }
}
