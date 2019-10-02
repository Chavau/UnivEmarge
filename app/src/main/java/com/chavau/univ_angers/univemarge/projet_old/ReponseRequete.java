package c.acdi.master.jderamaix.suaps;


/**
 * Classe utilisé pour transité les données venant de la base données (en Json)
 *      pour l'application (en java).
 *      Ces informations sont les résultat de la requête si elle atteint la base de données
 *      et qu'il n'y a pas de problème imprévue comme des erreurs de connexions, de typage
 *      ou encore de routes.
 *      Possibles résultats sont :<br>
 *          - "Limite de personnes atteintes."<br>
 *          - "Inscription réussie."<br>
 *          - "Désinscription réussie."<br>
 */
public class ReponseRequete {

    /*
     * Résultat de la requête formulé explicitement par la base de données,
     * N'a un but qu'informatif.
     */
    private String reponse;

    /**
     * Constructeur initialisant la reponse.
     * @param reponse : Le résultat informatif de la requête.
     */
    public ReponseRequete(String reponse){ this.reponse = reponse; }

    /**
     * Méthode renvoyant reponse.
     * @return : résultat informatif de la requête.
     */
    public String getReponse(){
        try {
            if (this.reponse != null) {
                return this.reponse;
            }
        } catch (Exception e) {
           return "null";
        }
        return "null";
    }

}
