package fr.amu.iut.chess;


import fr.amu.iut.earthquakeapp.jeu.Piece;
import fr.amu.iut.earthquakeapp.jeu.pieces.Pion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerTest {

    Piece pion;
    Piece fou;
    Piece cavalier;
    Piece tour;
    Piece roi;
    Piece reine;
    @Mock
    ArrayList<ArrayList<Piece>> chessBoard;

    @BeforeEach
    public void init(){
        pion = new Pion(true, 0, 0);

    }

    @Test
    public void testPion(){
        assertEquals(true, pion.isWhite());

    }


}
