package com.example.comp2008j_group13_majong.Tile;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MahjongTileComparator implements Comparator<MahjongTile> {
    private static final Map<MahjongTile.Suit, Integer> suitMap = new HashMap<>();

    static {
        suitMap.put(MahjongTile.Suit.万, 1);
        suitMap.put(MahjongTile.Suit.条, 2);
        suitMap.put(MahjongTile.Suit.饼, 3);
        suitMap.put(MahjongTile.Suit.风, 4);
        suitMap.put(MahjongTile.Suit.发财, 5);
        suitMap.put(MahjongTile.Suit.白板, 6);
    }

    @Override
    public int compare(MahjongTile tile1, MahjongTile tile2) {
        return Comparator.comparing((MahjongTile tile) -> suitMap.get(tile.getSuit()))
                .thenComparing(MahjongTile::getIndex)
                .compare(tile1, tile2);
    }
}
