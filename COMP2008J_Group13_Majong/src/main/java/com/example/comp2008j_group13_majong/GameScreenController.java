package com.example.comp2008j_group13_majong;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import com.example.comp2008j_group13_majong.MasterControll.GameRules;
import javax.swing.*;
import javafx.scene.text.Text;
import java.awt.event.MouseListener;


public class GameScreenController extends JLabel {
    private String name;

    private int value;

    private boolean up;

    @FXML
    private Text dealerLabel;

    private GameRules gameRules;

    private boolean canClick=false;

    private boolean isClicked=false;

    public GameScreenController() {
    }
    @FXML
    public void initialize() {
        // 初始化游戏规则和玩家
        gameRules = new GameRules();

        // 更新UI显示庄家信息
        updateDealerLabel();
    }


    private void updateDealerLabel() {
        String dealerPosition = gameRules.getDealerPosition();
        dealerLabel.setText(dealerPosition);
    }

    public GameScreenController(String name, int value, boolean up) {
        this.name = name;
        this.value = value;
        this.up = up;

        if(this.up){
            //正面
            turnFront();
        }else {
            //反面
            turnRear();
        }


        this.setSize(12,16);
        this.setVisible(true);
        this.addMouseListener((MouseListener) this);
    }

    public void turnFront(){
        this.setIcon(new ImageIcon("src/main/resources/images/"+name+".jpg"));
        this.up=true;
    }

    public void turnRear(){
        this.setIcon(new ImageIcon("src/main/resources/images/背面.png"));
        this.up=false;
    }
    public void mouseClicked(MouseEvent e) {
        if(canClick){
            if(isClicked){
                //取消选中
                isClicked=false;
            }else {
                //选中
                isClicked=true;
            }
        }
    }


}
