package fr.amu.iut.earthquakeapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Classe qui permet de charger les données dans un barchart (celui du dashboard dans notre appli)
 *
 * @version 1.0
 */

public class AccueilController {

    @FXML
    private GridPane chessBoard;

    public void initialize() {
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle square = new Rectangle(50, 50);
                if ((i + j) % 2 == 0) {
                    square.setFill(Color.WHITE);
                } else {
                    square.setFill(Color.GRAY);
                }
                chessBoard.add(square, j, i);
            }
        }
    }
}
