package com.example.comp2008j_group13_majong.User;

import com.example.comp2008j_group13_majong.Tile.MahjongTile;
import com.example.comp2008j_group13_majong.Tile.MahjongTileComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

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
    public boolean justPenged = false;
    public boolean justGangged = false;
    public boolean isHu;
    public boolean isPeng;
    public boolean isGang;

    private List<PlayerActionObserver> observers = new ArrayList<>();


    public int getScore(){
        if (isKong){
            score += 30;
            isKong = false;
        }
        if (isChi){
            score += 10;
            isChi = false;
        }
        if (isPeng){
            score += 10;
            isPeng = false;
        }
        if (isHu) {
            score += 100;
            isHu = false;
        }
        return score;
    }

    public void addObserver(PlayerActionObserver observer) {
        observers.add(observer);
    }

    private void notifyPeng() {
        for (PlayerActionObserver observer : observers) {
            observer.onPeng(this);
        }
    }

    private void notifyGang() {
        for (PlayerActionObserver observer : observers) {
            observer.onGang(this);
        }
    }

    public void notifyPass() {
        for (PlayerActionObserver observer : observers) {
            observer.onPass(this);
        }
    }

    public void notifyHu() {
        for (PlayerActionObserver observer : observers) {
            observer.onHU(this);
        }
    }

    private void notifyChi() {
        for (PlayerActionObserver observer : observers) {
            observer.onChi(this);
        }
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
            if ((getTileNum(t1) >= 1 && getTileNum(t2) >= 1)){
                isChi = true;
                shunzi[0][0] = t1;
                shunzi[0][1] = t2;
                shunzi[0][2] = tile;
            }else if ((getTileNum(t2) >= 1 && getTileNum(t3) >= 1)){
                isChi = true;
                shunzi[1][0] = t2;
                shunzi[1][1] = tile;
                shunzi[1][2] = t3;
            } else if ((getTileNum(t3) >= 1 && getTileNum(t4) >= 1)) {
                isChi = true;
                shunzi[2][0] = tile;
                shunzi[2][1] = t3;
                shunzi[2][2] = t4;
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
                handTiles.remove(getTile(shunzi[0][0]));
                handTiles.remove(getTile(shunzi[0][1]));
                inOrderTiles.add(shunzi[0]);
                isChi = false;
            }else if (shunzi[1][0] != null){
                handTiles.remove(getTile(shunzi[1][0]));
                handTiles.remove(getTile(shunzi[1][2]));
                inOrderTiles.add(shunzi[1]);
                isChi = false;
            }else if (shunzi[2][0] != null){
                handTiles.remove(getTile(shunzi[2][1]));
                handTiles.remove(getTile(shunzi[2][2]));
                inOrderTiles.add(shunzi[2]);
                isChi = false;
            }
            notifyChi();
        }
    }

    public MahjongTile[] ifPeng(MahjongTile tile) {
        List<MahjongTile> matchingTiles = new ArrayList<>();
        for(MahjongTile handTile : handTiles){
            if(tile.getValue() != null){
                if(handTile.getValue() != null && tile.getValue().equals(handTile.getValue()) && tile.getSuit().equals(handTile.getSuit())){
                    matchingTiles.add(handTile);
                }
            }else{
                if(tile.getSuit().equals(handTile.getSuit())){
                    matchingTiles.add(handTile);
                }
            }
        }


        if (matchingTiles.size() >= 2) {
            isPeng = true;
            return new MahjongTile[]{tile, matchingTiles.get(0), matchingTiles.get(1)};
        } else {
            isPeng = false;
        }

        return null;
    }

    public void peng(MahjongTile tile) {

        List<MahjongTile> matchingTiles = handTiles.stream()
                .filter(t -> t.equals(tile))
                .collect(Collectors.toList());

        if (matchingTiles.size() >= 2) {
            if (handTiles.contains(matchingTiles.get(0)) && handTiles.contains(matchingTiles.get(1))) {
                handTiles.remove(matchingTiles.get(0));
                handTiles.remove(matchingTiles.get(1));
                inOrderTiles.add(new MahjongTile[]{tile, matchingTiles.get(0), matchingTiles.get(1)});
                isPeng = false;
            }
        } else {
            isPeng = false;
        }
        notifyPeng();
    }


    public MahjongTile[] ifGang(MahjongTile tile) {
        List<MahjongTile> matchingTiles = new ArrayList<>();
        for (MahjongTile handTile : handTiles) {
            if (tile.getValue() != null) {
                if (handTile.getValue() != null && tile.getValue().equals(handTile.getValue()) && tile.getSuit().equals(handTile.getSuit())) {
                    matchingTiles.add(handTile);
                }
            } else {
                if (tile.getSuit().equals(handTile.getSuit())) {
                    matchingTiles.add(handTile);
                }
            }
        }

        if (matchingTiles.size() >= 3) {
            isGang = true;
            return new MahjongTile[]{tile, matchingTiles.get(0), matchingTiles.get(1), matchingTiles.get(2)};
        } else {
            isGang = false;
        }

        return null;
    }

    public void gang(MahjongTile tile) {
        List<MahjongTile> matchingTiles = handTiles.stream()
                .filter(t -> t.equals(tile))
                .collect(Collectors.toList());

        if (matchingTiles.size() >= 3) {

            if (handTiles.contains(matchingTiles.get(0)) && handTiles.contains(matchingTiles.get(1)) && handTiles.contains(matchingTiles.get(2))) {
                handTiles.remove(matchingTiles.get(0));
                handTiles.remove(matchingTiles.get(1));
                handTiles.remove(matchingTiles.get(2));
                inOrderTiles.add(new MahjongTile[]{tile, matchingTiles.get(0), matchingTiles.get(1), matchingTiles.get(2)});
                isGang = false;
            }
        } else {
            isGang = false;
        }
        notifyGang();
    }

    public ArrayList<MahjongTile> ifHu(MahjongTile tile) {
        ArrayList<MahjongTile> tilesToCheck = new ArrayList<>(handTiles);
        for (MahjongTile[] set : inOrderTiles) {
            tilesToCheck.addAll(Arrays.asList(set));
        }

        tilesToCheck.add(tile);

        if (isWinningHand(tilesToCheck)) {
            isHu = true;
            return tilesToCheck;
        }
        isHu = false;
        return null;
    }

    private boolean isWinningHand(ArrayList<MahjongTile> tiles) {
        tiles.sort(new MahjongTileComparator());
        return canFormWinningHand(tiles);
    }

    private boolean canFormWinningHand(ArrayList<MahjongTile> tiles) {
        for (int i = 0; i < tiles.size() - 1; i++) {
            if ((tiles.get(i).getSuit() == tiles.get(i + 1).getSuit() && tiles.get(i).getSuit() == MahjongTile.Suit.发财)
                    || (tiles.get(i).getSuit() == tiles.get(i + 1).getSuit() && tiles.get(i).getSuit() == MahjongTile.Suit.白板)
                || tiles.get(i).getSuit().equals(tiles.get(i + 1).getSuit()) &&
                    tiles.get(i).getValue().equals(tiles.get(i + 1).getValue())) {

                ArrayList<MahjongTile> remainingTiles = new ArrayList<>(tiles);
                remainingTiles.remove(i);
                remainingTiles.remove(i);
                if (canFormSets(remainingTiles)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canFormSets(ArrayList<MahjongTile> tiles) {
        if (tiles.isEmpty()) {
            return true;
        }

        if (tiles.size() >= 3 &&
                ((tiles.get(0).getSuit().equals(tiles.get(1).getSuit()) && tiles.get(0).getSuit().equals(tiles.get(2).getSuit()) &&
                        (tiles.get(0).getSuit() == MahjongTile.Suit.发财 || tiles.get(0).getSuit() == MahjongTile.Suit.白板))
                        || (tiles.get(0).getSuit().equals(tiles.get(1).getSuit()) && tiles.get(0).getValue().equals(tiles.get(1).getValue()) &&
                        tiles.get(0).getSuit().equals(tiles.get(2).getSuit()) && tiles.get(0).getValue().equals(tiles.get(2).getValue())))) {

            ArrayList<MahjongTile> remainingTiles = new ArrayList<>(tiles);
            remainingTiles.remove(0);
            remainingTiles.remove(0);
            remainingTiles.remove(0);
            return canFormSets(remainingTiles);
        }

        if (tiles.size() >= 4 &&
                ((tiles.get(0).getSuit().equals(tiles.get(1).getSuit()) && tiles.get(0).getSuit().equals(tiles.get(2).getSuit()) && tiles.get(0).getSuit().equals(tiles.get(3).getSuit()) &&
                        (tiles.get(0).getSuit() == MahjongTile.Suit.发财 || tiles.get(0).getSuit() == MahjongTile.Suit.白板))
                        || (tiles.get(0).getSuit().equals(tiles.get(1).getSuit()) && tiles.get(0).getValue().equals(tiles.get(1).getValue()) &&
                        tiles.get(0).getSuit().equals(tiles.get(2).getSuit()) && tiles.get(0).getValue().equals(tiles.get(2).getValue()) &&
                        tiles.get(0).getSuit().equals(tiles.get(3).getSuit()) && tiles.get(0).getValue().equals(tiles.get(3).getValue())))) {

            ArrayList<MahjongTile> remainingTiles = new ArrayList<>(tiles);
            remainingTiles.remove(0);
            remainingTiles.remove(0);
            remainingTiles.remove(0);
            remainingTiles.remove(0);
            return canFormSets(remainingTiles);
        }

        if (tiles.size() >= 3 &&
                tiles.get(0).getSuit().equals(tiles.get(1).getSuit()) &&
                tiles.get(0).getSuit().equals(tiles.get(2).getSuit()) &&
                tiles.get(1).getIndex() == tiles.get(0).getIndex() + 1 &&
                tiles.get(2).getIndex() == tiles.get(0).getIndex() + 2) {

            ArrayList<MahjongTile> remainingTiles = new ArrayList<>(tiles);
            remainingTiles.remove(0);
            remainingTiles.remove(0);
            remainingTiles.remove(0);
            return canFormSets(remainingTiles);
        }

        return false;
    }

    public void hu(MahjongTile tile) {
        ArrayList<MahjongTile> winningTiles = ifHu(tile);
        if (winningTiles != null) {
            handTiles.clear();
            MahjongTile[] winningTilesArray = winningTiles.toArray(new MahjongTile[0]);
            inOrderTiles.add(winningTilesArray);
            isHu = false;
        }
    }

    public boolean ifWin(){
        return getTiles().isEmpty();
    }

    public String getName(){
        return name;
    }

    public ArrayList<MahjongTile[]> getInOrderTiles(){
        return inOrderTiles;
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

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public int getTileNum(MahjongTile tile) {
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

    public MahjongTile getTile(MahjongTile tile){
        for (MahjongTile t : handTiles){
            if (t.getSuit().equals(tile.getSuit()) && t.getValue().equals(tile.getValue())){
                return t;
            }
        }
        return null;
    }

    public ArrayList<MahjongTile> getTiles(){
        return handTiles;
    }

    public abstract MahjongTile playTiles();

    public MahjongTile removeTile(int index) {
        MahjongTile tile = handTiles.get(index);
        handTiles.remove(tile);
        usedTiles.add(tile);
        return tile;
    }
}