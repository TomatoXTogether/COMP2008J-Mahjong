package com.example.comp2008j_group13_majong.Tile;

public class MahjongTile {
    public enum Suit {
        万, 条, 饼, 风, 发财, 白板
    }

    private Suit suit;
    private String value;

    public MahjongTile(Suit suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public MahjongTile(Suit suit) {
        this.suit = suit;
    }

    public Suit getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        if (value != null) {
            return value + suit;
        } else {
            return String.valueOf(suit);
        }
    }

}
