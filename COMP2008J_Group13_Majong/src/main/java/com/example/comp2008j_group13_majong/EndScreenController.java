package com.example.comp2008j_group13_majong;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class EndScreenController {

    @FXML
    private Button exit;

    @FXML
    private Text firstPlayerName;

    @FXML
    private Text firstPlayerScore;

    @FXML
    private Text fourthPlayerName;

    @FXML
    private Text fourthPlayerScore;

    @FXML
    private Text humanLose;

    @FXML
    private Text humanWin;

    @FXML
    private Button newGame;

    @FXML
    private Text secondPlayerName;

    @FXML
    private Text secondPlayerScore;

    @FXML
    private Text thirdPlayerName;

    @FXML
    private Text thirdPlayerScore;

    @FXML
    void exitBottonAction(ActionEvent event) {
        Stage currentStage = (Stage) exit.getScene().getWindow();
        //player.pause();
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
        newStage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/Mahjong icon.jpg")));
        newStage.show();

        //player.pause();
    }

}
