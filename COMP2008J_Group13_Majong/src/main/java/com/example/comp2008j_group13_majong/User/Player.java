package com.example.comp2008j_group13_majong.User;

import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import java.util.ArrayList;

public class Player extends User{

    //public String position;

    public Player(String name, ArrayList<MahjongTile> tiles, String position) {
        this.name = name;
        this.score = 0;
        this.position = position;
        this.handTiles = tiles;
        this.isTurn = false;
    }

    @Override
    ArrayList<MahjongTile> removeTiles(MahjongTile tile) {
        handTiles.remove(tile);
        return handTiles;
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
    @Override
    public int getIndex() {
        return index;
    }
    @Override
    public void setIndex(int index) {
        this.index = index;
    }
}
