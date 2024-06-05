package com.example.comp2008j_group13_majong.User;

import com.example.comp2008j_group13_majong.Tile.MahjongTile;

public interface PlayerActionObserver {
    void onPeng(User player);
    void onGang(User player);
    void onPass(User player);
}
