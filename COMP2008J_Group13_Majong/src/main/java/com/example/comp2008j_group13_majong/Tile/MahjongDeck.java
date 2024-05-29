package com.example.comp2008j_group13_majong.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MahjongDeck {
    private List<MahjongTile> tiles;

    public MahjongDeck() {
        tiles = new ArrayList<>();
        initializeDeck();
    }

    private void initializeDeck() {
        // 添加万、条、饼的牌
        for (MahjongTile.Suit suit : MahjongTile.Suit.values()) {
            if (suit == MahjongTile.Suit.万 || suit == MahjongTile.Suit.条 || suit == MahjongTile.Suit.饼) {
                for (int number = 1; number < 10; number++) {
                    for (int i = 0; i < 4; i++) {
                        tiles.add(new MahjongTile(suit, number));
                    }
                }
            }
        }

        // 添加风牌
        for (int number = 10; number < 15; number++) {
            for (int i = 0; i < 4; i++) {
                tiles.add(new MahjongTile(MahjongTile.Suit.风, number));
            }
        }

        // 添加发财和白板
        for (int i = 0; i < 4; i++) {
            tiles.add(new MahjongTile(MahjongTile.Suit.发财, 15));
            tiles.add(new MahjongTile(MahjongTile.Suit.白板, 16));
        }
    }

    public List<MahjongTile> getAllTiles() {
        return tiles;
    }

    // 把牌按照花色和大小自动排序
    public void sortTiles(List<MahjongTile> hand) {
        hand.sort(new MahjongTileComparator());
    }
    public void shuffle() {
        Collections.shuffle(tiles);
    }

    public MahjongTile drawTile() {
        if (!tiles.isEmpty()) {
            return tiles.remove(0);
        }
        return null; // If no tiles are left, return null
    }


}