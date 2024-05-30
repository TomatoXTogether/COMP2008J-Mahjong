package com.example.comp2008j_group13_majong.User;

import com.example.comp2008j_group13_majong.Tile.MahjongDeck;
import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import java.util.ArrayList;

public abstract class User {
    public String position;
    public String name;
    public int score;
    public ArrayList<MahjongTile[]> inOrderTiles; // tiles in order
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

    public MahjongTile[][] ifChi(MahjongTile tile){
        MahjongTile t1 = new MahjongTile(tile.getSuit(), tile.getValue(), tile.getIndex() - 2);
        MahjongTile t2 = new MahjongTile(tile.getSuit(), tile.getValue(), tile.getIndex() - 1);
        MahjongTile t3 = new MahjongTile(tile.getSuit(), tile.getValue(), tile.getIndex() + 1);
        MahjongTile t4 = new MahjongTile(tile.getSuit(), tile.getValue(), tile.getIndex() + 2);
        MahjongTile[][] shuzi = new MahjongTile[3][3];
        if ((getTile(t1) >= 1 && getTile(t2) >= 1)){
            isChi = true;
            shuzi[0][0] = t1;
            shuzi[0][1] = t2;
            shuzi[0][2] = tile;
        }else if ((getTile(t2) >= 1 && getTile(t3) >= 1)){
            isChi = true;
            shuzi[1][0] = t2;
            shuzi[1][1] = tile;
            shuzi[1][2] = t3;
        } else if ((getTile(t3) >= 1 && getTile(t4) >= 1)) {
            isChi = true;
            shuzi[2][0] = tile;
            shuzi[2][2] = t3;
            shuzi[2][1] = t4;
        }else {
            isChi = false;
        }
        return shuzi;
    }

    public void chi(MahjongTile tile) {
        if (isChi){

        }
    }

    public MahjongTile[] ifPong(MahjongTile tile){
        MahjongTile[] pongzi = new MahjongTile[3];
        if (getTile(tile) == 2){
            int i = 0;
            for (MahjongTile t : handTiles){
                if (t.getValue().equals(tile.getValue()) && t.getSuit().equals(tile.getSuit())){
                    pongzi[i] = t;
                    i ++;
                }
            }
            isPong = true;
        }else {
            isPong = false;
        }
        return pongzi;
    }

    public void peng(MahjongTile[] pongzi) {
        for (MahjongTile t : pongzi){
            handTiles.remove(t);
        }
        inOrderTiles.add(pongzi);
    }

    public MahjongTile[] ifKong(MahjongTile tile){
        MahjongTile[] kongzi = new MahjongTile[4];
        if (getTile(tile) == 3){
            int i = 0;
            for (MahjongTile t : handTiles){
                if (t.getValue().equals(tile.getValue()) && t.getSuit().equals(tile.getSuit())){
                    kongzi[i] = t;
                    i ++;
                }
            }
            isKong = true;
        }else {
            isKong = false;
        }
        return null;
    }

    public void kong(MahjongTile[] kongzi) {
        for (MahjongTile t : kongzi){
            handTiles.remove(t);
        }
        inOrderTiles.add(kongzi);
        isKong = false;
    }
    public boolean ifWin(){
        return getTiles().size() == 0;
    }

    public String getName(){
        return name;
    }

    public ArrayList<MahjongTile[]> getInOrderTiles(){
        return inOrderTiles;
    }

    public void setInOrderTiles(ArrayList<MahjongTile[]> inOrderTiles) {
        this.inOrderTiles = inOrderTiles;
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