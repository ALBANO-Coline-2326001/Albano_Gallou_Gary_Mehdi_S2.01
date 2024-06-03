package fr.amu.iut.earthquakeapp.controllers;

import fr.amu.iut.earthquakeapp.jeu.Board;

public class MoveController {

    private static MoveController instance ;

    public static MoveController getInstance(){
        if(instance == null){
            instance = new MoveController();
        }
        return instance;
    }

    private Board chessBoard;
    private int x;
    private int y;

    public void movePiece(){

    }

}
