package com.example.comp2008j_group13_majong.User;

import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import java.util.ArrayList;
import java.util.Random;

public class Computer extends User {

    public Computer(String name,ArrayList<MahjongTile> tiles,String position) {
        this.name = name;
        this.score = 0;
        this.position = position;
        this.isTurn = false;
        this.handTiles = tiles;
        this.usedTiles = new ArrayList<>();
        this.inOrderTiles = new ArrayList<>();
    }

    @Override
    public MahjongTile playTiles() {
        Random random = new Random();
        int randomIndex = random.nextInt(handTiles.size()-1);
        removeTile(randomIndex);
        return handTiles.get(randomIndex);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getIndex() {
        return index;
    }
    @Override
    public void setIndex(int index) {
        this.index = index;
    }
}
