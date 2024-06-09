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
    private TableColumn player1Column;
    @FXML
    private TableColumn player2Column;
    @FXML
    private TableColumn resultColumn;
    @FXML
    private TableView tournamentTable;

    private static Stage previousStage;
    private static String nomLog1;

    public void initialize() {
        // Configure les colonnes


        // Ajoute des données de test
       addData(nomLog1,nomLog2,"i");
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

    private static String nomLog2;

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

//    public void addPlayers(){
//        player1Column.
//    }
    private void addData(String player1, String player2, String result) {
        // Obtient la liste observable associée à la table
        ObservableList<ObservableList<String>> data = tournamentTable.getItems();

        // Crée une liste observable contenant les données du joueur
        ObservableList<String> row = FXCollections.observableArrayList(player1, player2, result);

        // Ajoute la liste observable à la liste observable principale
        data.add(row);
    }


}
