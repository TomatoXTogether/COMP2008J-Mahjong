package com.example.comp2008j_group13_majong.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class MahjongTile extends JLabel implements MouseListener {
    public enum Suit {
        万, 条, 饼, 风, 发财, 白板
    }

    // 牌的花色
    private Suit suit;
    // 牌的真实值
    private String realValue;
    // 牌对应的整数值
    private int value;
    // 牌显示正面还是背面
    private boolean up;
    // 是否可点击
    private boolean canClick = false;
    // 当前状态，是否已经被点击
    private boolean clicked = false;

    // 定义一个静态映射，将牌的真实值映射到整数值
    private static final Map<Integer, String> valueMap = new HashMap<>();

    static {
        // 初始化数值映射
        String[] numberValues = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
        for (int i = 0; i < numberValues.length; i++) {
            valueMap.put(i + 1, numberValues[i]);
        }
        valueMap.put(10, "东");
        valueMap.put(11, "南");
        valueMap.put(12, "西");
        valueMap.put(13, "北");
        valueMap.put(14, "中");
        valueMap.put(15, "发");
        valueMap.put(16, "白板");
    }

    public MahjongTile(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
        this.realValue = valueMap.get(value);
        this.up = false;
        setTileAppearance();
        // 给每一张牌添加鼠标监听
        this.addMouseListener(this);
        // 设置牌的宽高大小
        this.setSize(71, 96);
        // 显示牌
        this.setVisible(true);
    }

    // 根据牌的状态（正面或背面）设置外观
    private void setTileAppearance() {
        if (this.up) {
            this.turnFront();
        } else {
            this.turnRear();
        }
    }

    // 显示正面
    public void turnFront() {
        this.setIcon(new ImageIcon("path_to_images/" + suit + "_" + realValue + ".png"));
        this.up = true;
    }

    // 显示背面
    public void turnRear() {
        this.setIcon(new ImageIcon("path_to_images/rear.png")); // 修改图片路径
        this.up = false;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String getRealValue() {
        return realValue;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
        setTileAppearance();
    }

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
        if (value != 15 && value != 16) {
            return realValue + suit;
        } else {
            return realValue;
        }
    }

    // 出牌时，需要点击牌
    // 被点击之后，牌向上移动20个像素
    // 再次被点击，牌回落20个像素
    @Override
    public void mouseClicked(MouseEvent e) {
        if (canClick) {
            Point from = this.getLocation();
            int step;
            if (clicked) {
                step = 20;
            } else {
                step = -20;
            }
            clicked = !clicked;
            Point to = new Point(from.x, from.y + step);
            this.setLocation(to);
        }
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
