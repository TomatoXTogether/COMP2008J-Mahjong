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

public class GameRules {
    private Player humanPlayer;
    private List<Computer> computers;
    private User dealer;
    private List<User> players;
    private MahjongDeck deck;
    private ArrayList<MahjongTile> remainingTiles;
    private int currentPlayerIndex;

    public ArrayList<MahjongTile> humanPlayerHand;
    public ArrayList<MahjongTile> computer1Hand;
    public ArrayList<MahjongTile> computer2Hand;
    public ArrayList<MahjongTile> computer3Hand;
    public int dealerIndex;

    public GameRules() {
        deck = new MahjongDeck();
        remainingTiles = new ArrayList<>();
        initializePlayers();
        dealTiles();
        printPlayerHands();
    }

    private void initializePlayers() {
        // 创建玩家
        humanPlayer = new Player("Player", new ArrayList<MahjongTile>(), "南方");

        computers = new ArrayList<>();
        Computer computer1 = new Computer("Computer1", new ArrayList<MahjongTile>(), "北方");
        Computer computer2 = new Computer("Computer2", new ArrayList<MahjongTile>(), "东方");
        Computer computer3 = new Computer("Computer3", new ArrayList<MahjongTile>(), "西方");
        computers.add(computer1);
        computers.add(computer2);
        computers.add(computer3);

        // 设置玩家索引
        humanPlayer.setIndex(3);
        computer1.setIndex(1);
        computer2.setIndex(0);
        computer3.setIndex(2);

        humanPlayerHand = new ArrayList<>();
        computer1Hand = new ArrayList<>();
        computer2Hand = new ArrayList<>();
        computer3Hand = new ArrayList<>();

        // 将所有玩家添加到players列表
        players = new ArrayList<>();
        players.add(humanPlayer);
        players.addAll(computers);

        // 随机选择庄家
        selectDealer();
    }


    public void selectDealer() {
        Random random = new Random();
        dealerIndex = random.nextInt(4) ; //0-3

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
        for (int i = 0; i < 13; i++) {
            humanPlayerHand.add(remainingTiles.remove(0));
            computer1Hand.add(remainingTiles.remove(0));
            computer2Hand.add(remainingTiles.remove(0));
            computer3Hand.add(remainingTiles.remove(0));
        }
    }

    private void printPlayerHands() {
        // 打印玩家的手牌
        System.out.print(humanPlayer.getName() + " 的手牌: ");
        for (MahjongTile tile : humanPlayerHand) {
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
    }

    public ArrayList<MahjongTile> getHumanPlayerHand() {
        return humanPlayerHand;
    }

    public ArrayList<MahjongTile> getComputerHand(int index) {
        switch (index) {
            case 0:
                return computer1Hand;
            case 1:
                return computer2Hand;
            case 2:
                return computer3Hand;
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
            currentPlayer.getTiles().add(tile);

            // 打印当前玩家信息和收到的牌
            System.out.println(currentPlayer.getName() + " is player " + currentPlayer.getIndex() + ", received: " + tile.getValue() + tile.getSuit());

            // 更新当前玩家的手牌列表
            if (currentPlayer instanceof Computer) {
                handleComputerHand((Computer) currentPlayer, tile);

                // 电脑从牌堆中随机出一张牌
                MahjongTile discardedTile = currentPlayer.getTiles().remove(new Random().nextInt(currentPlayer.getTiles().size()));
                System.out.println(currentPlayer.getName() + " discarded: " + discardedTile.getValue() + discardedTile.getSuit());

                // 在界面上显示这张牌
                gameScreenController.updateUsedTiles(discardedTile, currentPlayer.getIndex());
            } else {
                humanPlayerHand.add(tile); // 如果是人类玩家，则更新 humanPlayerHand
            }

            // 更新currentPlayerIndex，使其在0到3之间循环
            currentPlayerIndex = (currentPlayerIndex + 1) % 4;
        }
    }
    private void handleComputerHand(Computer computer, MahjongTile tile) {
        int computerIndex = computers.indexOf(computer);
        switch (computerIndex) {
            case 0:
                computer1Hand.add(tile);
                break;
            case 1:
                computer2Hand.add(tile);
                break;
            case 2:
                computer3Hand.add(tile);
                break;
        }
    }
}
