package com.example.comp2008j_group13_majong.User;

import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import java.util.ArrayList;

public class Computer extends User {
    public String name;
    public int score;
    public ArrayList<MahjongTile> tiles;
    public boolean isTurn;
    public boolean isWin;
    public boolean isKong;
    public boolean isChi;
    public boolean isPong;

    @Override
    int getScore() {
        return 0;
    }

    @Override
    ArrayList<MahjongTile> removeTiles(MahjongTile tile) {
        return null;
    }

    @Override
    void addTile(MahjongTile tile) {

    }

    @Override
    MahjongTile selectTiles(MahjongTile tile) {
        return null;
    }

    @Override
    boolean ifChi() {
        return false;
    }

    @Override
    boolean ifPong() {
        return false;
    }

    @Override
    boolean ifKong() {
        return false;
    }

    @Override
    boolean ifWin() {
        return false;
    }
}
