package fr.amu.iut.earthquakeapp.donnée;

public class GameStats {
    private String opponent;
    private String time;
    private String result;

    // Constructors, getters, and setters
    public GameStats(String opponent, String time, String result) {
        this.opponent = opponent;
        this.time = time;
        this.result = result;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}