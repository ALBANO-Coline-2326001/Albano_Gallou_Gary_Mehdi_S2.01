package fr.amu.iut.earthquakeapp.controllers;

import fr.amu.iut.earthquakeapp.jeu.Piece;
import fr.amu.iut.earthquakeapp.jeu.pieces.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Classe qui permet de charger les données dans un barchart (celui du dashboard dans notre appli)
 *
 * @version 1.0
 */

public class AccueilController {

    private MoveController moveController; ;

    private Piece selectedPiece = null;
    private ImageView selectedImageView = null;
    private int selectedRow = -1;
    private int selectedCol = -1;

    @FXML
    private GridPane chessBoard;
    @FXML
    private Label timerLabel1;
    private ArrayList<ArrayList<Piece>> plateau = new ArrayList<>();
    private Timeline timeline;

    @FXML
    private TabPane tabPane;

    @FXML
    public void initialize() {
        for (Tab tab : tabPane.getTabs()) {
            tab.setClosable(false);
        }
        this.moveController = MoveController.getInstance();
        initializeBoard();
        affichage();
    }


    private void initializeBoard() {

        for (int i = 0; i < 8; i++) {
            ArrayList<Piece> row = new ArrayList<>();
            boolean isWhite = (i <= 1);  // White pieces on rows 0 and 1

            for (int j = 0; j < 8; j++) {
                Piece piece = null;

                if (i == 0 || i == 7) { // Main pieces on first and last rows
                    if (j == 0 || j == 7) {
                        piece = new Tour(isWhite, i, j);
                    } else if (j == 1 || j == 6) {
                        piece = new Cavalier(isWhite, i, j);
                    } else if (j == 2 || j == 5) {
                        piece = new Fou(isWhite, i, j);
                    } else if (j == 3) {
                        piece = new Reine(isWhite, i, j);
                    } else if (j == 4) {
                        piece = new Roi(isWhite, i, j);
                    }
                } else if (i == 1 || i == 6) { // Pawns on second and second last rows
                    piece = new Pion(isWhite, i, j);
                }
                // Add the piece or null (for empty squares) to the row
                row.add(piece);
            }
            // Add the completed row to the board
            plateau.add(row);
        }
    }


    private void affichage() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle square = new Rectangle(75, 75);
                square.setFill((i + j) % 2 == 0 ? Color.rgb(235,236,208) : Color.rgb(115,149,82));
                chessBoard.add(square, j, i);

                // Set the mouse click event handler for each square
                final int row = i;
                final int col = j;
                square.setOnMouseClicked(event -> handleMouseClick(row, col));

                Piece indice = plateau.get(i).get(j);
                if (indice != null) {
                    chessBoard.add(indice.getImage(), j, i);
                    indice.getImage().setOnMouseClicked(event -> handleMouseClick(row, col));
                }
                }
            }
        }

    private void handleMouseClick(int row, int col) {
        System.out.println("Ligne cliquée : " + row + ", Colonne : " + col);

        Piece clickedPiece = plateau.get(row).get(col);
        ImageView clickedImageView = (clickedPiece != null) ? clickedPiece.getImage() : null;

        // Si une pièce est déjà sélectionnée
        if (selectedPiece != null) {
            // Vérifie si la case cliquée est différente de la case originale et si la pièce peut être déplacée
            if (row != selectedRow || col != selectedCol) {
                // Tente de déplacer la pièce
                if (movePiece(selectedRow, selectedCol, row, col, clickedPiece)) {
                    // Le déplacement a réussi, enlève l'image précédente
                    chessBoard.getChildren().remove(selectedImageView);
                    if (clickedImageView != null) {
                        chessBoard.getChildren().remove(clickedImageView); // Enlève l'image de la pièce capturée
                    }
                    chessBoard.add(selectedImageView, col, row);

                    // Mettre à jour la structure de données du plateau
                    plateau.get(selectedRow).set(selectedCol, null);
                    plateau.get(row).set(col, selectedPiece);

                    // Réinitialiser la sélection
                    selectedPiece = null;
                    selectedImageView = null;
                    selectedRow = -1;
                    selectedCol = -1;
                } else {
                    // Le déplacement est invalide, réinitialiser la sélection
                    selectedPiece = null;
                    selectedImageView = null;
                    selectedRow = -1;
                    selectedCol = -1;
                }
            } else {
                // Clic sur la même pièce, la désélectionner
                selectedPiece = null;
                selectedImageView = null;
                selectedRow = -1;
                selectedCol = -1;
            }
        } else if (clickedPiece != null) {
            // Aucune pièce n'est sélectionnée et le joueur a cliqué sur une pièce
            selectedPiece = clickedPiece;
            selectedImageView = clickedImageView;
            selectedRow = row;
            selectedCol = col;
        }
    }


    private void resetSelection() {
        selectedPiece = null;
        selectedImageView = null;
        selectedRow = -1;
        selectedCol = -1;
    }


    private boolean movePiece(int fromRow, int fromCol, int toRow, int toCol, Piece targetPiece) {

        return true;  // Retourne false si le déplacement n'est pas valide
    }



    public void startTimer() {
        if (timeline != null) {
            timeline.stop(); // Arrête le timer précédent si il est en cours
        }
        int startTime = 600; // 10 minutes en secondes
        AtomicInteger timeSeconds = new AtomicInteger(startTime);

        // Mise à jour de l'étiquette pour le timer
        timerLabel1.setText(timeToString(timeSeconds.get()));

        // Crée un timeline pour le compte à rebours
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), e -> {
                    int currentTime = timeSeconds.decrementAndGet();
                    timerLabel1.setText(timeToString(currentTime));
                    if (currentTime <= 0) {
                        timeline.stop();
                        timerLabel1.setText("Temps écoulé !");
                    }
                })
        );
        timeline.playFromStart();
    }

    private String timeToString(int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}
