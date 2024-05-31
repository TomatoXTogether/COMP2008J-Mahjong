package com.example.comp2008j_group13_majong.User;

import com.example.comp2008j_group13_majong.Tile.MahjongDeck;
import com.example.comp2008j_group13_majong.Tile.MahjongTile;
import com.example.comp2008j_group13_majong.Tile.MahjongTileComparator;

import java.util.ArrayList;
import java.util.Objects;

public abstract class User {
    public String position;
    public String name;
    public int index;
    public int score;
    public ArrayList<MahjongTile[]> inOrderTiles; // tiles in order
    public ArrayList<MahjongTile> handTiles; // tiles in hand
    public ArrayList<MahjongTile> usedTiles;
    public boolean isTurn;
    public boolean isKong;
    public boolean isChi;
    public boolean isPong;

    public int getScore(){
        if (isKong){
            score += 30;
            isKong = false;
        }
        if (isChi){
            score += 10;
            isChi = false;
        }
        if (isPong){
            score += 10;
            isPong = false;
        }
        return score;
    }

    public MahjongTile[][] ifChi(MahjongTile tile){
        if (tile.ifNum){
            String[] numberValues = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
            MahjongTile t1 = null;
            MahjongTile t2 = null;
            MahjongTile t3 = null;
            MahjongTile t4 = null;
            if (tile.getIndex() == 1){
                t3 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() + 1 - 1], tile.getIndex() + 1);
                t4 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() + 2 - 1], tile.getIndex() + 2);
            }else if (tile.getIndex() == 2){
                t2 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() - 1 - 1], tile.getIndex() - 1);
                t3 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() + 1 - 1], tile.getIndex() + 1);
                t4 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() + 2 - 1], tile.getIndex() + 2);
            }else if (tile.getIndex() == 8){
                t1 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() - 2 - 1], tile.getIndex() - 2);
                t2 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() - 1 - 1], tile.getIndex() - 1);
                t3 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() + 1 - 1], tile.getIndex() + 1);
            }else if (tile.getIndex() == 9){
                t1 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() - 2 - 1], tile.getIndex() - 2);
                t2 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() - 1 - 1], tile.getIndex() - 1);
            }else {
                t1 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() - 2 - 1], tile.getIndex() - 2);
                t2 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() - 1 - 1], tile.getIndex() - 1);
                t3 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() + 1 - 1], tile.getIndex() + 1);
                t4 = new MahjongTile(tile.getSuit(), numberValues[tile.getIndex() + 2 - 1], tile.getIndex() + 2);
            }
            MahjongTile[][] shunzi = new MahjongTile[3][3];
            if ((getTile(t1) >= 1 && getTile(t2) >= 1)){
                isChi = true;
                shunzi[0][0] = t1;
                shunzi[0][1] = t2;
                shunzi[0][2] = tile;
            }else if ((getTile(t2) >= 1 && getTile(t3) >= 1)){
                isChi = true;
                shunzi[1][0] = t2;
                shunzi[1][1] = tile;
                shunzi[1][2] = t3;
            } else if ((getTile(t3) >= 1 && getTile(t4) >= 1)) {
                isChi = true;
                shunzi[2][0] = tile;
                shunzi[2][2] = t3;
                shunzi[2][1] = t4;
            }else {
                isChi = false;
            }
            return shunzi;
        }
        isChi = false;
        return null;
    }

    public void chi(MahjongTile tile) {
        if (isChi){
            MahjongTile[][] shunzi = ifChi(tile);
            if (shunzi[0][0] != null){
                handTiles.remove(shunzi[0][0]);
                handTiles.remove(shunzi[0][1]);
                handTiles.remove(shunzi[0][2]);
                inOrderTiles.add(shunzi[0]);
                isChi = false;
            }else if (shunzi[1][0] != null){
                handTiles.remove(shunzi[1][0]);
                handTiles.remove(shunzi[1][1]);
                handTiles.remove(shunzi[1][2]);
                inOrderTiles.add(shunzi[1]);
                isChi = false;
            }else if (shunzi[2][0] != null){
                handTiles.remove(shunzi[2][0]);
                handTiles.remove(shunzi[2][1]);
                handTiles.remove(shunzi[2][2]);
                inOrderTiles.add(shunzi[2]);
                isChi = false;
            }
        }
    }

    public MahjongTile[] ifPong(MahjongTile tile){
        MahjongTile[] pongzi = new MahjongTile[3];
        if (getTile(tile) == 2){
            int i = 0;
            for (MahjongTile t : handTiles){
                if (t.getValue().equals(tile.getValue()) && t.getSuit().equals(tile.getSuit())){
                    pongzi[i] = t;
                    i ++;
                }
            }
            isPong = true;
        }else {
            isPong = false;
        }
        return pongzi;
    }

    public void peng(MahjongTile[] pongzi) {
        for (MahjongTile t : pongzi){
            handTiles.remove(t);
        }
        inOrderTiles.add(pongzi);
    }

    public MahjongTile[] ifKong(MahjongTile tile){
        MahjongTile[] kongzi = new MahjongTile[4];
        if (getTile(tile) == 3){
            int i = 0;
            for (MahjongTile t : handTiles){
                if (t.getValue().equals(tile.getValue()) && t.getSuit().equals(tile.getSuit())){
                    kongzi[i] = t;
                    i ++;
                }
            }
            isKong = true;
        }else {
            isKong = false;
        }
        return null;
    }

    public void kong(MahjongTile[] kongzi) {
        for (MahjongTile t : kongzi){
            handTiles.remove(t);
        }
        inOrderTiles.add(kongzi);
        isKong = false;
    }

    public boolean isHu(ArrayList<MahjongTile> handTiles) {
        // 复制一份手牌，避免直接修改原始手牌
        ArrayList<MahjongTile> tiles = new ArrayList<>(handTiles);

        // 按照牌的值和花色进行排序
        tiles.sort(new MahjongTileComparator());

        // 尝试寻找将牌（即一对相同的牌）
        for (int i = 0; i < tiles.size() - 1; i++) {
            if (Objects.equals(tiles.get(i).getValue(), tiles.get(i + 1).getValue()) &&
                    tiles.get(i).getSuit() == tiles.get(i + 1).getSuit()) {

                // 将该对牌作为将牌
                MahjongTile tile1 = tiles.remove(i);
                MahjongTile tile2 = tiles.remove(i);

                // 检查剩下的牌是否可以组成四组顺子或刻子
                if (canFormMelds(tiles)) {
                    // 将将牌重新加入手牌
                    tiles.add(i, tile2);
                    tiles.add(i, tile1);
                    return true;
                }

                // 将将牌重新加入手牌
                tiles.add(i, tile2);
                tiles.add(i, tile1);
            }
        }
        return false;
    }

    // 判断是否可以将剩下的牌组成四组顺子或刻子
    private boolean canFormMelds(ArrayList<MahjongTile> tiles) {
        // 递归终止条件：如果手牌为空，说明成功组成四组顺子或刻子
        if (tiles.isEmpty()) {
            return true;
        }

        MahjongTile firstTile = tiles.get(0);

        // 尝试组成刻子
        if (tiles.size() >= 3 &&
                Objects.equals(tiles.get(0).getValue(), tiles.get(1).getValue()) &&
                Objects.equals(tiles.get(0).getValue(), tiles.get(2).getValue()) &&
                tiles.get(0).getSuit() == tiles.get(1).getSuit() &&
                tiles.get(0).getSuit() == tiles.get(2).getSuit()) {
            MahjongTile tile1 = tiles.remove(0);
            MahjongTile tile2 = tiles.remove(0);
            MahjongTile tile3 = tiles.remove(0);
            if (canFormMelds(tiles)) {
                tiles.add(0, tile3);
                tiles.add(0, tile2);
                tiles.add(0, tile1);
                return true;
            }
            tiles.add(0, tile3);
            tiles.add(0, tile2);
            tiles.add(0, tile1);
        }

        // 尝试组成顺子
        String[] numberValues = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
        if (firstTile.getSuit() == MahjongTile.Suit.万 ||
                firstTile.getSuit() == MahjongTile.Suit.条 ||
                firstTile.getSuit() == MahjongTile.Suit.饼) {
            MahjongTile secondTile = new MahjongTile(firstTile.getSuit(), numberValues[firstTile.getIndex() + 1 - 1], firstTile.getIndex() + 1);
            MahjongTile thirdTile = new MahjongTile(firstTile.getSuit(), numberValues[firstTile.getIndex() + 2 - 1], firstTile.getIndex() + 2);
            if (tiles.contains(secondTile) && tiles.contains(thirdTile)) {
                tiles.remove(secondTile);
                tiles.remove(thirdTile);
                tiles.remove(firstTile);
                if (canFormMelds(tiles)) {
                    tiles.add(firstTile);
                    tiles.add(secondTile);
                    tiles.add(thirdTile);
                    return true;
                }
                tiles.add(firstTile);
                tiles.add(secondTile);
                tiles.add(thirdTile);
            }
        }

        return false;
    }

    public boolean ifWin(){
        return getTiles().size() == 0;
    }

    public String getName(){
        return name;
    }

    public ArrayList<MahjongTile[]> getInOrderTiles(){
        return inOrderTiles;
    }

    public void setInOrderTiles(ArrayList<MahjongTile[]> inOrderTiles) {
        this.inOrderTiles = inOrderTiles;
    }

    public String getPosition() {
        return position;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    // 添加设置玩家位置的方法
    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isTurn() {
        return isTurn;
    }

    // 设置是否是玩家的回合的方法
    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public int getTile(MahjongTile tile) {
        if (tile != null){
            int num = 0;
            for (int i = 0; i < handTiles.size(); i++){
                if (handTiles.get(i).getSuit().equals(tile.getSuit())){
                    if (handTiles.get(i).getValue().equals(tile.getValue())){
                        num ++;
                    }
                }
            }
            return num;
        }
        return 0;
    }

    public ArrayList<MahjongTile> getTiles(){
        return handTiles;
    }

    public void addTile(MahjongTile tile){
        handTiles.add(tile);
    }

    abstract MahjongTile selectTiles(MahjongTile tile);

    public MahjongTile removeTile(int  index) {
        MahjongTile tile = handTiles.get(index);
        handTiles.remove(index);
        usedTiles.add(tile);
        return tile;
    }
}