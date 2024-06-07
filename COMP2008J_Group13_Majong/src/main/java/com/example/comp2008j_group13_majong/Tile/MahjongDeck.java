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
                for (int index = 1; index < 10; index++) {
                    String value = numberValues[index - 1];
                    for (int i = 0; i < 4; i++) {
                        MahjongTile tile = new MahjongTile(suit, value, index);
                        tiles.add(tile);
                        tile.ifNum = true;
                    }
                }
            }
        }

        // 添加风牌
        String[] fengValues = {"东", "南", "西", "北", "中"};
        for (String value : fengValues) {
            for (int i = 0; i < 4; i++) {
                MahjongTile tile = new MahjongTile(MahjongTile.Suit.风, value, 0);
                tiles.add(tile);
                tile.ifNum = false;
            }
        }

        // 添加发财和白板
        for (int i = 0; i < 4; i++) {
            MahjongTile t1 = new MahjongTile(MahjongTile.Suit.发财);
            MahjongTile t2 = new MahjongTile(MahjongTile.Suit.白板);
            tiles.add(t1);
            tiles.add(t2);
            t1.ifNum = false;
            t2.ifNum = false;
        }
    }

    public List<MahjongTile> getAllTiles() {
        return tiles;
    }

    public void sortHandTiles(List<MahjongTile> handTiles) {
        MahjongTileComparator comparator = new MahjongTileComparator();
        handTiles.sort(comparator); // 对手牌进行排序
    }

    public void shuffle() {
        Collections.shuffle(tiles);
    }
}