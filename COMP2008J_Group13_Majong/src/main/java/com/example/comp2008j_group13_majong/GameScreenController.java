package com.example.comp2008j_group13_majong;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.awt.event.MouseListener;


public class GameScreenController extends JLabel {
    private String name;

    private int value;

    private boolean up;

    private boolean canClick=false;

    private boolean isClicked=false;

    public GameScreenController() {

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
