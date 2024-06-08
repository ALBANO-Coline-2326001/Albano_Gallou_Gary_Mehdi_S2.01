package fr.amu.iut.earthquakeapp.donnée;

public class GameStats {
    private String opponent1;
    private String opponent2;
    private String time1;
    private String time2;
    private String result;


    public GameStats(String opponent1,String opponent2, String time1, String time2, String result) {
        this.opponent1 = opponent1;
        this.opponent2 = opponent2;
        this.time1 = time1;
        this.time2 = time2;
        this.result = result;
    }

    public String getOpponent1() {
        return opponent1;
    }

    public String getOpponent2() {
        return opponent2;
    }

    public String getTime1() {
        return time1;
    }

    public String getTime2() {
        return time2;
    }

    public String getResult() {
        return result;
    }

    public void setOpponent1(String opponent1) {
        this.opponent1 = opponent1;
    }

    public void setOpponent2(String opponent2) {
        this.opponent2 = opponent2;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

