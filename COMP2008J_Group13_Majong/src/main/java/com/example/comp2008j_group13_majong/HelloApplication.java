package com.example.comp2008j_group13_majong;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    //Media pick = new Media(new File("src/main/resources/music/bgm.mp3").toURI().toString());

    //public MediaPlayer player=new MediaPlayer(pick);
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("BeginScreen.fxml"));
        Parent root;
        root = loader.load();
        BeginScreenController controller = loader.getController();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}