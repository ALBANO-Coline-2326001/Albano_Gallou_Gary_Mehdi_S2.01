package fr.amu.iut.earthquakeapp.donnée;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.*;
import java.util.*;

public class PlayerData {
    private IntegerProperty gamesPlayed;
    private IntegerProperty score;

    public PlayerData() {
        this.gamesPlayed = new SimpleIntegerProperty(0);
        this.score = new SimpleIntegerProperty(0);
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed.set(gamesPlayed);
    }
    public void setScore(int score) {
        this.score.set(score);
    }

    public void writeDataToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Games Played,");
            sb.append("Score");
            sb.append('\n');

            sb.append(this.gamesPlayed);
            sb.append(',');
            sb.append(this.score);
            sb.append('\n');

            writer.write(sb.toString());

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static PlayerData readDataFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine(); // skip header
            line = reader.readLine(); // read data

            String[] data = line.split(",");
            int gamesPlayed = Integer.parseInt(data[0]);
            int score = Integer.parseInt(data[1]);

            return new PlayerData();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}