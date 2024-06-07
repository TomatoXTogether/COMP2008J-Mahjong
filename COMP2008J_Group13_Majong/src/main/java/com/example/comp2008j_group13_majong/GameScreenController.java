package com.example.comp2008j_group13_majong;

import com.example.comp2008j_group13_majong.MasterControll.GameEndChecker;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
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
    private Button pass;

    @FXML
    private ImageView passImage;

    @FXML
    private Button chi;

    @FXML
    private ImageView chiImage;

    @FXML
    private Button gang;

    @FXML
    private ImageView gangImage;

    @FXML
    private Button hu;

    @FXML
    private ImageView huImage;

    @FXML
    private Button peng;

    @FXML
    private ImageView pengImage;

    @FXML
    private Button play;

    @FXML
    private ImageView playImage;

    @FXML
    private Label remainTilesNumber;

    @FXML
    private Label dealer;

    @FXML
    private GridPane eastHandPile;

    @FXML
    private GridPane northHandPile;

    @FXML
    public GridPane playerHandPile;

    @FXML
    private GridPane westHandPile;

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

    private int index;
    private GameRules gameRules = GameRules.getInstance();
    private EndScreenController endScreenController;

    private Player humanPlayer;
    private Computer computer1;
    private Computer computer2;
    private Computer computer3;

    Media pick = new Media(new File("src/main/resources/music/bgm.mp3").toURI().toString());

    public MediaPlayer player=new MediaPlayer(pick);

    public GameScreenController() {
    }

    private int timeLine=15;

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
        switch (name) {
            case "chi" -> {
                chi.setVisible(isVisible);
                chiImage.setVisible(isVisible);
            }
            case "peng" -> {
                peng.setVisible(isVisible);
                pengImage.setVisible(isVisible);
            }
            case "gang" -> {
                gang.setVisible(isVisible);
                gangImage.setVisible(isVisible);
            }
            case "hu" -> {
                hu.setVisible(isVisible);
                huImage.setVisible(isVisible);
            }
            case "pass" -> {
                pass.setVisible(isVisible);
                passImage.setVisible(isVisible);
            }
            case "play" -> {
                play.setVisible(isVisible);
                playImage.setVisible(isVisible);
            }
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
                updateScore();

                timeStop();
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

            timeStop();

            autoPlayAction();
        }
    }


    public void huAction(GameScreenController gameScreenController, User currentPlayer, User lastPlayer) {
        MahjongTile huTile = lastPlayer.usedTiles.get(lastPlayer.usedTiles.size() - 1);
        ArrayList<MahjongTile> huTiles = currentPlayer.ifHu(huTile);
        if (huTiles != null) {
            currentPlayer.hu(huTile);

            updateInOrderTiles(currentPlayer.getIndex());
            lastPlayer.usedTiles.remove(huTile);
            updateUsedTiles(lastPlayer.getIndex());
            humanPlayer.notifyHu();
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

    private void timeStop(){
        timeline.stop();
        timeLine=15;
        countDown.setVisible(false);
    }


    @FXML
    public void gangBottonAction(ActionEvent event) {
        User lastUser = gameRules.current(gameRules.lastPlayerIndex);
        gameRules.currentPlayerIndex = humanPlayer.index;
        gameRules.gangAction(this,humanPlayer,lastUser);

        animation("gang",3);

        setVisible("gang",false);
        setVisible("pass",false);

        timeStop();

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

        timeStop();

        updateScore();
    }

    public void autoPlayAction() {
        gameRules.dealerNextRound(this);

        sortTiles();
        updateScore();
    }

    private void updateAllPlayerHands() {
        playerHandPile.getChildren().clear();
        northHandPile.getChildren().clear();
        eastHandPile.getChildren().clear();
        westHandPile.getChildren().clear();

        loadTilesFromListsToPaneForHuman(humanPlayer.handTiles);
        loadTilesFromListsToPaneForComputer(computer1.handTiles, northHandPile);
        loadTilesFromListsToPaneForComputer(computer2.handTiles, eastHandPile);
        loadTilesFromListsToPaneForComputer(computer3.handTiles, westHandPile);
        loadTilesFromListsToPaneForUsedTiles(humanPlayer.usedTiles, usedTiles);
    }

    public void updateOnePlayerHand(GridPane pane, ArrayList<MahjongTile> pile) {
        pane.getChildren().clear();

        loadTilesFromListsToPaneForHuman(pile);
        loadTilesFromListsToPaneForUsedTiles(humanPlayer.usedTiles, usedTiles);
    }


    public void playersTurn(){
        int currentPlayerIndex=gameRules.getCurrentPlayerIndex();

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
        score.setText("Score: "+humanPlayer.score);
    }


    @FXML
    private void showDealer(){
        dealer.setText("Dealer: "+gameRules.getDealerName());
    }

    @FXML
    void passButtonAction(ActionEvent event) {
        if(index!=-1){
            gameRules.currentPlayerIndex = (gameRules.lastPlayerIndex+1)%4;

            sortTiles();

            setVisible("pass",false);
            setVisible("chi",false);
            setVisible("peng",false);
            setVisible("gang",false);

            timeStop();
            humanPlayer.notifyPass();
            autoPlayAction();
        }
    }

    public void updateUsedTiles( int playerIndex) {
        switch (playerIndex) {
            case 1: // north
                loadTilesFromListsToPaneForUsedTiles(computer1.usedTiles, usedTilesInNorth);
                break;
            case 0: // east
                loadTilesFromListsToPaneForUsedTiles(computer2.usedTiles, usedTilesInEast);
                break;
            case 2: // west
                loadTilesFromListsToPaneForUsedTiles(computer3.usedTiles, usedTilesInWest);
                break;
            case 3://south
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


    private void sortTiles() {
        mahjongDeck.sortHandTiles(humanPlayer.handTiles);
        mahjongDeck.sortHandTiles(computer1.handTiles);
        mahjongDeck.sortHandTiles(computer2.handTiles);
        mahjongDeck.sortHandTiles(computer3.handTiles);

        playersTurn();
        updateAllPlayerHands();
        updateRemainTiles();
        updateOnePlayerHand(playerHandPile,humanPlayer.handTiles);
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
                    playerHandPile.getChildren().remove(tileDisplay);
                    playerHandPile.add(tileDisplay, finalRow, 0);
                    currentRaisedTile = tileDisplay;
                    index=finalRow;
                    setVisible("play",true);
                }
                else if (currentRaisedTile == tileDisplay) {
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

    private void loadTilesFromListsToPaneForUsedTiles(List<MahjongTile> usedTiles, GridPane pane){
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

    private void loadTilesFromListsToPaneForInOrderTiles(ArrayList<MahjongTile[]> inOrderTiles, GridPane pane) {
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



    private ImageView getTileDisplayForUsedTiles(MahjongTile tile) {
        ImageView iv = new ImageView();
        iv.setPreserveRatio(true); // ratio
        iv.setFitWidth(35);       // width
        iv.setFitHeight(80);      // height

        return getImageView(tile, iv);

    }

    private ImageView getImageView(MahjongTile tile, ImageView iv) {
        if (tile.getValue() != null) {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/" + tile.getValue() + tile.getSuit() + ".jpg")));
            iv.setImage(image);
        }else {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/" + tile.getSuit() + ".jpg")));
            iv.setImage(image);
        }
        return iv;
    }

    private ImageView getTileDisplayForHuman(MahjongTile tile) {
        ImageView iv = new ImageView();
        iv.setPreserveRatio(true); // ratio
        iv.setFitHeight(100);      // height
        iv.setFitWidth(45);       // width

        return getImageView(tile, iv);
    }

    private ImageView getTileDisplayForComputer(MahjongTile tile) {
            Image image = new Image(getClass().getResourceAsStream("/images/背面.jpg"));
            ImageView iv = new ImageView();
            iv.setPreserveRatio(true);
            iv.setFitWidth(50);
            iv.setFitHeight(100);
            iv.setImage(image);
            return iv;
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

        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        if(Objects.equals(operation, "hu")){
            imageView.setFitWidth(500);
            imageView.setFitHeight(500);
        }else {
            imageView.setFitWidth(300);
            imageView.setFitHeight(300);
        }

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), imageView);

        if(playerIndex==0){
            //east
            imageView.setLayoutX(700);
            imageView.setLayoutY(300);
            translateTransition.setToX(-100);

        }else if(playerIndex==1){
            //north
            imageView.setLayoutX(470);
            imageView.setLayoutY(150);
            translateTransition.setToY(100);

        }else if(playerIndex==2){
            //west
            imageView.setLayoutX(300);
            imageView.setLayoutY(300);
            translateTransition.setToX(100);

        }else {
            //south
            imageView.setLayoutX(470);
            imageView.setLayoutY(450);
            translateTransition.setToY(-100);
        }

        translateTransition.play();

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2.5), imageView);
        fadeOut.setFromValue(2.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);

        fadeOut.playFromStart();

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

        showDealer();
        player.play();

        autoPlayAction();
    }

}
