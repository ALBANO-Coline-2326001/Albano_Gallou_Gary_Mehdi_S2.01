package fr.amu.iut.earthquakeapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField nomJ1;

    @FXML
    private TextField nomJ2;

    @FXML
    private Button bLogin;

    private String nom1;

    private String nom2;

    @FXML
    public void ButtonLogin(){
        nom1 = nomJ1.getText();
        nom2 = nomJ2.getText();
    }
}
