package fr.amu.iut.earthquakeapp.donnée;

/**
 * Classe représentant les statistiques d'une partie, incluant l'adversaire, le temps et le résultat.
 */
public class GameStats {
    private String opponent;  // Nom de l'adversaire
    private String time;      // Durée de la partie
    private String result;    // Résultat de la partie

    /**
     * Constructeur de la classe GameStats.
     *
     * @param opponent Le nom de l'adversaire.
     * @param time La durée de la partie.
     * @param result Le résultat de la partie.
     */
    public GameStats(String opponent, String time, String result) {
        this.opponent = opponent;
        this.time = time;
        this.result = result;
    }

    /**
     * Obtient le nom de l'adversaire.
     *
     * @return Le nom de l'adversaire.
     */
    public String getOpponent() {
        return opponent;
    }

    /**
     * Définit le nom de l'adversaire.
     *
     * @param opponent Le nom de l'adversaire.
     */
    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    /**
     * Obtient la durée de la partie.
     *
     * @return La durée de la partie.
     */
    public String getTime() {
        return time;
    }

    /**
     * Définit la durée de la partie.
     *
     * @param time La durée de la partie.
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Obtient le résultat de la partie.
     *
     * @return Le résultat de la partie.
     */
    public String getResult() {
        return result;
    }

    /**
     * Définit le résultat de la partie.
     *
     * @param result Le résultat de la partie.
     */
    public void setResult(String result) {
        this.result = result;
    }
}
