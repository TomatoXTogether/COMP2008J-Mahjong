package com.example.comp2008j_group13_majong.User;

import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import java.util.ArrayList;

public abstract class User {
    protected String position;
    protected boolean isTurn;

    abstract int getScore();

    abstract ArrayList<MahjongTile> removeTiles(MahjongTile tile);

    abstract void addTile(MahjongTile tile);

    abstract MahjongTile selectTiles(MahjongTile tile);

    abstract boolean ifChi();

    abstract boolean ifPong();

    abstract boolean ifKong();

    abstract boolean ifWin();

    public abstract String getName();

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public boolean isTurn() {
        return isTurn;
    }
    public void setTurn(boolean turn) {
        isTurn = turn;
    }
}