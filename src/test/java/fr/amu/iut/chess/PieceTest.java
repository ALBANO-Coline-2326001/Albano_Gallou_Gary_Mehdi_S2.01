package fr.amu.iut.chess;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

public class PieceTest {

    PieceModel pieceBlanche;
    @Mock
    ArrayList<ArrayList<PieceModel>> chessBoard;
    @Mock
    ArrayList<PieceModel> row2;
    @Mock
    ArrayList<PieceModel> row3;
    @Mock
    ArrayList<PieceModel> row5;
    @Mock
    ArrayList<PieceModel> row7;
    @Mock
    ArrayList<PieceModel> row6;
    @Mock
    ArrayList<PieceModel> row0;
    @Mock
    ArrayList<PieceModel> row1;

    PieceModel pieceNoir;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        pieceBlanche = new PieceModel(true, 0, 0);
        pieceNoir = new PieceModel(false, 0, 0);

        // Mock des lignes du tableau d'échecs
        when(chessBoard.get(2)).thenReturn(row2);
        when(chessBoard.get(3)).thenReturn(row3);
        when(chessBoard.get(7)).thenReturn(row7);
        when(chessBoard.get(6)).thenReturn(row6);
        when(chessBoard.get(0)).thenReturn(row0);
        when(chessBoard.get(1)).thenReturn(row1);
        when(chessBoard.get(5)).thenReturn(row5);

        // Mock des cases spécifiques dans les lignes
        when(row2.get(4)).thenReturn(pieceBlanche);
        when(row3.get(4)).thenReturn(pieceNoir);
        when(row7.get(1)).thenReturn(pieceNoir);
        when(row7.get(0)).thenReturn(null);
        when(row6.get(4)).thenReturn(pieceBlanche);
        when(row0.get(0)).thenReturn(pieceNoir);
        when(row1.get(0)).thenReturn(pieceNoir);
        when(row3.get(3)).thenReturn(pieceNoir);
        when(row7.get(3)).thenReturn(pieceBlanche);
    }

    @Test
    public void testPion() {
        assertEquals(true, pieceBlanche.isWhite());
        assertEquals(false, pieceNoir.isValide(3, 4, chessBoard)); //Test un mouvement en avant bloqué par un pion adversaire
    }

    @Test
    public void testCavalier() {
        pieceBlanche.setCoordonne(7, 1);
        when(row7.get(1)).thenReturn(pieceBlanche);
        when(row5.get(0)).thenReturn(null);

        assertEquals(true, pieceBlanche.isValideCavalier(5, 0, chessBoard)); // Test du mouvement en L

        when(row5.get(1)).thenReturn(null);
        assertEquals(false, pieceBlanche.isValideCavalier(5, 1, chessBoard)); // Test pour un mouvement quine correspond pas au Cavalier
    }

    @Test
    public void testRoi() {
        pieceBlanche.setCoordonne(7, 4);
        when(row7.get(4)).thenReturn(pieceBlanche);
        when(row6.get(4)).thenReturn(pieceBlanche);

        assertEquals(false, pieceBlanche.isValideRoi(6, 4, chessBoard)); // Test un mouvement invalide pour le roi , bloqué par un pion devant

        when(row7.get(3)).thenReturn(null);
        assertEquals(true, pieceBlanche.isValideRoi(7, 3, chessBoard)); // Test pour un mouvement valide du roi

        when(row3.get(4)).thenReturn(null);
        assertEquals(false, pieceBlanche.isValideRoi(3, 4, chessBoard)); // Test pour un mouvement invalide du roi : avancer de plusieur cases
    }

    @Test
    public void testTour() {
        pieceNoir.setCoordonne(0, 0);
        when(row0.get(0)).thenReturn(pieceNoir);
        when(row1.get(0)).thenReturn(null);

        assertEquals(true, pieceNoir.isValideTour(1, 0, chessBoard)); // Test mouvement valide de la tour

        when(row0.get(1)).thenReturn(pieceNoir);
        assertEquals(false,pieceNoir.isValideTour(0,1,chessBoard)); // Test mouvement invalide deplacement horizontale bloqué par le cavalier noir
    }


    @Test
    public void testFou(){
        pieceBlanche.setCoordonne(7,2);
        when(row7.get(2)).thenReturn(pieceBlanche);
        when(row3.get(3)).thenReturn(pieceNoir);
        assertEquals(false,pieceBlanche.isValideReine(3,3,chessBoard)); // Test mouvement invalide pour le fou (accès à une case pas dans son champs de déplacelment)

        when(row7.get(2)).thenReturn(pieceBlanche);
        when(row5.get(4)).thenReturn(null);
        assertEquals(true,pieceBlanche.isValideReine(5,4,chessBoard)); // Test mouvement valide pour le fou : deplacement en diagonale

        when(row7.get(2)).thenReturn(pieceBlanche);
        when(row5.get(4)).thenReturn(pieceBlanche);
        assertEquals(false,pieceBlanche.isValideReine(5,4,chessBoard)); // Test mouvement invalide , diagonale bloqué par un pion allié

    }


}
