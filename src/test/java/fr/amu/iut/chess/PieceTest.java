package fr.amu.iut.chess;


import fr.amu.iut.earthquakeapp.jeu.Piece;
import fr.amu.iut.earthquakeapp.jeu.pieces.Pion;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

public class PieceTest {


    Piece pion;
/*    Piece fou;
    Piece cavalier;
    Piece tour;
    Piece roi;
    Piece reine;*/
    @Mock
    ArrayList<ArrayList<Piece>> chessBoard;

    Piece pionAdverse;

    @BeforeEach
    public void init(){

        pion = new Pion(true, 0, 0);
        pionAdverse = new Pion(false, 0, 0);
    }


    @Test
    public void testPion(){
        assertEquals(true, pion.isWhite());
        when(chessBoard.get(2).get(4)).thenReturn(pion);
        when(chessBoard.get(3).get(4)).thenReturn(pionAdverse);
        assertEquals(2, pion.getX());
//        assertEquals(false, pion.isValide(3, 4, chessBoard));
    }


}
