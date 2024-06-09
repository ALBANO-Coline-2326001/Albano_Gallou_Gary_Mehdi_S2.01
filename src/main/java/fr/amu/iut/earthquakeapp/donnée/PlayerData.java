package fr.amu.iut.earthquakeapp.donnée;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.*;
import java.util.*;

public class PlayerData {
    private int gamesPlayed;
    private int score;
    private String name;

    public PlayerData() {
     gamesPlayed = 0;
       score = 0;
    }

    public PlayerData(String name) {
        this.name = name;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public void writeDataToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Games Played ,");
            sb.append(this.gamesPlayed);
            sb.append(',');
            sb.append('\n');
            sb.append("Score");
            sb.append(this.score);
            sb.append('\n');

            writer.write(sb.toString());

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

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