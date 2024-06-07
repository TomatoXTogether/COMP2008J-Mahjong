package com.example.comp2008j_group13_majong;

import com.example.comp2008j_group13_majong.MasterControll.GameRules;
import com.example.comp2008j_group13_majong.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EndScreenController implements Initializable {

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

    private GameRules gameRules = GameRules.getInstance();

    private ArrayList<User> players;



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

    public void updateScoreBoard(){
        firstPlayerName.setText( players.get(0).name);
        firstPlayerScore.setText(String.valueOf(players.get(0).score));
        secondPlayerName.setText( players.get(1).name);
        secondPlayerScore.setText(String.valueOf(players.get(1).score));
        thirdPlayerName.setText( players.get(2).name);
        thirdPlayerScore.setText(String.valueOf(players.get(2).score));
        fourthPlayerName.setText( players.get(3).name);
        fourthPlayerScore.setText(String.valueOf(players.get(3).score));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        players = gameRules.sortPlayers(gameRules.players);
        updateScoreBoard();
        MediaPlayer player;

        if(players.get(0).equals(gameRules.humanPlayer)){
            humanWin.setVisible(true);
            humanLose.setVisible(false);
            Media pick = new Media(new File("src/main/resources/music/win.mp3").toURI().toString());
            player=new MediaPlayer(pick);
        }else {
            humanWin.setVisible(false);
            humanLose.setVisible(true);
            Media pick = new Media(new File("src/main/resources/music/lose.mp3").toURI().toString());
            player=new MediaPlayer(pick);
        }
        player.play();

    }
}
