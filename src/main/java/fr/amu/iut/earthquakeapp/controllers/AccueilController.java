package fr.amu.iut.earthquakeapp.controllers;

import fr.amu.iut.earthquakeapp.donnée.PlayerData;
import fr.amu.iut.earthquakeapp.jeu.Piece;
import fr.amu.iut.earthquakeapp.jeu.pieces.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private boolean isWhiteTurn = true;

    private MoveController moveController;
    @FXML
    private Button jouer;
    @FXML
    private Tab Partie;
    @FXML
    private Label donnee;

    private Piece selectedPiece = null;
    private ImageView selectedImageView = null;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private IntegerProperty nbpartie = new SimpleIntegerProperty(0);
    private PlayerData playerData = new PlayerData();

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


    public Button getJouer() {
        return jouer;
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
                row.add(piece);
            }
            plateau.add(row);
        }
    }

    private void affichage() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle square = new Rectangle(75, 75);
                square.setFill((i + j) % 2 == 0 ? Color.rgb(235, 236, 208) : Color.rgb(115, 149, 82));
                chessBoard.add(square, j, i);

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

        if (selectedPiece != null) {
            if (row != selectedRow || col != selectedCol) {
                if (movePiece(selectedRow, selectedCol, row, col, selectedPiece)) {
                    chessBoard.getChildren().remove(selectedImageView);
                    if (clickedImageView != null) {
                        chessBoard.getChildren().remove(clickedImageView);
                    }
                    chessBoard.add(selectedImageView, col, row);

                    plateau.get(selectedRow).set(selectedCol, null);
                    plateau.get(row).set(col, selectedPiece);

                    resetSelection();
                } else {
                    resetSelection();
                }
            } else {
                resetSelection();
            }
        } else if (clickedPiece != null) {
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
        if (targetPiece.isValide(toRow, toCol, plateau)) {
            plateau.get(fromRow).set(fromCol, null);  // Retirer la pièce de l'ancienne position
            plateau.get(toRow).set(toCol, targetPiece);  // Placer la pièce dans la nouvelle position
            targetPiece.move(toRow, toCol);  // Mettre à jour les coordonnées de la pièce
            return true;
        }
        return false;
    }

    public void startTimer() {
        if (timeline != null) {
            timeline.stop(); // Arrête le timer précédent s'il est en cours
        }
        int startTime = 600; // 10 minutes en secondes
        AtomicInteger timeSeconds = new AtomicInteger(startTime);

        timerLabel1.setText(timeToString(timeSeconds.get()));

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

        nbpartie.set(nbpartie.get() + 1);
        playerData.setGamesPlayed(nbpartie.get());
        playerData.writeDataToFile("playerData.json");
    }


    public void showData(){
        if (Partie.isSelected()){
            donnee.setText(PlayerData.readDataFromFile("playerData.json"));
            donnee.setTextFill(Color.WHITE);

        }

    }

    private String timeToString(int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
