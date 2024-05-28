package com.example.comp2008j_group13_majong.User;

import com.example.comp2008j_group13_majong.Tile.MahjongDeck;
import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import java.util.ArrayList;

public abstract class User {
    protected String position;
    protected boolean isTurn;
    public ArrayList<MahjongTile> hand;
    public String name;
    public int score;
    public ArrayList<MahjongTile> tiles;
    public boolean isWin;
    public boolean isKong;
    public boolean isChi;
    public boolean isPong;

    public int getScore(){
        if (isKong){
            score += 30;
            isKong = false;
        }
        if (isChi){
            score += 10;
            isChi = false;
        }
        if (isPong){
            score += 10;
            isPong = false;
        }
        return score;
    }

    public boolean ifChi(MahjongTile tile){
        return false;
    }

    public boolean ifPong(MahjongTile tile){
        if (getTile(tile) == 2){
            return true;
        }
        return false;
    }

    public boolean ifKong(MahjongTile tile){
        if (getTile(tile) == 3){
            return true;
        }
        return false;
    }

    public boolean ifWin(){
        return getHand().size() == 0;
    }

    public abstract String getName();

    public ArrayList<MahjongTile> getHand(){
        return hand;
    }

    public void setHand(ArrayList<MahjongTile> hand) {
        this.hand = hand;
    }

    public String getPosition() {
        return position;
    }

    // 添加设置玩家位置的方法
    public void setPosition(String position) {
        this.position = position;
    }
    public boolean isTurn() {
        return isTurn;
    }

    // 设置是否是玩家的回合的方法
    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public int getTile(MahjongTile tile) {
        int num = 0;
        for (int i = 0; i < tiles.size(); i++){
            if (tiles.get(i).getValue().equals(tile.getValue())){
                if (tiles.get(i).getSuit().equals(tile.getSuit())){
                    num ++;
                }
            }
        }
        return num;
    }

    public ArrayList<MahjongTile> getTiles(){
        return tiles;
    }

    abstract ArrayList<MahjongTile> removeTiles(MahjongTile tile);

    abstract void addTile(MahjongTile tile);

    abstract MahjongTile selectTiles(MahjongTile tile);
}