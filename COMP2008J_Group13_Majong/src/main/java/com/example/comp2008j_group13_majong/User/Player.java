package com.example.comp2008j_group13_majong.User;

import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import java.util.ArrayList;

public class Player extends User{

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
    ArrayList<MahjongTile> removeTiles(MahjongTile tile) {
        tiles.remove(tile);
        return tiles;
    }

    @Override
    MahjongTile selectTiles(MahjongTile tile) {
        return tile;
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
