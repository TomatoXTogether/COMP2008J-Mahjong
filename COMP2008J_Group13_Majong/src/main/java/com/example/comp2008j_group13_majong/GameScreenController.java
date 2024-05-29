package com.example.comp2008j_group13_majong;

import com.example.comp2008j_group13_majong.MasterControll.GameRules;
import com.example.comp2008j_group13_majong.Tile.MahjongDeck;
import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameScreenController implements Initializable {

    @FXML
    private Button chi;

    @FXML
    private GridPane eastHandPile;

    @FXML
    private Button gang;

    @FXML
    private GridPane handPile;

    @FXML
    private Button hu;

    @FXML
    private GridPane northHandPile;

    @FXML
    private Button peng;

    @FXML
    private GridPane playerHandPile;

    @FXML
    private GridPane westHandPile;

    ImageView tileDisplay;

    ArrayList<MahjongTile> humanPlayerHand;
    ArrayList<MahjongTile> computer1Hand;
    ArrayList<MahjongTile> computer2Hand;
    ArrayList<MahjongTile> computer3Hand;

    public GameScreenController() {
    }
    @FXML
    void chiBottonAction(ActionEvent event) {

    }

    @FXML
    void gangBottonAction(ActionEvent event) {

    }

    @FXML
    void huBottonAction(ActionEvent event) {

    }


    @FXML
    void mouseClicked(MouseEvent event) {
         // Get the current Y position of the image view
        tileDisplay.setLayoutY(tileDisplay.getLayoutY() + 10);

        System.out.println(""+tileDisplay.getLayoutY()+tileDisplay.getLayoutX());
    }

    @FXML
    void pengBottonAction(ActionEvent event) {

    }

    private void loadTilesFromListsToPane (List<MahjongTile> humanTiles, List<MahjongTile> computer1Tiles,List<MahjongTile> computer2Tiles,List<MahjongTile> computer3Tiles) {
        for (int row = 0; row < humanTiles.size(); row++) {
            MahjongTile tile = humanTiles.get(row);
             tileDisplay = getTileDisplayForHuman(tile);
             tileDisplay.setOnMouseClicked(e -> {
                 mouseClicked(e);
             });
            playerHandPile.add(tileDisplay, row, 0);
        }
        for (int row = 0; row < computer1Tiles.size(); row++) {
            MahjongTile tile = computer1Tiles.get(row);
            ImageView tileDisplay = getTileDisplayForComputer(tile);
            northHandPile.add(tileDisplay, row, 0);
        }
        for (int column = 0; column < computer2Tiles.size(); column++) {
            MahjongTile tile = computer2Tiles.get(column);
            ImageView tileDisplay = getTileDisplayForComputer(tile);
            eastHandPile.add(tileDisplay, 0, column);
        }
        for (int column = 0; column < computer3Tiles.size(); column++) {
            MahjongTile tile = computer3Tiles.get(column);
            ImageView tileDisplay = getTileDisplayForComputer(tile);
            westHandPile.add(tileDisplay, 0, column);
        }
    }

    protected ImageView getTileDisplayForHuman(MahjongTile tile) {
        if(tile.getValue()!=null){
            Image image = new Image(getClass().getResourceAsStream("/images/" + tile.getValue()+tile.getSuit() + ".jpg"));
            //System.out.println(tile.getValue()+tile.getSuit());
            ImageView iv = new ImageView();
            iv.setPreserveRatio(true); // 保持比例
            iv.setFitWidth(50);       // 宽度
            iv.setFitHeight(100);      // 高度
            iv.setImage(image);         // 关联图像
            return iv;
        }else {
            Image image = new Image(getClass().getResourceAsStream("/images/" + tile.getSuit() + ".jpg"));
            //System.out.println(tile.getSuit());
            ImageView iv = new ImageView();
            iv.setPreserveRatio(true); // 保持比例
            iv.setFitWidth(50);       // 宽度
            iv.setFitHeight(100);      // 高度
            iv.setImage(image);         // 关联图像
            return iv;
        }
    }

    protected ImageView getTileDisplayForComputer(MahjongTile tile) {
            Image image = new Image(getClass().getResourceAsStream("/images/背面.jpg"));
            //System.out.println(tile.getValue()+tile.getSuit());
            ImageView iv = new ImageView();
            iv.setPreserveRatio(true);
            iv.setFitWidth(50);
            iv.setFitHeight(100);
            iv.setImage(image);
            return iv;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GameRules gameRules = new GameRules();
        MahjongDeck mahjongDeck = new MahjongDeck();
        humanPlayerHand=gameRules.humanPlayerHand;
        computer1Hand=gameRules.computer1Hand;
        computer2Hand=gameRules.computer2Hand;
        computer3Hand=gameRules.computer3Hand;

        mahjongDeck.sortTiles(humanPlayerHand);
        loadTilesFromListsToPane(humanPlayerHand,computer1Hand,computer2Hand,computer3Hand);
    }
}
