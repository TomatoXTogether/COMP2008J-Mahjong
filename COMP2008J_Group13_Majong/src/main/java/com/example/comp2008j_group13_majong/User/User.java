package com.example.comp2008j_group13_majong.User;

import com.example.comp2008j_group13_majong.Tile.MahjongDeck;
import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import java.util.ArrayList;

public abstract class User {
    public String position;
    public String name;
    public int score;
    public ArrayList<ArrayList<MahjongTile>> inOrderTiles; // tiles in order
    public ArrayList<MahjongTile> handTiles; // tiles in hand
    public boolean isTurn;
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
            ArrayList<MahjongTile> pongzi = new ArrayList<>();
            for (MahjongTile t : handTiles){
                if (t.getValue().equals(tile.getValue()) && t.getSuit().equals(tile.getSuit())){
                    handTiles.remove(t);
                    pongzi.add(t);
                    inOrderTiles.add(pongzi);
                }
            }
            return isPong = true;
        }
        return isPong = false;
    }

    public boolean ifKong(MahjongTile tile){
        if (getTile(tile) == 3){
            ArrayList<MahjongTile> kongzi = new ArrayList<>();
            for (MahjongTile t : handTiles){
                if (t.getValue().equals(tile.getValue()) && t.getSuit().equals(tile.getSuit())){
                    handTiles.remove(t);
                    kongzi.add(t);
                    inOrderTiles.add(kongzi);
                }
            }
            return isKong = true;
        }
        return isKong = false;
    }

    public boolean ifWin(){
        return getTiles().size() == 0;
    }

    public String getName(){
        return name;
    }

    public ArrayList<ArrayList<MahjongTile>> getInOrderTiles(){
        return inOrderTiles;
    }

    public void setInOrderTiles(ArrayList<ArrayList<MahjongTile>> hand) {
        this.inOrderTiles = hand;
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
        for (int i = 0; i < handTiles.size(); i++){
            if (handTiles.get(i).getValue().equals(tile.getValue())){
                if (handTiles.get(i).getSuit().equals(tile.getSuit())){
                    num ++;
                }
            }
        }
        return num;
    }

    public ArrayList<MahjongTile> getTiles(){
        return handTiles;
    }

    public void addTile(MahjongTile tile){
        handTiles.add(tile);
    }

    abstract MahjongTile selectTiles(MahjongTile tile);

    abstract ArrayList<MahjongTile> removeTiles(MahjongTile tile);
}