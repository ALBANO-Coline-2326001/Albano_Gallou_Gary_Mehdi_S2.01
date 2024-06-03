package fr.amu.iut.earthquakeapp.controllers;

public class Cavalier {
    private int x;
    private int y;

    public Cavalier(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /* Une méthode qui renvoie les coordonnées possibles du cavalier */
    public static void deplacementCavalier(int x, int y) {
        int[][] deplacement = {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};
        for (int i = 0; i < deplacement.length; i++) {
            int newX = x + deplacement[i][0];
            int newY = y + deplacement[i][1];
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                System.out.println("Le cavalier peut se déplacer en : " + newX + " " + newY);
            }
        }
    }
}
