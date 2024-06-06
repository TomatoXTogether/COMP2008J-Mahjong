package com.example.comp2008j_group13_majong;

import com.example.comp2008j_group13_majong.MasterControll.GameEndChecker;
import com.example.comp2008j_group13_majong.MasterControll.GameRules;
import com.example.comp2008j_group13_majong.MasterControll.PlayerAction;
import com.example.comp2008j_group13_majong.MasterControll.ScoreCalculator;
import com.example.comp2008j_group13_majong.Tile.MahjongDeck;
import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import com.example.comp2008j_group13_majong.User.Computer;
import com.example.comp2008j_group13_majong.User.Player;
import com.example.comp2008j_group13_majong.User.User;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
//import javafx.scene.media.Media;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameScreenController implements Initializable {

    @FXML
    private AnchorPane animationPane;

    @FXML
    public Label countDown;

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
    private Label dealer;

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
    public GameRules gameRules = GameRules.getInstance();
    private EndScreenController endScreenController;

    private Player humanPlayer;
    private Computer computer1;
    private Computer computer2;
    private Computer computer3;

    Media pick = new Media(new File("src/main/resources/music/bgm.mp3").toURI().toString());

    public MediaPlayer player=new MediaPlayer(pick);

    public GameScreenController() {
    }

    int timeLine=15;

    private AtomicBoolean playing = new AtomicBoolean(false);

    public Timeline timeline;
    public void startAnimationForPlay() {
        countDown.setVisible(true);
            if (!playing.get()) {
                timeline = new Timeline(
                        new KeyFrame(Duration.seconds(1), event -> {
                            if (timeLine >= 0) {
                                countDown.setText("Count Down: " + timeLine--);
                            } else {
                                index = new Random().nextInt(playerHandPile.getColumnCount());
                                playBottonAction(new ActionEvent());
                                timeLine = 20;
                            }
                        })
                );
                timeline.setCycleCount(Animation.INDEFINITE);

                timeline.play();

                playing.set(true);
            }

    }
    public void startAnimationForOperation() {
        countDown.setVisible(true);
        if (!playing.get()) {
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> {
                        if (timeLine >= 0) {
                            countDown.setText("Count Down: " + timeLine--);
                        } else {
                            passButtonAction(new ActionEvent());
                            timeLine = 20;
                        }
                    })
            );
            timeline.setCycleCount(Animation.INDEFINITE);

            timeline.play();

            playing.set(true);
        }

    }

    public void setVisible(String name,boolean isVisible){
        if(name.equals("chi")){
            chi.setVisible(isVisible);
            chiImage.setVisible(isVisible);
        }else if(name.equals("peng")){
            peng.setVisible(isVisible);
            pengImage.setVisible(isVisible);
        }else if(name.equals("gang")){
            gang.setVisible(isVisible);
            gangImage.setVisible(isVisible);
        }else if(name.equals("hu")){
            hu.setVisible(isVisible);
            huImage.setVisible(isVisible);
        }else if(name.equals("pass")){
            pass.setVisible(isVisible);
            passImage.setVisible(isVisible);
        }else if(name.equals("play")){
            play.setVisible(isVisible);
            playImage.setVisible(isVisible);
        }
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
                setVisible("chi",false);
                setVisible("pass",false);

                updateOnePlayerHand(playerHandPile, humanPlayer.handTiles);
                updateUsedTiles(last.index);
                updateInOrderTiles(3);
                gameRules.dealerNextRound(this);
                updateScore();

                timeline.stop();
                timeLine=15;
                countDown.setVisible(false);
            }
    }


    @FXML
    void huBottonAction(ActionEvent event) throws IOException {
        User last = gameRules.lastPlayer;
        gameRules.currentPlayerIndex = humanPlayer.index;
        huAction(this, humanPlayer, last);
        hu.setVisible(false);
        huImage.setVisible(false);
        pass.setVisible(false);
        passImage.setVisible(false);
        updateOnePlayerHand(playerHandPile, humanPlayer.handTiles);
        animation("hu",3);
        updateScore();
        Stage currentStage = (Stage) hu.getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EndScreen.fxml"));
        Parent root;
        root = loader.load();
        EndScreenController controller = loader.getController();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
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
            setVisible("play",false);
            updateOnePlayerHand(playerHandPile, humanPlayer.handTiles);
            currentRaisedTile = null;
            timeline.stop();
            timeLine=15;
            countDown.setVisible(false);
        }
    }

    @FXML
    public boolean pengTestAction(ActionEvent event,MahjongTile usedTile){
        if (gang.isVisible()) {
            setVisible("peng",false);
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
            setVisible("peng",true);
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
            setVisible("gang",true);
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
            updateScore();

            // 在界面上显示赢家
            GameEndChecker.checkWin(currentPlayer);

            // 结束游戏
            GameEndChecker.endGame();
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
        User lastUser = gameRules.current(gameRules.lastPlayerIndex);
        gameRules.currentPlayerIndex = humanPlayer.index;
        gameRules.gangAction(this,humanPlayer,lastUser);

        animation("gang",3);
        setVisible("gang",false);
        setVisible("pass",false);

        timeline.stop();
        timeLine=15;
        countDown.setVisible(false);

        updateScore();
    }

    @FXML
    void pengBottonAction(ActionEvent event) {
        User lastUser = gameRules.current(gameRules.lastPlayerIndex);
        gameRules.currentPlayerIndex = humanPlayer.index;
        gameRules.pengAction(this,humanPlayer,lastUser);

        animation("peng",3);
        setVisible("peng",false);
        setVisible("pass",false);

        timeline.stop();
        timeLine=15;
        countDown.setVisible(false);

        updateScore();
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
        updateScore();
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
        if(currentPlayerIndex==0){
            east.setTextFill(Color.RED);
            north.setTextFill(Color.BLACK);
            west.setTextFill(Color.BLACK);
            south.setTextFill(Color.BLACK);
        }else if(currentPlayerIndex==1){
            north.setTextFill(Color.RED);
            east.setTextFill(Color.BLACK);
            west.setTextFill(Color.BLACK);
            south.setTextFill(Color.BLACK);
        }else if(currentPlayerIndex==2){
            west.setTextFill(Color.RED);
            east.setTextFill(Color.BLACK);
            north.setTextFill(Color.BLACK);
            south.setTextFill(Color.BLACK);
        }else if(currentPlayerIndex==3){
            south.setTextFill(Color.RED);
            east.setTextFill(Color.BLACK);
            west.setTextFill(Color.BLACK);
            north.setTextFill(Color.BLACK);
        }
    }

    private void updateRemainTiles(){
        remainTilesNumber.setText("Remain: "+gameRules.getRemainingTilesNumber());
    }

    public void updateScore(){
        score.setText("Remain: "+humanPlayer.score);
    }


    @FXML
    private void showDealer(){
        dealer.setText("Dealer: "+gameRules.getDealerName());
    }

    @FXML
    void passButtonAction(ActionEvent event) {
        if(index!=-1){
            //gameRules.dealerNextRound(this);
            gameRules.currentPlayerIndex = (gameRules.lastPlayerIndex+1)%4;
            // 摸牌后重新排序玩家的手牌
            mahjongDeck.sortHandTiles(humanPlayer.handTiles);
            mahjongDeck.sortHandTiles(computer1.handTiles);
            mahjongDeck.sortHandTiles(computer2.handTiles);
            mahjongDeck.sortHandTiles(computer3.handTiles);
            playersTurn();
            updateAllPlayerHands();
            updateRemainTiles();
            updateOnePlayerHand(playerHandPile,humanPlayer.handTiles);

            setVisible("pass",false);
            setVisible("chi",false);
            setVisible("peng",false);
            setVisible("gang",false);

            humanPlayer.notifyPass();
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
                    setVisible("play",true);
                }
                else if (currentRaisedTile == tileDisplay) {
                    // 点击的牌是当前上升的牌，下降它
                    playerHandPile.getChildren().remove(tileDisplay);
                    playerHandPile.add(tileDisplay, finalRow, 1);
                    currentRaisedTile = null;
                    index=-1;
                    setVisible("play",false);
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
        int maxCols = 6;
        int rowIndex = 0;
        int colIndex = 0;

        for (MahjongTile tile : usedTiles) {
            ImageView tileDisplay = getTileDisplayForUsedTiles(tile);
            pane.add(tileDisplay, colIndex, rowIndex);

            colIndex++;
            if (colIndex >= maxCols) {
                colIndex = 0;
                rowIndex++;
            }

            if(rowIndex > 6) {
                break;
            }
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
        //animation("chi",1);
        //animation("peng",0);
        animation("gang",1);
        //animation("hu",2);
        showDealer();
        player.play();
    }

    public void animation(String operation, int playerIndex){
        Image image;
        MediaPlayer player;

        if(Objects.equals(operation, "chi")){
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/吃特效.png")));
            Media pick = new Media(new File("src/main/resources/music/吃音效.m4a").toURI().toString());
            player=new MediaPlayer(pick);
        }else if(Objects.equals(operation, "peng")){
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/碰特效.png")));
            Media pick = new Media(new File("src/main/resources/music/碰音效.m4a").toURI().toString());
            player=new MediaPlayer(pick);
        }else if(Objects.equals(operation, "gang")){
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/杠特效.png")));
            Media pick = new Media(new File("src/main/resources/music/杠音效.m4a").toURI().toString());
             player=new MediaPlayer(pick);
        }else {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/胡特效.png")));
            Media pick = new Media(new File("src/main/resources/music/胡音效.m4a").toURI().toString());
             player=new MediaPlayer(pick);
        }

        player.play();
        // 创建ImageView以显示图像
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        if(Objects.equals(operation, "hu")){
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

        animationPane.getChildren().add(imageView);


    }

    public void showActionButtons(boolean canPeng, boolean canGang) {
        peng.setVisible(canPeng);
        gang.setVisible(canGang);
        pass.setVisible(true);
    }

    public void hideActionButtons() {
        peng.setVisible(false);
        gang.setVisible(false);
        pass.setVisible(false);
    }

    public void onPengButtonClicked() {
        // 调用玩家的pengAction方法
        // 这里需要引用当前玩家和当前牌
        // e.g., currentPlayer.pengAction(this, currentTile);
        hideActionButtons();
    }

    public void onGangButtonClicked() {
        // 调用玩家的gangAction方法
        // 这里需要引用当前玩家和当前牌
        // e.g., currentPlayer.gangAction(this, currentTile);
        hideActionButtons();
    }

    public void onPassButtonClicked() {
        // 不进行任何操作，继续游戏
        hideActionButtons();
        // 更新顺序为下一玩家
        gameRules.dealerNextRound(this);
    }


}
