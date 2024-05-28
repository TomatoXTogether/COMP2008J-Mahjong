package com.example.comp2008j_group13_majong;

import com.example.comp2008j_group13_majong.MasterControll.GameRules;
import com.example.comp2008j_group13_majong.Tile.MahjongTile;
import com.example.comp2008j_group13_majong.Tile.TilesDisplay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

    public ObservableList<TilesDisplay> tileDisplays = FXCollections.observableArrayList();


    public GameScreenController() {
        GameRules gameRules = new GameRules();
        ArrayList<MahjongTile> humanPlayerHand=gameRules.humanPlayerHand;
        ArrayList<MahjongTile> computer1Hand=gameRules.computer1Hand;
        ArrayList<MahjongTile> computer2Hand=gameRules.computer2Hand;
        ArrayList<MahjongTile> computer3Hand=gameRules.computer3Hand;
        System.out.println("humanPlayerHand: " + humanPlayerHand);
        //loadTilesFromListsToPane(humanPlayerHand);

        //loadTilesFromListsToPane(humanPlayerHand);
        //loadTilesFromListsToPane(humanPlayerHand);
        //loadTilesFromListsToPane(humanPlayerHand);
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

    }

    @FXML
    void pengBottonAction(ActionEvent event) {

    }

    private void loadTilesFromListsToPane (List<MahjongTile> tiles) {
        for (int row = 0; row < tiles.size(); row++) {
            MahjongTile tile = tiles.get(row);
            ImageView tileDisplay = getTileDisplayFor(tile); // Assuming you've correctly implemented getTileDisplayFor() to return ImageView instances
            playerHandPile.add(tileDisplay, row, 0); // At left most column and change the position of rowindex
        }

    }

    protected ImageView getTileDisplayFor(MahjongTile tile) {

        //Image image = new Image(getClass().getResourceAsStream("/images/" + tile.getValue()+tile.getSuit() + ".jpg"));
        if(tile.getValue()!=null){
            Image image = new Image(getClass().getResourceAsStream("/images/" + tile.getValue()+tile.getSuit() + ".jpg"));
            System.out.println(tile.getValue()+tile.getSuit());
            //ImageView tileDisplay = new ImageView(image);
            ImageView iv = new ImageView();
            iv.setPreserveRatio(true); // 保持比例
            iv.setFitWidth(50);       // 宽度
            iv.setFitHeight(100);      // 高度
            iv.setImage(image);         // 关联图像

            return iv;
        }else {
            Image image = new Image(getClass().getResourceAsStream("/images/" + tile.getSuit() + ".jpg"));
            System.out.println(tile.getSuit());
            //ImageView tileDisplay = new ImageView(image);
            ImageView iv = new ImageView();
            iv.setPreserveRatio(true); // 保持比例
            iv.setFitWidth(50);       // 宽度
            iv.setFitHeight(100);      // 高度
            iv.setImage(image);         // 关联图像

            return iv;
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GameRules gameRules = new GameRules();
        ArrayList<MahjongTile> humanPlayerHand=gameRules.humanPlayerHand;
        loadTilesFromListsToPane(humanPlayerHand);

    }
}
