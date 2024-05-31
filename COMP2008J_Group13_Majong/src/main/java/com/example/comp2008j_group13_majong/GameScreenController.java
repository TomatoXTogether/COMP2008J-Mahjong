package com.example.comp2008j_group13_majong;

import com.example.comp2008j_group13_majong.MasterControll.GameRules;
import com.example.comp2008j_group13_majong.Tile.MahjongDeck;
import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import com.example.comp2008j_group13_majong.User.Computer;
import com.example.comp2008j_group13_majong.User.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
//import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameScreenController implements Initializable {
    @FXML
    private Label east;

    @FXML
    private Label north;

    @FXML
    private Label south;

    @FXML
    private Label west;

    @FXML
    private GridPane pairingTilesInEast;

    @FXML
    private GridPane pairingTilesInNorth;

    @FXML
    private GridPane pairingTilesInSouth;

    @FXML
    private GridPane pairingTilesInWest;

    @FXML
    private ImageView chiImage;

    @FXML
    private ImageView gangImage;

    @FXML
    private ImageView huImage;

    @FXML
    private ImageView pengImage;

    @FXML
    private Label remainTilesNumber;

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
    private Button play;

    @FXML
    private GridPane playerHandPile;

    @FXML
    private Button drawButton;

    @FXML
    private GridPane westHandPile;

    @FXML
    private Label round;

    @FXML
    private Label score;

    @FXML
    private GridPane usedTiles;

    @FXML
    private GridPane usedTilesInEast;

    @FXML
    private GridPane usedTilesInNorth;

    @FXML
    private GridPane usedTilesInWest;

    private MahjongDeck mahjongDeck = new MahjongDeck();


    private ImageView currentRaisedTile;

    int index;

    private GameRules gameRules=new GameRules();

    private Player humanPlayer;
    private Computer computer1;
    private Computer computer2;
    private Computer computer3;


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
    void playBottonAction(ActionEvent event) {
        if(index!=-1){
            MahjongTile usedTile = humanPlayer.removeTile(index);
            //System.out.println(humanPlayerHand);
            playerHandPile.getChildren().remove(currentRaisedTile);
            MahjongTile[][] shunzi = computer2.ifChi(usedTile);
            play.setVisible(false);
            //gameRules.dealerNextRound();
            updateOnePlayerHand(playerHandPile,humanPlayer.handTiles);
            currentRaisedTile = null;
        }
    }

    @FXML
    void drawButtonAction(ActionEvent event) {
        gameRules.dealerNextRound(this);
        // 摸牌后重新排序玩家的手牌
        mahjongDeck.sortHandTiles(humanPlayer.handTiles);
        playersTurn();
        updateAllPlayerHands();
        updateRemainTiles();
    }

    private void updateAllPlayerHands() {
        // 清空所有玩家手牌的显示
        playerHandPile.getChildren().clear();
        northHandPile.getChildren().clear();
        eastHandPile.getChildren().clear();
        westHandPile.getChildren().clear();

        // 重新加载每个玩家的手牌
        loadTilesFromListsToPaneForHuman(humanPlayer.handTiles);
        loadTilesFromListsToPaneForComputer(computer1.handTiles, northHandPile);
        loadTilesFromListsToPaneForComputer(computer2.handTiles, eastHandPile);
        loadTilesFromListsToPaneForComputer(computer3.handTiles, westHandPile);
        loadTilesFromListsToPaneForUsedTiles(humanPlayer.usedTiles, usedTiles);
    }

    private void updateOnePlayerHand(GridPane pane,ArrayList<MahjongTile> pile) {
        pane.getChildren().clear();
        // 重新加载每个玩家的手牌
        loadTilesFromListsToPaneForHuman(pile);
        loadTilesFromListsToPaneForUsedTiles(humanPlayer.usedTiles, usedTiles);
    }

    @FXML
    void mouseClicked(MouseEvent event) {

    }

    @FXML
    void pengBottonAction(ActionEvent event) {

    }

    private void playersTurn(){
        int currentPlayerIndex=gameRules.getCurrentPlayerIndex();

        if(currentPlayerIndex==1){
            east.setTextFill(Color.RED);
            north.setTextFill(Color.BLACK);
            west.setTextFill(Color.BLACK);
            south.setTextFill(Color.BLACK);
        }else if(currentPlayerIndex==2){
            north.setTextFill(Color.RED);
            east.setTextFill(Color.BLACK);
            west.setTextFill(Color.BLACK);
            south.setTextFill(Color.BLACK);
        }else if(currentPlayerIndex==3){
            west.setTextFill(Color.RED);
            east.setTextFill(Color.BLACK);
            north.setTextFill(Color.BLACK);
            south.setTextFill(Color.BLACK);
        }else {
            south.setTextFill(Color.RED);
            east.setTextFill(Color.BLACK);
            west.setTextFill(Color.BLACK);
            north.setTextFill(Color.BLACK);
        }
    }

    private void updateRemainTiles(){
        remainTilesNumber.setText("Remain: "+gameRules.getRemainingTilesNumber());
    }


    private void loadTilesFromListsToPaneForHuman (List<MahjongTile> humanTiles) {
        for (int row = 0; row < humanTiles.size(); row++) {
            MahjongTile tile = humanTiles.get(row);
            ImageView tileDisplay = getTileDisplayForHuman(tile);
            tileDisplay.setOnMouseClicked(this::mouseClicked);

            playerHandPile.add(tileDisplay, row, 1);
            int finalRow = row;
            tileDisplay.setOnMouseClicked(event -> {
                if (currentRaisedTile == null) {
                    // 没有当前上升的牌
                    playerHandPile.getChildren().remove(tileDisplay);
                    playerHandPile.add(tileDisplay, finalRow, 0);
                    System.out.println(finalRow);
                    currentRaisedTile = tileDisplay;

                    index=finalRow;
                    play.setVisible(true);
                } else if (currentRaisedTile == tileDisplay) {
                    // 点击的牌是当前上升的牌，下降它
                    playerHandPile.getChildren().remove(tileDisplay);
                    playerHandPile.add(tileDisplay, finalRow, 1);
                    currentRaisedTile = null;
                    index=-1;
                    play.setVisible(false);

                }
            });
        }
    }

    private void loadTilesFromListsToPaneForComputer(List<MahjongTile> computerTiles, GridPane pane){
        if(computerTiles==computer1.handTiles){
            for (int row = 0; row < computerTiles.size(); row++) {
                MahjongTile tile = computerTiles.get(row);
                ImageView tileDisplay = getTileDisplayForComputer(tile);
                pane.add(tileDisplay, row, 0);
            }
        }else {
            for (int column = 0; column < computerTiles.size(); column++) {
                MahjongTile tile = computerTiles.get(column);
                ImageView tileDisplay = getTileDisplayForComputer(tile);
                pane.add(tileDisplay, 0, column);
            }
        }
    }

    public void loadTilesFromListsToPaneForUsedTiles(List<MahjongTile> usedTiles, GridPane pane){
        for (int row = 0; row < usedTiles.size(); row++) {
            MahjongTile tile = usedTiles.get(row);
            ImageView tileDisplay = getTileDisplayForUsedTiles(tile);
            pane.add(tileDisplay, row, 0);
        }
    }


    public void updateUsedTiles(MahjongTile tile, int playerIndex) {
        switch (playerIndex) {
            case 1: // 北玩家
                computer1.handTiles.add(tile);
                loadTilesFromListsToPaneForUsedTiles(computer1.usedTiles, usedTilesInNorth);
                break;
            case 0: // 东玩家
                computer2.handTiles.add(tile);
                loadTilesFromListsToPaneForUsedTiles(computer2.usedTiles, usedTilesInEast);
                break;
            case 2: // 西玩家
                computer3.handTiles.add(tile);
                loadTilesFromListsToPaneForUsedTiles(computer3.usedTiles, usedTilesInWest);
                break;
        }
    }

    public List<MahjongTile> getHumanPlayerHand() {
        return humanPlayer.handTiles;
    }

    private ImageView getTileDisplayForUsedTiles(MahjongTile tile) {
        if (tile.getValue() != null) {
            Image image = new Image(getClass().getResourceAsStream("/images/" + tile.getValue() + tile.getSuit() + ".jpg"));
            //System.out.println(tile.getValue()+tile.getSuit());
            ImageView iv = new ImageView();
            iv.setPreserveRatio(true); // 保持比例
            iv.setFitWidth(35);       // 宽度
            iv.setFitHeight(80);      // 高度
            iv.setImage(image);         // 关联图像
            return iv;
        }else {
            Image image = new Image(getClass().getResourceAsStream("/images/" + tile.getSuit() + ".jpg"));
            //System.out.println(tile.getSuit());
            ImageView iv = new ImageView();
            iv.setPreserveRatio(true); // 保持比例
            iv.setFitWidth(35);       // 宽度
            iv.setFitHeight(80);      // 高度
            iv.setImage(image);         // 关联图像
            return iv;
        }
    }

    private ImageView getTileDisplayForHuman(MahjongTile tile) {
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

    private ImageView getTileDisplayForComputer(MahjongTile tile) {
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

        humanPlayer = gameRules.humanPlayer;
        computer1 = gameRules.computer1;
        computer2 = gameRules.computer2;
        computer3 = gameRules.computer3;

        loadTilesFromListsToPaneForHuman(humanPlayer.handTiles);
        loadTilesFromListsToPaneForComputer(computer1.handTiles,northHandPile);
        loadTilesFromListsToPaneForComputer(computer2.handTiles,eastHandPile);
        loadTilesFromListsToPaneForComputer(computer3.handTiles,westHandPile);
        loadTilesFromListsToPaneForUsedTiles(humanPlayer.usedTiles, usedTiles);
        loadTilesFromListsToPaneForUsedTiles(computer1.usedTiles, usedTilesInNorth);
        loadTilesFromListsToPaneForUsedTiles(computer2.usedTiles, usedTilesInEast);
        loadTilesFromListsToPaneForUsedTiles(computer3.usedTiles, usedTilesInWest);
        //playerIndex = gameRules.getDealerIndex();
    }

}
