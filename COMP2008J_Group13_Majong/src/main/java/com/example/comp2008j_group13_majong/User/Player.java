package com.example.comp2008j_group13_majong.User;

import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import java.util.ArrayList;

public class Player extends User{
    public String name;
    public int score;
    public ArrayList<MahjongTile> tiles;
    private ArrayList<MahjongTile> hand;
    public boolean isTurn;
    public boolean isWin;
    public boolean isKong;
    public boolean isChi;
    public boolean isPong;
    //public String position;

    public Player(String name, ArrayList<MahjongTile> tiles, String position) {
        this.name = name;
        this.score = 0;
        this.tiles = tiles;
        this.position = position;
        this.isTurn = false;
        this.hand = hand != null ? hand : new ArrayList<>();
    }

    @Override
    int getScore() {
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
    @Override
    ArrayList<MahjongTile> removeTiles(MahjongTile tile) {
        tiles.remove(tile);
        return tiles;
    }

    @Override
    void addTile(MahjongTile tile) {
        tiles.add(tile);
    }

    @Override
    MahjongTile selectTiles(MahjongTile tile) {
        return tile;
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArrayList<MahjongTile> getHand() {
        return hand;
    }
    @Override
    public void setHand(ArrayList<MahjongTile> hand) {
        this.hand = hand;
    }


    @Override
    public String getPosition() {
        return position;
    }

    @Override
    public void setTurn(boolean turn) {
        // 实现设置是否是当前玩家的逻辑
    }
}
