package com.example.comp2008j_group13_majong.MasterControll;

import com.example.comp2008j_group13_majong.GameScreenController;
import com.example.comp2008j_group13_majong.User.Computer;
import com.example.comp2008j_group13_majong.User.Player;
import com.example.comp2008j_group13_majong.User.User;
import com.example.comp2008j_group13_majong.Tile.MahjongDeck;
import com.example.comp2008j_group13_majong.Tile.MahjongTile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.example.comp2008j_group13_majong.Tile.MahjongTile.Suit.发财;

public class GameRules {
    public Player humanPlayer;
    public Computer computer1;
    public Computer computer2;
    public Computer computer3;

    private List<Computer> computers;
    private User dealer;
    private List<User> players;
    private MahjongDeck deck;
    private ArrayList<MahjongTile> remainingTiles;
    private int currentPlayerIndex;

    public int dealerIndex;

    public GameRules() {
        deck = new MahjongDeck();
        remainingTiles = new ArrayList<>();
        initializePlayers();
        dealTiles();
        printPlayerHands();
    }

    private void initializePlayers() {
        ArrayList<MahjongTile> humanPlayerHand = new ArrayList<>();
        ArrayList<MahjongTile> computer1Hand = new ArrayList<>();
        ArrayList<MahjongTile> computer2Hand = new ArrayList<>();
        ArrayList<MahjongTile> computer3Hand = new ArrayList<>();
        // 创建玩家
        humanPlayer = new Player("Player", humanPlayerHand, "South");

        computers = new ArrayList<>();
        computer1 = new Computer("Computer1", computer1Hand, "North");
        computer2 = new Computer("Computer2", computer2Hand, "East");
        computer3 = new Computer("Computer3", computer3Hand, "West");
        computers.add(computer1);
        computers.add(computer2);
        computers.add(computer3);

        // 设置玩家索引
        humanPlayer.setIndex(3);
        computer1.setIndex(1);
        computer2.setIndex(0);
        computer3.setIndex(2);

        // 将所有玩家添加到players列表
        players = new ArrayList<>();
        players.add(humanPlayer);
        players.addAll(computers);

        // 随机选择庄家
        selectDealer();
    }


    public void selectDealer() {
        Random random = new Random();
        //dealerIndex = random.nextInt(4) ; //0-3
        dealerIndex = 3;

        if (dealerIndex == 3) {
            dealer = humanPlayer;
        } else {
            for (Computer computer : computers) {
                if (computer.getIndex() == dealerIndex) {
                    dealer = computer;
                    break;
                }
            }
        }

        dealer.setTurn(true);
        System.out.println("庄家是：" + dealer.getPosition());
        currentPlayerIndex = dealerIndex;
    }

    public int getCurrentPlayerIndex(){
        return currentPlayerIndex;
    }

    private void dealTiles() {
        // 打乱牌堆中的牌的顺序
        deck.shuffle();
        remainingTiles.addAll(deck.getAllTiles());
        System.out.println("剩余牌堆里有： " + remainingTiles.size() + " 张牌");


        // 给每个玩家发放14张随机的牌
        /** for (int i = 0; i < 14; i++) {
            humanPlayer.handTiles.add(remainingTiles.remove(0));
            computer1.handTiles.add(remainingTiles.remove(0));
            computer2.handTiles.add(remainingTiles.remove(0));
            computer3.handTiles.add(remainingTiles.remove(0));
        }**/

        // test
        String[] numberValues = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
        for (MahjongTile.Suit suit : MahjongTile.Suit.values()) {
            if (suit == MahjongTile.Suit.饼) {
                for (int index = 1; index < 10; index++) {
                    String value = numberValues[index - 1];
                    MahjongTile tile = new MahjongTile(suit, value, index);
                    humanPlayer.handTiles.add(tile);
                }
            }
        }
        for (int i = 0; i < 14; i++) {
            computer2.handTiles.add(remainingTiles.remove(0));
            computer1.handTiles.add(remainingTiles.remove(0));
            computer3.handTiles.add(remainingTiles.remove(0));
        }
    }

    private void printPlayerHands() {
        // 打印玩家的手牌
        System.out.print(humanPlayer.getName() + " 的手牌: ");
        for (MahjongTile tile : humanPlayer.handTiles) {
            System.out.print(tile.toString() + ", ");
        }
        System.out.println();

        // 打印庄家的手牌
        System.out.print(dealer.getName() + " 的手牌: ");
        for (MahjongTile tile : dealer.getTiles()) {
            System.out.print(tile.toString() + ", ");
        }
        System.out.println();

        // 打印电脑玩家的手牌
        for (int i = 0; i < computers.size(); i++) {
            Computer computer = computers.get(i);
            ArrayList<MahjongTile> hand = getComputerHand(i);
            System.out.print(computer.getName() + " 的手牌: ");
            for (MahjongTile tile : hand) {
                System.out.print(tile.toString() + ", ");
            }
            System.out.println();
        }

        // print inorder tiles
        for (int i = 0; i < players.size(); i++) {
            User user = players.get(i);
            ArrayList<MahjongTile[]> inOrderTiles = user.getInOrderTiles();
            System.out.print(user.getName() + " 的顺子牌: ");
            for (MahjongTile[] tiles : inOrderTiles) {
                for (MahjongTile tile : tiles) {
                    System.out.print(tile.toString() + ",");
                }
            }
            System.out.println();
        }
    }

    public ArrayList<MahjongTile> getHumanPlayerHand() {
        return humanPlayer.handTiles;
    }

    public ArrayList<MahjongTile> getComputerHand(int index) {
        switch (index) {
            case 0:
                return computer1.handTiles;
            case 1:
                return computer2.handTiles;
            case 2:
                return computer3.handTiles;
            default:
                return new ArrayList<>();
        }
    }

    public ArrayList<MahjongTile> getRemainingTiles() {
        return remainingTiles;
    }

    public int getRemainingTilesNumber(){return remainingTiles.size();}


    public void dealerNextRound(GameScreenController gameScreenController) {
        if (!remainingTiles.isEmpty()) {
            // 获取当前玩家
            User currentPlayer = players.stream()
                    .filter(player -> player.getIndex() == currentPlayerIndex)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Invalid player index"));

            // 给当前玩家发一张牌
            MahjongTile tile = remainingTiles.remove(0);
            currentPlayer.handTiles.add(tile);

            // 打印当前玩家信息和收到的牌
            System.out.println(currentPlayer.getName() + " is player " + currentPlayer.getIndex() + ", received: " + tile.getValue() + tile.getSuit());

            // 更新当前玩家的手牌列表
            if (currentPlayer instanceof Computer) {
                if (currentPlayer.isChi){
                    User last = last(currentPlayerIndex);
                    MahjongTile chiTile = last.usedTiles.get(last.usedTiles.size() - 1);
                    currentPlayer.chi(chiTile);
                }
                //handleComputerHand((Computer) currentPlayer, tile);

                // 电脑从牌堆中随机出一张牌
                int discardedTileIndex = new Random().nextInt(currentPlayer.handTiles.size());
                MahjongTile discardedTile = currentPlayer.removeTile(discardedTileIndex);
                System.out.println(currentPlayer.getName() + " discarded: " + discardedTile.getValue() + discardedTile.getSuit());

                // 在界面上显示这张牌
                gameScreenController.updateUsedTiles(discardedTile, currentPlayer.getIndex());

                next(currentPlayerIndex).ifChi(discardedTile);
            } else {
                if (currentPlayer.isChi){
                    currentPlayer.chi(last(currentPlayerIndex).usedTiles.get(last(currentPlayerIndex).usedTiles.size()-1));
                }

                //humanPlayer.handTiles.add(tile); // 如果是人类玩家，则更新 humanPlayerHand
            }

            // 更新currentPlayerIndex，使其在0到3之间循环
            currentPlayerIndex = (currentPlayerIndex + 1) % 4;
        }
        printPlayerHands();
    }

    private void handleComputerHand(Computer computer, MahjongTile tile) {
        int computerIndex = computers.indexOf(computer);
        switch (computerIndex) {
            case 0:
                computer1.handTiles.add(tile);
                break;
            case 1:
                computer2.handTiles.add(tile);
                break;
            case 2:
                computer3.handTiles.add(tile);
                break;
        }
    }

    public User next(int currentIndex){
       User next = null;
       for (User user : players){
           if (user.getIndex() == (currentIndex + 1 ) % 4){
               next = user;
           }
       }
       return next;
    }

    public User last(int currentIndex) {
            User last = null;
            for (User user : players){
                if (user.getIndex() == (currentIndex + 3) % 4){
                    last = user;
                }
            }
            return last;
        }
}
