package com.example.comp2008j_group13_majong.User;

import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import java.util.ArrayList;
import java.util.Random;

public class Computer extends User {
    public String name;
    public int score;
    public ArrayList<MahjongTile> tiles;
    public boolean isTurn;
    public boolean isWin;
    public boolean isKong;
    public boolean isChi;
    public boolean isPong;

    public Computer(String name, ArrayList<MahjongTile> tiles) {
        this.name = name;
        this.score = 0;
        this.tiles = tiles;
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
        Random random = new Random();
        int randomIndex = random.nextInt(tiles.size());
        return tiles.get(randomIndex);
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
