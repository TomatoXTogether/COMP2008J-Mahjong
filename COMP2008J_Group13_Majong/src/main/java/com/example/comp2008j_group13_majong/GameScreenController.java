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
import javafx.scene.control.Label;
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


    private ImageView currentRaisedTile;

    int index;

    private GameRules gameRules;

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
    void playBottonAction(ActionEvent event) {
        if(index!=-1){
            humanPlayerHand.remove(index);
            System.out.println(humanPlayerHand);
            loadTilesFromListsToPaneForHuman(humanPlayerHand);
            playerHandPile.getChildren().remove(currentRaisedTile);
            play.setVisible(false);
            //gameRules.dealerNextRound();
            updateOnePlayerHand(playerHandPile,humanPlayerHand);
        }

    }

    @FXML
    void drawButtonAction(ActionEvent event) {
        gameRules.dealerNextRound();
        updateOnePlayerHand(playerHandPile,humanPlayerHand);
    }

    private void updateOnePlayerHand(GridPane pane,ArrayList<MahjongTile> pile) {
        // 清空所有玩家手牌的显示
        pane.getChildren().clear();
        //northHandPile.getChildren().clear();
        //eastHandPile.getChildren().clear();
        //westHandPile.getChildren().clear();

        // 重新加载每个玩家的手牌
        loadTilesFromListsToPaneForHuman(pile);
    }

    @FXML
    void mouseClicked(MouseEvent event) {

    }

    @FXML
    void pengBottonAction(ActionEvent event) {

    }

    private void loadTilesFromListsToPaneForHuman (List<MahjongTile> humanTiles) {
        for (int row = 0; row < humanTiles.size(); row++) {
            MahjongTile tile = humanTiles.get(row);
            ImageView tileDisplay = getTileDisplayForHuman(tile);
            tileDisplay.setOnMouseClicked(e -> {
                mouseClicked(e);
            });
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
        if(computerTiles==computer1Hand){
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

//    public MahjongTile createTileFromImage(ImageView imageView) {
//        // 从ImageView获取图片路径
//        System.out.println(imageView.getImage());
//        String imageUrl = imageView.getImage().getUrl();
//
//        // 提取文件名部分（假设图片路径格式为 "/images/{value}{suit}.jpg" 或 "/images/{suit}.jpg"）
//        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.lastIndexOf(".jpg"));
//
//        // 区分是有值的牌还是无值的牌
//        if (fileName.length() > 1) {
//            // 有值的牌
//            String value = fileName.substring(0, fileName.length() - 1);
//            String suit = fileName.substring(fileName.length() - 1);
//            return new MahjongTile(value, suit);
//        } else {
//            // 无值的牌
//            return new MahjongTile(null, fileName);
//        }
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameRules = new GameRules();
        //GameRules gameRules = new GameRules();
        MahjongDeck mahjongDeck = new MahjongDeck();
        humanPlayerHand=gameRules.humanPlayerHand;
        computer1Hand=gameRules.computer1Hand;
        computer2Hand=gameRules.computer2Hand;
        computer3Hand=gameRules.computer3Hand;

        mahjongDeck.sortTiles(humanPlayerHand);
        loadTilesFromListsToPaneForHuman(humanPlayerHand);
        loadTilesFromListsToPaneForComputer(computer1Hand,northHandPile);
        loadTilesFromListsToPaneForComputer(computer2Hand,eastHandPile);
        loadTilesFromListsToPaneForComputer(computer3Hand,westHandPile);
    }
}
