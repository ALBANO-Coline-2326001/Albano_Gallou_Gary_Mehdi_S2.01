package fr.amu.iut.earthquakeapp.controllers;

import fr.amu.iut.earthquakeapp.Accueil;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;




/**
 * Classe qui permet de charger les données dans un barchart (celui du dashboard dans notre appli)
 *
 * @version 1.0
 */

public class AccueilController {
    private boolean isWhiteTurn = true;
    private boolean startPlay = false;

    private MoveController moveController;
    @FXML
    private Button jouer;
    @FXML
    private Tab Partie;
    @FXML
    private Label donnee;
    @FXML
    private ComboBox<String> timeOptions;

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

    @FXML
    private Label timerLabel2;
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
        startPlay = false;

    }

    public void afficherNomsDesPieces() {
        for (ArrayList<Piece> ligne : plateau) {
            for (Piece piece : ligne) {
                if (piece != null){
                    System.out.print(piece.getNom() + " ");
                }
            }
            System.out.println();  // Pour passer à la ligne suivante après chaque ligne du plateau
        }
    }

    private void initializeBoard() {
        if(!plateau.isEmpty()){
            plateau.clear();
        }
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
        System.out.println(finJeu());
        if (!startPlay) {
            System.out.println("non");
        }
        else {
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
        if (finJeu()){
            recommencerPartie();
        }
    }

    public void recommencerPartie() {
        initializeBoard();
        chessBoard.getChildren().clear();
        affichage();
        // Réinitialiser le plateau de jeu
        // Rafraîchir l'affichage de l'échiquier
        startPlay = false; // Réinitialiser le contrôle de jeu
        afficherNomsDesPieces();
        timeline.stop();
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

            // Supprimer l'écouteur d'événements de la pièce d'origine
            selectedImageView.setOnMouseClicked(null);

            // Réaffecter un nouvel écouteur d'événements à la nouvelle position de la pièce
            ImageView newImageView = targetPiece.getImage();
            newImageView.setOnMouseClicked(event -> handleMouseClick(toRow, toCol));

            // Mise à jour de selectedImageView pour la nouvelle pièce déplacée
            selectedImageView = newImageView;




            return true;
        }
        return false;
    }

    public void startTimer() {
        if (timeline != null) {
            timeline.stop(); // Arrête le timer précédent s'il est en cours
        }

        // Lire la durée sélectionnée par l'utilisateur dans la ComboBox
        String selectedTime = timeOptions.getValue();
        int startTime;
        if (selectedTime != null) {
            startTime = Integer.parseInt(selectedTime.split(" ")[0]) * 60; // Convertir les minutes en secondes
        } else {
            startTime = 600; // Durée par défaut de 10 minutes en secondes
        }

        AtomicInteger timeSeconds = new AtomicInteger(startTime);
        timerLabel1.setText(timeToString(timeSeconds.get()));
        timerLabel2.setText(timeToString(timeSeconds.get()));

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), e -> {
                    int currentTime = timeSeconds.decrementAndGet();
                    timerLabel1.setText(timeToString(currentTime));
                    timerLabel2.setText(timeToString(currentTime));
                    if (currentTime <= 0) {
                        timeline.stop();
                        recommencerPartie();
                    }
                })
        );
        timeline.playFromStart();
    }



    public void showData(){
            donnee.setText(PlayerData.readDataFromFile("playerData.json"));
            donnee.setTextFill(Color.WHITE);

    }

    private String timeToString(int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void start(){
        startTimer();
        nbpartie.set(nbpartie.get() + 1);
        playerData.setGamesPlayed(nbpartie.get());
        playerData.writeDataToFile("playerData.json");
        startPlay = true;
    }

    public boolean finJeu(){


        boolean roiBlancPresent = false;
        boolean roiNoirAbsent = true;

        // Expressions régulières pour correspondre aux noms des pièces
        Pattern patternRoiBlanc = Pattern.compile("Roi\\d+blanc");
        Pattern patternRoiNoir = Pattern.compile("Roi\\d+noir");

        for (ArrayList<Piece> ligne : plateau) {
            for (Piece piece : ligne) {
                if (piece != null) {
                    String nom = piece.getNom();
                    Matcher matcherRoiBlanc = patternRoiBlanc.matcher(nom);
                    Matcher matcherRoiNoir = patternRoiNoir.matcher(nom);

                    if (matcherRoiBlanc.matches()) {
                        roiBlancPresent = true;
                    }
                    if (matcherRoiNoir.matches()) {
                        roiNoirAbsent = false;
                    }
                }
            }
        }


        if (roiBlancPresent && roiNoirAbsent) {
            System.out.println("Le roi blanc est présent et le roi noir est absent. ROI BLANC GAGNE");
            return true;
            // Ajoutez ici les actions à entreprendre lorsque le roi blanc est présent et le roi noir est absent
        } else if (!roiBlancPresent && !roiNoirAbsent) {
            System.out.println("roi noir gagne");
            return true;
            // Ajoutez ici les actions à entreprendre lorsque les conditions ne sont pas remplies
        }
        return false;
    }
}


