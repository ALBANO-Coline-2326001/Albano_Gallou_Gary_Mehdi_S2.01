package fr.amu.iut.chess.donnée;

public class GameStats {
    private String opponent2;
    private String time2;
    private String result;


    public GameStats(String opponent2,  String time2, String result) {
        this.opponent2 = opponent2;
        this.time2 = time2;
        this.result = result;
    }


    public String getOpponent() {
        return opponent2;
    }

    public String getTime() {
        return time2;
    }

    public String getResult() {
        return result;
    }


    public void setOpponent2(String opponent2) {
        this.opponent2 = opponent2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

