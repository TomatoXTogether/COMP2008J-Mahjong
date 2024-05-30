package com.example.comp2008j_group13_majong;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BeginScreenController implements Initializable {

    @FXML
    private Button exit;

    @FXML
    private Button newGame;

    Media pick = new Media(new File("src/main/resources/music/bgm.mp3").toURI().toString());

    public MediaPlayer player=new MediaPlayer(pick);

    @FXML
    void exitBottonAction(ActionEvent event) {
        Stage currentStage = (Stage) exit.getScene().getWindow();
        player.pause();
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

        player.pause();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player.play();
    }
}
