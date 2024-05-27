package com.example.comp2008j_group13_majong;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameScreenController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}