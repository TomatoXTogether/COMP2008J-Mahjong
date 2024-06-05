package com.example.comp2008j_group13_majong;

import com.example.comp2008j_group13_majong.MasterControll.GameRules;
import com.example.comp2008j_group13_majong.MasterControll.ScoreCalculator;
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
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

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
    public Button pass;

    @FXML
    public ImageView passImage;

    @FXML
    public ImageView chiImage;

    @FXML
    public ImageView gangImage;

    @FXML
    public ImageView huImage;

    @FXML
    public ImageView pengImage;

    @FXML
    public ImageView playImage;

    @FXML
    private Label remainTilesNumber;

    @FXML
    public Button chi;

    @FXML
    private GridPane eastHandPile;

    @FXML
    public Button gang;

    @FXML
    private GridPane handPile;

    @FXML
    public Button hu;

    @FXML
    private GridPane northHandPile;

    @FXML
    public Button peng;

    @FXML
    public Button play;

    @FXML
    public GridPane playerHandPile;

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
    private ScoreCalculator scoreCalculator = new ScoreCalculator();
    private GameRules gameRules=new GameRules();

    private Player humanPlayer;
    private Computer computer1;
    private Computer computer2;
    private Computer computer3;


    public GameScreenController() {
    }
    @FXML
    void chiBottonAction(ActionEvent event) {
            if (index != -1) {
                User last = gameRules.last(humanPlayer.index);
                MahjongTile chiTile = last.usedTiles.get(last.usedTiles.size() - 1);
                if (humanPlayer.isChi) {
                    humanPlayer.chi(chiTile);
                    last.usedTiles.remove(last.usedTiles.size() - 1);
                }
                animation("chi",3);
                chi.setVisible(false);
                chiImage.setVisible(false);
                pass.setVisible(false);
                passImage.setVisible(false);
                updateOnePlayerHand(playerHandPile, humanPlayer.handTiles);
                updateUsedTiles(last.index);
                updateInOrderTiles(3);
                gameRules.dealerNextRound(this);
            }
    }


    @FXML
    void huBottonAction(ActionEvent event) {
        User last = gameRules.lastPlayer;
        //gameRules.currentPlayerIndex = humanPlayer.index;
        huAction(this, humanPlayer, last);
        hu.setVisible(false);
        huImage.setVisible(false);
        updateOnePlayerHand(playerHandPile, humanPlayer.handTiles);
        animation("hu",3);
    }

    @FXML
    void playBottonAction(ActionEvent event) {
        if (index != -1) {
            MahjongTile usedTile = humanPlayer.removeTile(index);
            playerHandPile.getChildren().remove(currentRaisedTile);
            for (int i = 0; i < gameRules.computers.size(); i++) {
                Computer computer = gameRules.computers.get(i);
                computer.ifHu(usedTile);
                computer.ifGang(usedTile);
                computer.ifPeng(usedTile);
            }
            computer2.ifChi(usedTile);
            play.setVisible(false);
            playImage.setVisible(false);
            updateOnePlayerHand(playerHandPile, humanPlayer.handTiles);
            currentRaisedTile = null;
        }
    }

    @FXML
    public boolean pengTestAction(ActionEvent event,MahjongTile usedTile){
        if (gang.isVisible()) {
            peng.setVisible(false);
            pengImage.setVisible(false);
            return false;
        }

        for (int i = 0; i < gameRules.computers.size(); i++) {
            Computer computer = gameRules.computers.get(i);
            MahjongTile[] pengzi = computer.ifPeng(usedTile);
            if (pengzi != null) {
                // 如果是电脑玩家自动碰
                gameRules.pengAction(this, computer, humanPlayer);
                return true;
            }
        }

        // 检查真人玩家是否可以碰
        MahjongTile[] pengzi = humanPlayer.ifPeng(usedTile);
        if (pengzi != null) {
            peng.setVisible(true);
            pengImage.setVisible(true);
            return true;
        }

        return false;
    }
    @FXML
    public boolean gangTestAction(ActionEvent event,MahjongTile usedTile){
        for (int i = 0; i < gameRules.computers.size(); i++) {
            Computer computer = gameRules.computers.get(i);
            MahjongTile[] gangzi = computer.ifGang(usedTile);
            if (gangzi != null) {
                // 如果是电脑玩家自动杠
                gameRules.gangAction(this, computer, humanPlayer);
                return true;
            }
        }

        // 检查真人玩家是否可以杠
        MahjongTile[] gangzi = humanPlayer.ifGang(usedTile);
        if (gangzi != null) {
            gang.setVisible(true);
            gangImage.setVisible(true);
            return true;
        }

        return false;
    }

    public void huAction(GameScreenController gameScreenController, User currentPlayer, User lastPlayer) {
        MahjongTile huTile = lastPlayer.usedTiles.get(lastPlayer.usedTiles.size() - 1);
        ArrayList<MahjongTile> huTiles = currentPlayer.ifHu(huTile);
        if (huTiles != null) {
            currentPlayer.hu(huTile);

            // 在界面上更新胡操作后的牌
            updateInOrderTiles(currentPlayer.getIndex());
            lastPlayer.usedTiles.remove(huTile);
            updateUsedTiles(lastPlayer.getIndex());

            // 在界面上显示赢家
            //GameEndChecker.checkWin(currentPlayer);

            // 结束游戏
            //GameEndChecker.endGame();
        }
    }


    public boolean huTestAction(ActionEvent event, MahjongTile usedTile, User lastPlayer) {
        if (lastPlayer instanceof Player) {
            for (int i = 0; i < gameRules.computers.size(); i++) {
                Computer computer = gameRules.computers.get(i);
                ArrayList<MahjongTile> tilesToCheck = computer.ifHu(usedTile);
                if (tilesToCheck != null) {
                    huAction(this, computer, lastPlayer);
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
                        huAction(this, player, lastPlayer);
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
    public void gangBottonAction(ActionEvent event) {
        User currentUser = gameRules.current(gameRules.currentPlayerIndex);
        gameRules.currentPlayerIndex = humanPlayer.index;
        gameRules.gangAction(this,humanPlayer,currentUser);
        gang.setVisible(false);
//        User currentUser = gameRules.current(gameRules.currentPlayerIndex);
//        User lastUser = gameRules.last(gameRules.currentPlayerIndex);
//        gameRules.gangAction(this, currentUser, lastUser);
//        gang.setVisible(false);
        gangImage.setVisible(false);
        pass.setVisible(false);
        passImage.setVisible(false);
        animation("gang",3);
    }

    @FXML
    void pengBottonAction(ActionEvent event) {
        User currentUser = gameRules.current(gameRules.currentPlayerIndex);
        gameRules.currentPlayerIndex = humanPlayer.index;
        gameRules.pengAction(this,humanPlayer,currentUser);
        peng.setVisible(false);
        pengImage.setVisible(false);
        pass.setVisible(false);
        passImage.setVisible(false);
        animation("peng",3);
    }

    @FXML
    public void drawButtonAction(ActionEvent event) {
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

    public void updateOnePlayerHand(GridPane pane, ArrayList<MahjongTile> pile) {
        pane.getChildren().clear();
        // 重新加载每个玩家的手牌
        loadTilesFromListsToPaneForHuman(pile);
        loadTilesFromListsToPaneForUsedTiles(humanPlayer.usedTiles, usedTiles);
    }


    public void playersTurn(){
        int currentPlayerIndex=gameRules.getCurrentPlayerIndex();
        System.out.println("GameController playerindex = "+ currentPlayerIndex);
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
        }else if(currentPlayerIndex==0){
            south.setTextFill(Color.RED);
            east.setTextFill(Color.BLACK);
            west.setTextFill(Color.BLACK);
            north.setTextFill(Color.BLACK);
        }
    }

    private void updateRemainTiles(){
        remainTilesNumber.setText("Remain: "+gameRules.getRemainingTilesNumber());
    }

    @FXML
    void passButtonAction(ActionEvent event) {
        if(index!=-1){
            //gameRules.dealerNextRound(this);
            gameRules.currentPlayerIndex = (gameRules.currentPlayerIndex + 1) % 4;
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
                    currentRaisedTile = tileDisplay;
                    index=finalRow;
                    play.setVisible(true);
                    playImage.setVisible(true);
                }
                else if (currentRaisedTile == tileDisplay) {
                    // 点击的牌是当前上升的牌，下降它
                    playerHandPile.getChildren().remove(tileDisplay);
                    playerHandPile.add(tileDisplay, finalRow, 1);
                    currentRaisedTile = null;
                    index=-1;
                    play.setVisible(false);
                    playImage.setVisible(false);
                }
            });
        }
    }

    @FXML
    private void mouseClicked(MouseEvent mouseEvent) {
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
        int col = 0;
        for (int row = 0; row < usedTiles.size(); row++) {

            MahjongTile tile = usedTiles.get(row);
            ImageView tileDisplay = getTileDisplayForUsedTiles(tile);

            pane.add(tileDisplay, row  ,0 ); // 将行数除以7决定在第几行

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


    public void updateUsedTiles( int playerIndex) {
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

    public void updateInOrderTiles( int playerIndex) {
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
        animation("chi",1);
        animation("peng",0);
        animation("hu",2);
    }

    public void animation(String operation, int playerIndex){
        Image image;
        if(operation=="chi"){
            image = new Image(getClass().getResourceAsStream("/images/吃特效.png"));
        }else if(operation=="peng"){
            image = new Image(getClass().getResourceAsStream("/images/碰特效.png"));
        }else if(operation=="gang"){
            image = new Image(getClass().getResourceAsStream("/images/杠特效.png"));
        }else {
            image = new Image(getClass().getResourceAsStream("/images/胡特效.png"));
        }

        // 创建ImageView以显示图像
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        if(operation=="hu"){
            imageView.setFitWidth(500);  // 设置图像宽度
            imageView.setFitHeight(500); // 设置图像高度
        }else {
            imageView.setFitWidth(300);  // 设置图像宽度
            imageView.setFitHeight(300); // 设置图像高度
        }


        // 创建平移动画
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), imageView);

        if(playerIndex==0){
            //east
            imageView.setLayoutX(700);
            imageView.setLayoutY(300);
            translateTransition.setToX(-100); // 将图像水平向右平移200个像素

        }else if(playerIndex==1){
            //north
            imageView.setLayoutX(470);
            imageView.setLayoutY(150);
            translateTransition.setToY(100); // 将图像水平向右平移200个像素

        }else if(playerIndex==2){
            //west
            imageView.setLayoutX(300);
            imageView.setLayoutY(300);
            translateTransition.setToX(100); // 将图像水平向右平移200个像素

        }else {
            //south
            imageView.setLayoutX(470);
            imageView.setLayoutY(450);
            translateTransition.setToY(-100); // 将图像水平向右平移200个像素
        }

        // 开始动画
        translateTransition.play();

        // 创建 FadeTransition 来处理图像消失动画
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), imageView);
        fadeOut.setFromValue(2.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);

        fadeOut.playFromStart(); //立即开始动画

        // 让程序在3秒后结束运行（模拟图像的消失）
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        //animationPane.getChildren().add(imageView);


    }


}
