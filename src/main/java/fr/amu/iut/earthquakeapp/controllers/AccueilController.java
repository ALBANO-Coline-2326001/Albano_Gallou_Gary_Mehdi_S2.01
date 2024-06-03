package fr.amu.iut.earthquakeapp.controllers;

import fr.amu.iut.earthquakeapp.jeu.Piece;
import fr.amu.iut.earthquakeapp.jeu.pieces.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    @FXML
    private GridPane chessBoard;
    @FXML
    private Label timerLabel1;
    private ArrayList<ArrayList<Piece>> plateau = new ArrayList<>();
    private Timeline timeline;

    public void initialize() {
        this.moveController = MoveController.getInstance();
        initializeBoard();
    }


    private void initializeBoard() {
        /*
        // Crée tous les pions que l'on ajoute au plateau
        for (int i = 0; i < 8; i++) {
            ArrayList<Piece> row = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                boolean isWhite = i < 2; // les pièces des deux premières rangées sont blanches, les autres sont noires
                if (i == 0 || i == 7) {
                    if (j == 0 || j == 7) {
                        row.add(new Tour(isWhite, i, j, new ImageView("@../../../resources/img/images echec/cavalier_blanc.png")));
                    } else if (j == 1 || j == 6) {
                        row.add(new Cavalier(isWhite, i, j, new ImageView("@../../../resources/img/images echec/cavalier_blanc.png")));
                    } else if (j == 2 || j == 5) {
                        row.add(new Fou(isWhite, i, j, new ImageView("@../../../resources/img/images echec/tour.png")));
                    } else if (j == 3) {
                        row.add(new Reine(isWhite, i, j, new ImageView("fr/amu/iut/earthquakeapp/images/reine.png")));
                    } else {
                        row.add(new Roi(isWhite, i, j, new ImageView("fr/amu/iut/earthquakeapp/images/roi.png")));
                    }
            }
            plateau.add(row);
        }*/

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle square = new Rectangle(75, 75);
                square.setFill((i + j) % 2 == 0 ? Color.WHITE : Color.GREEN);
                chessBoard.add(square, j, i);
               // Piece indice = plateau[i,j];
               // ImageView pieceImage = indice.getImage();
               // if (pieceImage != null) {
               //     chessBoard.add(pieceImage, j, i);
                }
            }
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
