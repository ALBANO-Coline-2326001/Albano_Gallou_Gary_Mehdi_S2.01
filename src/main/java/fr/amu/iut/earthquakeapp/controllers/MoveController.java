package fr.amu.iut.earthquakeapp.controllers;

import fr.amu.iut.earthquakeapp.jeu.Board;
import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class MoveController {

    private ObservableList<ObservableList<Piece>> pieces;
    private static MoveController instance ;
    private static Board chessBoard;
    private int x;
    private int y;
    private ChangeListener<Number> deplacement;




    public MoveController(Board chessBoard) {
       this.chessBoard = chessBoard;
    }
    public static MoveController getInstance(){
        if(instance == null){
            instance = new MoveController(chessBoard);
        }
        return instance;
    }


    public void move(){
        for (ArrayList<Piece> row : chessBoard.getBoard()) {
            for (Piece piece : row) {
                if (piece != null) {
                    piece.getImage().setOnMouseClicked(event -> {
                        Piece clickedPiece = (Piece) event.getSource();
                        deplacement = (observable, oldValue, newValue) -> {
                            x = (int) newValue / 50;
                            y = (int) newValue / 50;
                            clickedPiece.move(x, y);
                        };
                        // Effectuer l'action souhaitée
                        System.out.println("Piece clicked: " + clickedPiece);
                        // Gérer le clic ici
                    });
                }
            }
        }

    }

}
