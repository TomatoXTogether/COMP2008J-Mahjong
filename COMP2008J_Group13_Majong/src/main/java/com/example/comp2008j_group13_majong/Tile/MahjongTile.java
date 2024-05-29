package com.example.comp2008j_group13_majong.Tile;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MahjongTile implements MouseListener {
    public enum Suit {
        万, 条, 饼, 风, 发财, 白板
    }

    // 牌的花色
    private Suit suit;
    // 牌的值
    private String value;
    private int index;
    // 牌显示正面还是背面
    private boolean up;
    // 是否可点击
    private boolean canClick = false;
    // 当前状态，是否已经被点击
    private boolean clicked = false;

    public MahjongTile(Suit suit, String value, int index) {
        this.suit = suit;
        this.value = value;
        this.up = false;
        this.index = index;
//        setTileAppearance();
//        // 给每一张牌添加鼠标监听
//        this.addMouseListener(this);
//        // 设置牌的宽高大小
//        this.setSize(71, 96);
//        // 显示牌
//        this.setVisible(true);
    }

    public MahjongTile(Suit suit) {
        this(suit, null, 0);
    }

    // 根据牌的状态（正面或背面）设置外观
//    private void setTileAppearance() {
//        if (this.up) {
//            this.turnFront();
//        } else {
//            this.turnRear();
//        }
//    }

    // 显示正面
//    public void turnFront() {
//        if (value != null) {
//            this.setIcon(new ImageIcon("path_to_images/" + suit + "_" + value + ".png"));
//        } else {
//            this.setIcon(new ImageIcon("path_to_images/" + suit + ".png"));
//        }
//        this.up = true;
//    }

    // 显示背面
//    public void turnRear() {
//        this.setIcon(new ImageIcon("path_to_images/rear.png")); // 修改图片路径
//        this.up = false;
//    }

    public Suit getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public boolean isUp() {
        return up;
    }

    public int getIndex() {
        return index;
    }

    public void setValue(String value) {
        this.value = value;
    }

//    public void setUp(boolean up) {
//        this.up = up;
//        setTileAppearance();
//    }

    public boolean isCanClick() {
        return canClick;
    }

    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    @Override
    public String toString() {
        if (value != null) {
            return value + suit;
        } else {
            return String.valueOf(suit);
        }
    }

    // 出牌时，需要点击牌
    // 被点击之后，牌向上移动20个像素
    // 再次被点击，牌回落20个像素
    @Override
    public void mouseClicked(MouseEvent e) {
        if (canClick) {
            //Point from = this.getLocation();
            int step;
            if (clicked) {
                step = 20;
            } else {
                step = -20;
            }
            clicked = !clicked;
            //Point to = new Point(from.x, from.y + step);
            //this.setLocation(to);
        }
        //return false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
