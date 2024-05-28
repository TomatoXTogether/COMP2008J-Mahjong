package com.example.comp2008j_group13_majong;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class BeginScreenController {

    @FXML
    private Button exit;

    @FXML
    private Button newGame;

    @FXML
    void exitBottonAction(ActionEvent event) {
        Stage currentStage = (Stage) exit.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void newGameBottonAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) newGame.getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));
        Parent root;
        root = loader.load();
        GameScreenController controller = loader.getController();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    }

}
