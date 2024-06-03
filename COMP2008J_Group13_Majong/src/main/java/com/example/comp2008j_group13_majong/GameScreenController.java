package com.example.comp2008j_group13_majong;

import com.example.comp2008j_group13_majong.MasterControll.GameRules;
import com.example.comp2008j_group13_majong.Tile.MahjongDeck;
import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import com.example.comp2008j_group13_majong.User.Computer;
import com.example.comp2008j_group13_majong.User.Player;
import com.example.comp2008j_group13_majong.User.User;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
//import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameScreenController implements Initializable {

    @FXML
    private AnchorPane animationPane;

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
    public ImageView chiImage;

    @FXML
    private ImageView gangImage;

    @FXML
    private ImageView huImage;

    @FXML
    private ImageView pengImage;

    @FXML
    private Label remainTilesNumber;

    @FXML
    public Button chi;

    @FXML
    private GridPane eastHandPile;

    @FXML
    private Button gang;

    @FXML
    public Button pass;

    @FXML
    public ImageView passImage;

    @FXML
    private GridPane handPile;

    @FXML
    private Button hu;

    @FXML
    private GridPane northHandPile;

    @FXML
    public Button peng;

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
        if(index!=-1){
            User last = gameRules.last(humanPlayer.index);
            MahjongTile chiTile = last.usedTiles.get(last.usedTiles.size() - 1);
            if (humanPlayer.isChi) {
                humanPlayer.chi(chiTile);
                last.usedTiles.remove(last.usedTiles.size() - 1);
            }
            chi.setVisible(false);
            chiImage.setVisible(false);
            updateOnePlayerHand(playerHandPile,humanPlayer.handTiles);
            updateUsedTiles(last.index);
            updateInOrderTiles(3) ;
        }
    }

    @FXML
    public void passButtonAction(ActionEvent actionEvent) {
        if(index!=-1){
            gameRules.dealerNextRound(this);
            // 摸牌后重新排序玩家的手牌
            mahjongDeck.sortHandTiles(humanPlayer.handTiles);
            mahjongDeck.sortHandTiles(computer1.handTiles);
            mahjongDeck.sortHandTiles(computer2.handTiles);
            mahjongDeck.sortHandTiles(computer3.handTiles);
            playersTurn();
            updateAllPlayerHands();
            updateRemainTiles();
            updateOnePlayerHand(playerHandPile,humanPlayer.handTiles);
            pass.setVisible(false);
            passImage.setVisible(false);
            chi.setVisible(false);
            chiImage.setVisible(false);
            peng.setVisible(false);
            pengImage.setVisible(false);
            gang.setVisible(false);
            gangImage.setVisible(false);
        }
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
            playerHandPile.getChildren().remove(currentRaisedTile);
            if (!huTestAction(event, usedTile, humanPlayer)) {
                if(!pengTestAction(event, usedTile)){
                    MahjongTile[][] shunzi = computer2.ifChi(usedTile);
                }
            }
            play.setVisible(false);
            updateOnePlayerHand(playerHandPile,humanPlayer.handTiles);
            currentRaisedTile = null;
        }
    }

    @FXML
    public boolean pengTestAction(ActionEvent event,MahjongTile usedTile){
        //MahjongTile usedTile = humanPlayer.removeTile(index);
        for (int i = 0; i < gameRules.computers.size(); i++) {
            Computer computer = gameRules.computers.get(i);
            MahjongTile[] pengzi = computer.ifPeng(usedTile);
            if (pengzi != null) {
                gameRules.pengAction(this, computer, humanPlayer);
                return true;
            }
        }
        return false;
    }

    public boolean huTestAction(ActionEvent event, MahjongTile usedTile, User lastPlayer) {
        if (lastPlayer instanceof Player) {
            for (int i = 0; i < gameRules.computers.size(); i++) {
                Computer computer = gameRules.computers.get(i);
                ArrayList<MahjongTile> tilesToCheck = computer.ifHu(usedTile);
                if (tilesToCheck != null) {
                    gameRules.huAction(this, computer, lastPlayer);
                    return true;
                }
            }
        } else if (lastPlayer instanceof Computer) {
            List<User> players = new ArrayList<>();
            players.add(humanPlayer);
            players.add(computer1);
            players.add(computer2);
            players.add(computer3);
            for (User player : players) {
                if (player != lastPlayer) {
                    ArrayList<MahjongTile> tilesToCheck = player.ifHu(usedTile);
                    if (tilesToCheck != null) {
                        gameRules.huAction(this, player, lastPlayer);
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @FXML
    private void removeUsedTile(MahjongTile tile) {
        // 更新usedTiles列表和界面显示
        humanPlayer.usedTiles.remove(tile);
        updateUsedTiles(humanPlayer.getIndex());
    }

    @FXML
    void drawButtonAction(ActionEvent event) {
        gameRules.dealerNextRound(this);
        // 摸牌后重新排序玩家的手牌
        mahjongDeck.sortHandTiles(humanPlayer.handTiles);
        mahjongDeck.sortHandTiles(computer1.handTiles);
        mahjongDeck.sortHandTiles(computer2.handTiles);
        mahjongDeck.sortHandTiles(computer3.handTiles);
        playersTurn();
        updateAllPlayerHands();
        updateRemainTiles();
        updateOnePlayerHand(playerHandPile,humanPlayer.handTiles);
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
        User currentUser = gameRules.current(gameRules.currentPlayerIndex);
        gameRules.currentPlayerIndex = humanPlayer.index;
        gameRules.pengAction(this,humanPlayer,currentUser);
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
        pane.getChildren().clear();
        for (int row = 0; row < usedTiles.size(); row++) {
            MahjongTile tile = usedTiles.get(row);
            ImageView tileDisplay = getTileDisplayForUsedTiles(tile);
            pane.add(tileDisplay, row, 0);
        }
    }

    public void loadTilesFromListsToPaneForInOrderTiles(ArrayList<MahjongTile[]> inOrderTiles, GridPane pane) {
        for (int row = 0; row < inOrderTiles.size(); row++) {
            MahjongTile[] tiles = inOrderTiles.get(row);
            int col = 0;
            for (MahjongTile tile : tiles){
                ImageView tileDisplay = getTileDisplayForUsedTiles(tile);
                pane.add(tileDisplay, col, row);
                col ++;
            }
        }
    }


    public void updateUsedTiles(int playerIndex) {
        switch (playerIndex) {
            case 1: // 北玩家
                //computer1.handTiles.add(tile);
                loadTilesFromListsToPaneForUsedTiles(computer1.usedTiles, usedTilesInNorth);
                break;
            case 0: // 东玩家
                //computer2.handTiles.add(tile);
                loadTilesFromListsToPaneForUsedTiles(computer2.usedTiles, usedTilesInEast);
                break;
            case 2: // 西玩家
                //computer3.handTiles.add(tile);
                loadTilesFromListsToPaneForUsedTiles(computer3.usedTiles, usedTilesInWest);
                break;
            case 3:
                loadTilesFromListsToPaneForUsedTiles(humanPlayer.usedTiles, usedTiles);
        }
    }

    public void updateInOrderTiles(int playerIndex) {
        switch (playerIndex) {
            case 1:
                loadTilesFromListsToPaneForInOrderTiles(computer1.inOrderTiles, pairingTilesInNorth);
                break;
            case 0:
                loadTilesFromListsToPaneForInOrderTiles(computer2.inOrderTiles, pairingTilesInEast);
                break;
            case 2:
                loadTilesFromListsToPaneForInOrderTiles(computer3.inOrderTiles, pairingTilesInWest);
                break;
            case 3:
                loadTilesFromListsToPaneForInOrderTiles(humanPlayer.inOrderTiles, pairingTilesInSouth);
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
            iv.setFitWidth(45);       // 宽度
            iv.setFitHeight(100);      // 高度
            iv.setImage(image);         // 关联图像
            return iv;
        }else {
            Image image = new Image(getClass().getResourceAsStream("/images/" + tile.getSuit() + ".jpg"));
            //System.out.println(tile.getSuit());
            ImageView iv = new ImageView();
            iv.setPreserveRatio(true); // 保持比例
            iv.setFitWidth(45);       // 宽度
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

        mahjongDeck.sortHandTiles(humanPlayer.handTiles);
        mahjongDeck.sortHandTiles(computer1.handTiles);
        mahjongDeck.sortHandTiles(computer2.handTiles);
        mahjongDeck.sortHandTiles(computer3.handTiles);
        loadTilesFromListsToPaneForHuman(humanPlayer.handTiles);
        loadTilesFromListsToPaneForComputer(computer1.handTiles,northHandPile);
        loadTilesFromListsToPaneForComputer(computer2.handTiles,eastHandPile);
        loadTilesFromListsToPaneForComputer(computer3.handTiles,westHandPile);
        loadTilesFromListsToPaneForUsedTiles(humanPlayer.usedTiles, usedTiles);
        loadTilesFromListsToPaneForUsedTiles(computer1.usedTiles, usedTilesInNorth);
        loadTilesFromListsToPaneForUsedTiles(computer2.usedTiles, usedTilesInEast);
        loadTilesFromListsToPaneForUsedTiles(computer3.usedTiles, usedTilesInWest);
        animation();
    }

    public void animation(){
        // 加载动画特效
        Image image = new Image(getClass().getResourceAsStream("/images/背景.JPG"));

        // 创建ImageView以显示图像
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);  // 设置图像宽度
        imageView.setFitHeight(100); // 设置图像高度

        // 创建平移动画
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), imageView);
        translateTransition.setToX(-150); // 将图像水平向右平移200个像素

        // 开始动画
        translateTransition.play();

        // 创建 FadeTransition 来处理图像消失动画
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.6), imageView);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);

        fadeOut.playFromStart(); //立即开始动画

        // 让程序在3秒后结束运行（模拟图像的消失）
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(10000);
                //System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        animationPane.getChildren().add(imageView);


    }
}
