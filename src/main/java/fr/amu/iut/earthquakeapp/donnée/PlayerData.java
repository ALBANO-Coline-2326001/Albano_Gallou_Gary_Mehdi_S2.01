package fr.amu.iut.earthquakeapp.donnée;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.*;
import java.util.*;

public class PlayerData {
    private int gamesPlayed;
    private int score;
    private List<GameStats> games;
    private StringBuilder sb = new StringBuilder();

    public int getScore() {
        return score;
    }

    /**
     * Constructeur par défaut qui initialise les parties jouées et le score à zéro.
     */
    public PlayerData() {
        gamesPlayed = 0;
        score = 0;
        games = new ArrayList<>();


    }

    public void setGames(GameStats game){
        games.add(game);
    }

    /**
     * Définit le nombre de parties jouées.
     *
     * @param gamesPlayed Nombre de parties jouées.
     */
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * Définit le score du joueur.
     *
     * @param score Le score à attribuer au joueur.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Écrit les données du joueur dans un fichier.
     *
     * @param filename Le nom du fichier dans lequel écrire les données.
     */
    public void writeDataToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            sb.replace(0,22,"Games Played ," + this.gamesPlayed + '\n' + "Score" + this.score +'\n' );

            if (!games.isEmpty()) {
                sb.append(games.get(games.size()-1).getOpponent() + " " + games.get(games.size()-1).getTime() + "  " + games.get(games.size()-1).getResult());
                sb.append('\n');
            }
            writer.println(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Lit les données du joueur à partir d'un fichier.
     *
     * @param filename Le nom du fichier à partir duquel lire les données.
     * @return Le contenu du fichier sous forme de chaîne de caractères, ou null en cas d'erreur.
     */
    public static String readDataFromFile(String filename) {
        StringBuilder fileContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return fileContent.toString();
    }


}