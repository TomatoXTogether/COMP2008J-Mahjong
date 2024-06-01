package com.example.comp2008j_group13_majong.User;

import com.example.comp2008j_group13_majong.Tile.MahjongDeck;
import com.example.comp2008j_group13_majong.Tile.MahjongTile;
import com.example.comp2008j_group13_majong.Tile.MahjongTileComparator;

import java.util.ArrayList;
import java.util.Objects;

public abstract class User {
    public String position;
    public String name;
    public int index;
    public int score;
    public ArrayList<MahjongTile[]> inOrderTiles; // tiles in order
    public ArrayList<MahjongTile> handTiles; // tiles in hand
    public ArrayList<MahjongTile> usedTiles;
    public boolean isTurn;
    public boolean isKong;
    public boolean isChi;
    public boolean isPong;
    public boolean isHu;

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
        if (tile.ifNum){
            String[] numberValues = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
            MahjongTile t1 = null;
            MahjongTile t2 = null;
            MahjongTile t3 = null;
            MahjongTile t4 = null;
            if (tile.getIndex() == 1){
                t3 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() + 1 - 1], tile.getIndex() + 1);
                t4 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() + 2 - 1], tile.getIndex() + 2);
            }else if (tile.getIndex() == 2){
                t2 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() - 1 - 1], tile.getIndex() - 1);
                t3 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() + 1 - 1], tile.getIndex() + 1);
                t4 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() + 2 - 1], tile.getIndex() + 2);
            }else if (tile.getIndex() == 8){
                t1 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() - 2 - 1], tile.getIndex() - 2);
                t2 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() - 1 - 1], tile.getIndex() - 1);
                t3 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() + 1 - 1], tile.getIndex() + 1);
            }else if (tile.getIndex() == 9){
                t1 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() - 2 - 1], tile.getIndex() - 2);
                t2 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() - 1 - 1], tile.getIndex() - 1);
            }else {
                t1 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() - 2 - 1], tile.getIndex() - 2);
                t2 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() - 1 - 1], tile.getIndex() - 1);
                t3 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() + 1 - 1], tile.getIndex() + 1);
                t4 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() + 2 - 1], tile.getIndex() + 2);
            }
            MahjongTile[][] shunzi = new MahjongTile[3][3];
            if ((getTile(t1) >= 1 && getTile(t2) >= 1)){
                isChi = true;
                shunzi[0][0] = t1;
                shunzi[0][1] = t2;
                shunzi[0][2] = tile;
            }else if ((getTile(t2) >= 1 && getTile(t3) >= 1)){
                isChi = true;
                shunzi[1][0] = t2;
                shunzi[1][1] = tile;
                shunzi[1][2] = t3;
            } else if ((getTile(t3) >= 1 && getTile(t4) >= 1)) {
                isChi = true;
                shunzi[2][0] = tile;
                shunzi[2][2] = t3;
                shunzi[2][1] = t4;
            }else {
                isChi = false;
            }
            return shunzi;
        }
        isChi = false;
        return null;
    }

    public void chi(MahjongTile tile) {
        if (isChi){
            MahjongTile[][] shunzi = ifChi(tile);
            if (shunzi[0][0] != null){
                handTiles.remove(shunzi[0][0]);
                handTiles.remove(shunzi[0][1]);
                handTiles.remove(shunzi[0][2]);
                inOrderTiles.add(shunzi[0]);
                isChi = false;
            }else if (shunzi[1][0] != null){
                handTiles.remove(shunzi[1][0]);
                handTiles.remove(shunzi[1][1]);
                handTiles.remove(shunzi[1][2]);
                inOrderTiles.add(shunzi[1]);
                isChi = false;
            }else if (shunzi[2][0] != null){
                handTiles.remove(shunzi[2][0]);
                handTiles.remove(shunzi[2][1]);
                handTiles.remove(shunzi[2][2]);
                inOrderTiles.add(shunzi[2]);
                isChi = false;
            }
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
        return getTiles().isEmpty();
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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
        if (tile != null){
            int num = 0;
            for (int i = 0; i < handTiles.size(); i++){
                if (handTiles.get(i).getSuit().equals(tile.getSuit())){
                    if (handTiles.get(i).getValue().equals(tile.getValue())){
                        num ++;
                    }
                }
            }
            return num;
        }
        return 0;
    }

    public ArrayList<MahjongTile> getTiles(){
        return handTiles;
    }

    public void addTile(MahjongTile tile){
        handTiles.add(tile);
    }

    abstract MahjongTile selectTiles(MahjongTile tile);

    public MahjongTile removeTile(int  index) {
        MahjongTile tile = handTiles.get(index);
        handTiles.remove(index);
        usedTiles.add(tile);
        return tile;
    }
}