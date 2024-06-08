package com.example.comp2008j_group13_majong.Tile;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MahjongTile implements MouseListener {
    public enum Suit {
        万, 条, 饼, 风, 发财, 白板
    }

    private Suit suit;
    private String value;
    private int index;
    private boolean up;
    private boolean canClick = false;
    private boolean clicked = false;

    public boolean ifNum;

    public MahjongTile(Suit suit, String value, int index) {
        this.suit = suit;
        this.value = value;
        this.up = false;
        this.index = index;
    }

    public MahjongTile(Suit suit) {
        this(suit, null, 0);
    }

    public Suit getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        if (value != null) {
            return value + suit;
        } else {
            return String.valueOf(suit);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (canClick) {
            int step;
            if (clicked) {
                step = 20;
            } else {
                step = -20;
            }
            clicked = !clicked;
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
