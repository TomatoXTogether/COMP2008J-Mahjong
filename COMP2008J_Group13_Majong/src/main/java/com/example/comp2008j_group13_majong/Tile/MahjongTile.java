package com.example.comp2008j_group13_majong.Tile;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MahjongTile {
    public enum Suit {
        万, 条, 饼, 风, 发财, 白板
    }

    private Suit suit;
    private String value;
    private int index;

    public boolean ifNum;

    public MahjongTile(Suit suit, String value, int index) {
        this.suit = suit;
        this.value = value;
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

}
