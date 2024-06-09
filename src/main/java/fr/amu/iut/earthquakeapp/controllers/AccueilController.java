package fr.amu.iut.earthquakeapp.controllers;

import fr.amu.iut.earthquakeapp.donnée.GameStats;
import fr.amu.iut.earthquakeapp.donnée.PlayerData;
import fr.amu.iut.earthquakeapp.jeu.Piece;
import fr.amu.iut.earthquakeapp.jeu.pieces.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
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
import java.util.Random;
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
    private VBox VboxParties;

    @FXML
    private Label pseudoJ1;

    @FXML
    private Label pseudoJ2;




    @FXML
    private String nomLoginJ1;

    @FXML
    private String nomLoginJ2;



    @FXML
    private TextField nomJ1;

    @FXML
    private TextField nomJ2;

    @FXML
    private Label timerLabel1;

    @FXML
    private Label timerLabel2;

    private Timeline whiteTimeline;
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
    private GameStats donnePartie = new GameStats("","","","","");
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





    private boolean movePiece(int fromRow, int fromCol, int toRow, int toCol, Piece targetPiece) {
        // Vérifiez que les coordonnées sont dans les limites du plateau
        if (toRow < 0 || toRow >= 8 || toCol < 0 || toCol >= 8) {
            return false;
        }

        // Vérifiez si le mouvement est valide
        if (targetPiece.isValide(toRow, toCol, plateau)) {
            // Mettre à jour les coordonnées de la pièce
            targetPiece.move(toRow, toCol);

            // Retirer la pièce de l'ancienne position
            plateau.get(fromRow).set(fromCol, null);

            // Placer la pièce dans la nouvelle position
            plateau.get(toRow).set(toCol, targetPiece);

            // Supprimer l'écouteur d'événements de la pièce d'origine
            if (selectedImageView != null) {
                selectedImageView.setOnMouseClicked(null);
            }

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
        if (isWhiteTurn) {
            return; // Le bot joue pour les noirs seulement
        }

        // Générer tous les mouvements valides pour les noirs
        ArrayList<int[]> allValidMoves = generateAllValidMoves(false);

        if (!allValidMoves.isEmpty()) {
            // Choisir un mouvement aléatoire parmi les mouvements valides
            Random random = new Random();
            int[] move = allValidMoves.get(random.nextInt(allValidMoves.size()));
            int fromRow = move[0];
            int fromCol = move[1];
            int toRow = move[2];
            int toCol = move[3];

            Piece piece = plateau.get(fromRow).get(fromCol);
            if (movePiece(fromRow, fromCol, toRow, toCol, piece)) {
                System.out.println("Le bot a déplacé " + piece.getNom() + " de (" + fromRow + "," + fromCol + ") à (" + toRow + "," + toCol + ")");
                if (finJeu()) {
                    playerData.setGames(donnePartie);
                    playerData.writeDataToFile("playerData.json");
                    recommencerPartie();
                } else {
                    isWhiteTurn = !isWhiteTurn;
                    whiteTimeline.playFromStart();
                    blackTimeline.stop();
                }
                // Mettre à jour l'affichage après le déplacement du bot
                affichage();
            }
        }
    }





    private ArrayList<int[]> generateAllValidMoves(boolean isWhite) {
        ArrayList<int[]> allValidMoves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = plateau.get(i).get(j);
                if (piece != null && piece.isWhite() == isWhite) {
                    for (int row = 0; row < 8; row++) {
                        for (int col = 0; col < 8; col++) {
                            if (piece.isValide(row, col, plateau)) {
                                allValidMoves.add(new int[]{i, j, row, col});
                            }
                        }
                    }
                }
            }
        }

        return allValidMoves;
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

                    if (finJeu()) {
                        playerData.setGames(donnePartie);
                        playerData.writeDataToFile("playerData.json");
                        recommencerPartie();
                    }

                    resetSelection();
                } else {
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
        }
    }


    private void affichage() {
        // Effacer l'échiquier pour le redessiner
        chessBoard.getChildren().clear();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // Créer chaque case de l'échiquier
                Rectangle square = new Rectangle(75, 75);
                square.setFill((i + j) % 2 == 0 ? Color.rgb(235, 236, 208) : Color.rgb(115, 149, 82));
                chessBoard.add(square, j, i);

                final int row = i;
                final int col = j;
                // Ajouter un événement de clic à chaque case
                square.setOnMouseClicked(event -> handleMouseClick(row, col));

                // Ajouter la pièce à la case si elle existe
                Piece piece = plateau.get(i).get(j);
                if (piece != null) {
                    chessBoard.add(piece.getImage(), j, i);
                    piece.getImage().setOnMouseClicked(event -> handleMouseClick(row, col));
                    piece.getImage().setOnMouseEntered(event -> piece.getImage().setCursor(Cursor.HAND));
                    piece.getImage().setOnMouseExited(event -> piece.getImage().setCursor(Cursor.DEFAULT));
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

    private int dataSize = 1;
    public void showData(){
        VboxParties.getChildren().clear();
        Label l = new Label(PlayerData.readDataFromFile("playerData.json"));
        l.setTextFill(Color.WHITE);
        l.getStyleClass().add("labelsParties");
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
            donnePartie = new GameStats(nomLoginJ2,nomLoginJ1,tempsRestantNoir,tempsRestantBlanc ,winner);
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

            donnePartie = new GameStats(nomLoginJ2,nomLoginJ1,tempsRestantNoir,tempsRestantBlanc ,winner);
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
        nomLoginJ2 = "LE ROBOT";
        pseudoJ2.setText(nomLoginJ2);
        startPlay = true;
        startTimer();

    }



    @FXML
    public void lanceTournoie(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/Tournoi.fxml"));
            System.setProperty("http.agent", "Gluon Mobile/1.0.3");
            TournoiController tournoiController = new TournoiController();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            TournoiController.setPreviousStage(currentStage);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Mode tournois");
            stage.getIcons().add(new Image("img/iconDame.png"));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void ButtonLogin(){
        nomLoginJ1 = nomJ1.getText();
        nomLoginJ2 = nomJ2.getText();
        pseudoJ1.setText(nomLoginJ1);
        pseudoJ2.setText(nomLoginJ2);
        TournoiController.setNomLog1(nomLoginJ1);
        TournoiController.setNomLog2(nomLoginJ2);

    }


}


