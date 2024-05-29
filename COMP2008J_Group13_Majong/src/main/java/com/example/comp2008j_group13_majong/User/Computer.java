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
    }

    @Override
    ArrayList<MahjongTile> removeTiles(MahjongTile tile) {
        handTiles.remove(tile);
        return handTiles;
    }

    @Override
    MahjongTile selectTiles(MahjongTile tile) {
        Random random = new Random();
        int randomIndex = random.nextInt(handTiles.size());
        return handTiles.get(randomIndex);
    }

    @Override
    public String getName() {
        return name;
    }
}
