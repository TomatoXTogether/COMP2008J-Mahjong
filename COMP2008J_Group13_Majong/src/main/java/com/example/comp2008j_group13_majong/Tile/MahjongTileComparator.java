package com.example.comp2008j_group13_majong.Tile;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MahjongTileComparator implements Comparator<MahjongTile> {
    private static final Map<String, Integer> valueMap = new HashMap<>();
    private static final Map<MahjongTile.Suit, Integer> suitMap = new HashMap<>();

    static {
        // 初始化数值映射
        String[] values = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
        for (int i = 0; i < values.length; i++) {
            valueMap.put(values[i], i + 1);
        }
        valueMap.put("东", 10);
        valueMap.put("南", 11);
        valueMap.put("西", 12);
        valueMap.put("北", 13);
        valueMap.put("中", 14);
        valueMap.put("发", 15);
        valueMap.put("", 16);  // 白板

        // 初始化花色映射
        suitMap.put(MahjongTile.Suit.万, 1);
        suitMap.put(MahjongTile.Suit.条, 2);
        suitMap.put(MahjongTile.Suit.饼, 3);
        suitMap.put(MahjongTile.Suit.风, 4);
        suitMap.put(MahjongTile.Suit.发财, 5);
        suitMap.put(MahjongTile.Suit.白板, 6);
    }

    @Override
    public int compare(MahjongTile tile1, MahjongTile tile2) {
        // 首先按花色排序
        int suitComparison = suitMap.get(tile1.getSuit()).compareTo(suitMap.get(tile2.getSuit()));
        if (suitComparison != 0) {
            return suitComparison;
        }

        // 如果是发财或白板，不再按值排序
        if (tile1.getSuit() == MahjongTile.Suit.发财 || tile1.getSuit() == MahjongTile.Suit.白板) {
            return 0;
        }

        // 然后按值排序
        return valueMap.get(tile1.getValue()).compareTo(valueMap.get(tile2.getValue()));
    }
}
