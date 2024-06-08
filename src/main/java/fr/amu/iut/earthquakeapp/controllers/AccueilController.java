package fr.amu.iut.earthquakeapp.controllers;

import fr.amu.iut.earthquakeapp.Accueil;
import fr.amu.iut.earthquakeapp.donnée.GameStats;
import fr.amu.iut.earthquakeapp.donnée.PlayerData;
import fr.amu.iut.earthquakeapp.jeu.Piece;
import fr.amu.iut.earthquakeapp.jeu.pieces.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Classe qui permet de charger les données dans un barchart (celui du dashboard dans notre appli)
 *
 * @version 1.0
 */

public class AccueilController {
    private boolean isWhiteTurn = true;
    private boolean startPlay = false;
    @FXML
    private Label donnee;
    @FXML
    private VBox VboxParties;

    @FXML
    private Button joueurContreJoueur;

    @FXML
    private Button joueurContreBot;
    @FXML
    private Tab Partie;

    @FXML
    private Label timerLabel1;

    @FXML
    private Label timerLabel2;


    private Timeline whiteTimeline;
    private Timeline timeline;
    private Timeline timeline2;
    private Timeline blackTimeline;
    private String tempsRestantNoir;
    private String tempsRestantBlanc;
    private boolean isBotMode = false;

    private Piece selectedPiece = null;
    private ImageView selectedImageView = null;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private IntegerProperty nbpartie = new SimpleIntegerProperty(0);
    private PlayerData playerData = new PlayerData();
    private GameStats donnePartie = new GameStats("","","");
    private String time;
    private String winner;


    @FXML
    private GridPane chessBoard;

    private ArrayList<ArrayList<Piece>> plateau = new ArrayList<>();


    @FXML
    private TabPane tabPane;

    @FXML
    private ComboBox<String> timeOptions;

    @FXML
    public void initialize() {
        for (Tab tab : tabPane.getTabs()) {
            tab.setClosable(false);
        }

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
            boolean isWhite = (i >= 6);  // White pieces on rows 6 and 7

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
    @FXML
    private void handleMouseClick(int row, int col) {
        if (!startPlay) {
            return;
        }

        System.out.println("Ligne cliquée : " + row + ", Colonne : " + col);

        Piece clickedPiece = plateau.get(row).get(col);
        ImageView clickedImageView = (clickedPiece != null) ? clickedPiece.getImage() : null;

        clearHighlights();

        if (selectedPiece != null) {
            if (row != selectedRow || col != selectedCol) {
                if (movePiece(selectedRow, selectedCol, row, col, selectedPiece)) {
                    chessBoard.getChildren().remove(selectedImageView);
                    if (clickedImageView != null) {
                        System.out.println("Pièce capturée : " + clickedPiece.getNom());
                        chessBoard.getChildren().remove(clickedImageView);
                    }
                    chessBoard.add(selectedImageView, col, row);

                    plateau.get(selectedRow).set(selectedCol, null);
                    plateau.get(row).set(col, selectedPiece);

                    // Ne mettez à jour le tour et ne démarrez les timers que si le mouvement est valide
                    isWhiteTurn = !isWhiteTurn;

                    if (isWhiteTurn) {
                        blackTimeline.stop();
                        whiteTimeline.playFromStart();
                    } else {
                        whiteTimeline.stop();
                        blackTimeline.playFromStart();
                        if (isBotMode) { // Si c'est le tour du bot et que le mode joueur contre bot est activé
                            botPlay(); // Fait jouer le bot de manière aléatoire
                        }
                    }

                    resetSelection();
                } else {
                    // Ne réinitialise la sélection que si le mouvement n'est pas valide
                    resetSelection();
                }
            } else {
                resetSelection();
            }
        } else if (clickedPiece != null && clickedPiece.isWhite() == isWhiteTurn) {
            selectedPiece = clickedPiece;
            selectedImageView = clickedImageView;
            selectedRow = row;
            selectedCol = col;

            highlightValidMoves(clickedPiece);

            // Aucun changement de tour ici
        }

        if (finJeu()) {
            donnePartie = new GameStats("opponent",time,winner);
            recommencerPartie();
        }
    }




    private boolean movePiece(int fromRow, int fromCol, int toRow, int toCol, Piece targetPiece) {
        if (targetPiece.isValide(toRow, toCol, plateau)) {
            // Appeler la méthode move de la pièce
            targetPiece.move(toRow, toCol);  // Mettre à jour les coordonnées de la pièce

            plateau.get(fromRow).set(fromCol, null);  // Retirer la pièce de l'ancienne position
            plateau.get(toRow).set(toCol, targetPiece);  // Placer la pièce dans la nouvelle position

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
    private void highlightValidMoves(Piece piece) {
        clearHighlights(); // Efface les anciennes surbrillances

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (piece.isValide(i, j, plateau)) {
                    Rectangle highlight = new Rectangle(75, 75, Color.YELLOW);
                    highlight.setOpacity(0.5); // Ajustez l'opacité selon vos préférences
                    highlight.setMouseTransparent(true); // Permet à la souris de cliquer à travers le rectangle
                    chessBoard.add(highlight, j, i);
                }
            }
        }
    }


    private void clearHighlights() {
        chessBoard.getChildren().removeIf(node -> node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.YELLOW));
    }


    private void botPlay() {
        // Parcourez le plateau pour trouver une pièce du bot et un mouvement valide
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = plateau.get(i).get(j);
                if (piece != null && !piece.isWhite()) { // Si la pièce est une pièce du bot
                    for (int x = 0; x < 8; x++) {
                        for (int y = 0; y < 8; y++) {
                            if (piece.isValide(x, y, plateau)) { // Si le mouvement est valide
                                // Jouez le mouvement
                                movePiece(i, j, x, y, piece);
                                // Changez de tour
                                isWhiteTurn = !isWhiteTurn;
                                return; // Sortir de la méthode après avoir joué un mouvement
                            }
                        }
                    }
                }
            }
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
                    indice.getImage().setOnMouseEntered(event -> indice.getImage().setCursor(Cursor.HAND));
                    indice.getImage().setOnMouseExited(event -> indice.getImage().setCursor(Cursor.DEFAULT));


                }
            }
        }
    }



    public void recommencerPartie() {
        initializeBoard();
        isWhiteTurn=true;
        chessBoard.getChildren().clear();
        affichage();
        nbpartie.set(nbpartie.get() + 1);
        if (winner == "1 - 0"){
            playerData.setScore(playerData.getScore() + 1);
        }
        playerData.setGamesPlayed(nbpartie.get());
        playerData.setGames(donnePartie);
        playerData.writeDataToFile("playerData.json");
        // Réinitialiser le plateau de jeu
        // Rafraîchir l'affichage de l'échiquier
        startPlay = false; // Réinitialiser le contrôle de jeu
        afficherNomsDesPieces();
    }


    private void resetSelection() {
        selectedPiece = null;
        selectedImageView = null;
        selectedRow = -1;
        selectedCol = -1;
    }


    public void showData(){
        VboxParties.getChildren().clear();
        Label l = new Label(PlayerData.readDataFromFile("playerData.json"));
        l.setTextFill(Color.WHITE);
        VboxParties.getChildren().add(l);

    }


    private String timeToString(int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void startTimer() {
        if (whiteTimeline != null) {
            whiteTimeline.stop(); // Arrête le timer précédent du joueur blanc s'il est en cours
        }
        if (blackTimeline != null) {
            blackTimeline.stop(); // Arrête le timer précédent du joueur noir s'il est en cours
        }

        // Lire la durée sélectionnée par l'utilisateur dans la ComboBox
        String selectedTime = timeOptions.getValue();
        int startTime;
        if (selectedTime != null) {
            startTime = Integer.parseInt(selectedTime.split(" ")[0]) * 60; // Convertir les minutes en secondes
        } else {
            startTime = 600; // Durée par défaut de 10 minutes en secondes
        }

        AtomicInteger whiteTimeSeconds = new AtomicInteger(startTime);
        AtomicInteger blackTimeSeconds = new AtomicInteger(startTime);
        timerLabel1.setText(timeToString(whiteTimeSeconds.get()));
        timerLabel2.setText(timeToString(blackTimeSeconds.get()));

        whiteTimeline = new Timeline();
        whiteTimeline.setCycleCount(Timeline.INDEFINITE);
        whiteTimeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), e -> {
                    int currentTime = whiteTimeSeconds.decrementAndGet();
                    timerLabel1.setText(timeToString(currentTime));
                    if (currentTime <= 0) {
                        whiteTimeline.stop();
                        recommencerPartie();
                    }
                })
        );

        blackTimeline = new Timeline();
        blackTimeline.setCycleCount(Timeline.INDEFINITE);
        blackTimeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), e -> {
                    int currentTime = blackTimeSeconds.decrementAndGet();
                    timerLabel2.setText(timeToString(currentTime));
                    if (currentTime <= 0) {
                        blackTimeline.stop();
                        recommencerPartie();
                    }
                })
        );


    }

    public void stopWhiteTimer() {
        if (whiteTimeline != null) {
            whiteTimeline.stop();
        }
    }

    // Méthode pour arrêter le timer du joueur noir
    public void stopBlackTimer() {
        if (blackTimeline != null) {
            blackTimeline.stop();
        }
    }






//    public void start(){
//        nbpartie.set(nbpartie.get() + 1);
//        playerData.setGamesPlayed(nbpartie.get());
//        playerData.writeDataToFile("playerData.json");
//        startPlay = true;
//    }

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
            stopWhiteTimer();
            stopBlackTimer();
            tempsRestantNoir = timerLabel2.getText();
            tempsRestantBlanc = timerLabel1.getText();
            time = tempsRestantBlanc;
            winner = "1 - 0";
            System.out.println(tempsRestantNoir + "sec");
            System.out.println(tempsRestantBlanc + "sec");


            return true;
            // Ajoutez ici les actions à entreprendre lorsque le roi blanc est présent et le roi noir est absent
        } else if (!roiBlancPresent && !roiNoirAbsent) {
            System.out.println("roi noir gagne");
            stopWhiteTimer();
            stopBlackTimer();
            tempsRestantNoir = timerLabel2.getText();
            tempsRestantBlanc = timerLabel1.getText();
            time = tempsRestantBlanc;
            winner = "0 - 1";
            System.out.println(tempsRestantNoir + "sec");
            System.out.println(tempsRestantBlanc + "sec");

            return true;
            // Ajoutez ici les actions à entreprendre lorsque les conditions ne sont pas remplies
        }
        return false;
    }


    @FXML
    public void JoueurContreJoueur() {
        isBotMode = false; // Le mode Joueur contre Joueur est activé
        recommencerPartie();
        startPlay = true;
        startTimer();
    }

    @FXML
    public void JoueurContreBot() {
        isBotMode = true; // Le mode Joueur contre Bot est activé
        recommencerPartie();
        startPlay = true;
        startTimer();

    }

    @FXML
    public void lanceTournoie(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/Tournoi.fxml"));
            System.setProperty("http.agent", "Gluon Mobile/1.0.3");
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Tournament Mode");
            stage.getIcons().add(new Image("img/iconDame.png"));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}


