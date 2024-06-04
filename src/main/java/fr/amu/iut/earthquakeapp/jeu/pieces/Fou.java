package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Fou extends Piece {

    private static int dernierId = 0;

    public Fou(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        setImage(isWhite ? new ImageView("/img/image echec/fou_blanc.png") : new ImageView("/img/image echec/fou_noir.png"));
        setNom("Fou" + dernierId + (isWhite ? "blanc" : "noir"));
        getImage().setFitHeight(75);
        getImage().setFitWidth(75);
        dernierId++;
    }

    @Override
    public void move(int x, int y) {
        setCoordonne(x, y);
    }

    @Override
    public boolean isValide(int x, int y, ArrayList<ArrayList<Piece>> plateau) {
        // Insérez votre logique de validation spécifique pour le mouvement du fou ici
        // Assurez-vous de respecter les règles du jeu d'échecs pour le mouvement du fou

        // Par exemple, vous pouvez vérifier si le déplacement est diagonal
        int deltaX = Math.abs(x - getX());
        int deltaY = Math.abs(y - getY());

        return deltaX == deltaY;
    }

}
