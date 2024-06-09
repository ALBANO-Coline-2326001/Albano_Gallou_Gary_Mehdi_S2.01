package fr.amu.iut.earthquakeapp.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import java.io.IOException;

public class TournoiController {

    @FXML
    private TableColumn<PlayerData, String> player1Column;
    @FXML
    private TableColumn<PlayerData, String> player2Column;
    @FXML
    private TableColumn<PlayerData, String> resultColumn;
    @FXML
    private TableView<PlayerData> tournamentTable;

    private static Stage previousStage;
    private static String nomLog1;
    private static String nomLog2;

    public void initialize() {
        // Configure les colonnes
        player1Column.setCellValueFactory(new PropertyValueFactory<>("player1"));
        player2Column.setCellValueFactory(new PropertyValueFactory<>("player2"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));

        // Ajoute des données de test
        addData(nomLog1, nomLog2, "");
    }

    public static String getNomLog2() {
        return nomLog2;
    }

    public static void setNomLog1(String nomLog1) {
        TournoiController.nomLog1 = nomLog1;
    }

    public static String getNomLog1() {
        return nomLog1;
    }

    public static void setNomLog2(String nomLog2) {
        TournoiController.nomLog2 = nomLog2;
    }

    public static void setPreviousStage(Stage stage) {
        previousStage = stage;
    }

    @FXML
    private void handleBackToMenu(ActionEvent event) {
        try {
            // Charger le fichier FXML de la vue principale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Accueil.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Show the previous stage (main window)
            previousStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception, afficher un message d'erreur, etc.
        }
    }

    private void addData(String player1, String player2, String result) {
        // Ajoute les données de joueur au tableau
        tournamentTable.getItems().add(new PlayerData(player1, player2, result));
    }

    // Classe interne pour représenter les données des joueurs
    public static class PlayerData {
        private final String player1;
        private final String player2;
        private final String result;

        public PlayerData(String player1, String player2, String result) {
            this.player1 = player1;
            this.player2 = player2;
            this.result = result;
        }

        public String getPlayer1() {
            return player1;
        }

        public String getPlayer2() {
            return player2;
        }

        public String getResult() {
            return result;
        }
    }
}
