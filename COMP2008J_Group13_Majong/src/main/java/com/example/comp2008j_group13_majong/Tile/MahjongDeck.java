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
        // 定义花色和对应的值
        String[] numberValues = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
        // 添加万、条、饼的牌
        for (MahjongTile.Suit suit : MahjongTile.Suit.values()) {
            if (suit == MahjongTile.Suit.万 || suit == MahjongTile.Suit.条 || suit == MahjongTile.Suit.饼) {
                for (String value : numberValues) {
                    int index = 1;
                    for (int i = 0; i < 4; i++) {
                        tiles.add(new MahjongTile(suit, value, index));
                    }
                    index ++;
                }
            }
        }

        // 添加风牌
        String[] fengValues = {"东", "南", "西", "北", "中"};
        for (String value : fengValues) {
            for (int i = 0; i < 4; i++) {
                tiles.add(new MahjongTile(MahjongTile.Suit.风, value, 0));
            }
        }

        // 添加发财和白板
        for (int i = 0; i < 4; i++) {
            tiles.add(new MahjongTile(MahjongTile.Suit.发财));
            tiles.add(new MahjongTile(MahjongTile.Suit.白板));
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