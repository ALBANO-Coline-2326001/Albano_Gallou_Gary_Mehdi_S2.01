package fr.amu.iut.earthquakeapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Classe main du logiciel.
 * @version 1.0
 */

public class Accueil extends Application {

    private static Scene scene;

    /**
     * Permet de charger le fichier fxml principale ainsi que toutes les données, méthodes qui s'en suit
     * @param stage Fenêtre du logiciel
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/Accueil.fxml"));
        System.setProperty("http.agent", "Gluon Mobile/1.0.3");
        scene = new Scene(loader.load());
        stage.setTitle("Echec Viewer");
        stage.getIcons().add(new Image("img/iconDame.png"));
        stage.setScene(scene);
        stage.show();
    }
}
