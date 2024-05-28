package com.example.comp2008j_group13_majong.User;

import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import java.util.ArrayList;
import java.util.Random;

public class Computer extends User {

    public Computer(String name,ArrayList<MahjongTile> tiles,String position) {
        this.name = name;
        this.score = 0;
        this.tiles = tiles;
        this.position = position;
        this.isTurn = false;
        this.hand = hand != null ? hand : new ArrayList<>();
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
    public String getName() {
        return name;
    }

    @Override
    public int getTile(MahjongTile tile) {
        return 0;
    }
}
