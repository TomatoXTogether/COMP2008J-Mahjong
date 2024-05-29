package com.example.comp2008j_group13_majong.MasterControll;

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

    public ArrayList<MahjongTile> humanPlayerHand;
    public ArrayList<MahjongTile> computer1Hand;
    public ArrayList<MahjongTile> computer2Hand;
    public ArrayList<MahjongTile> computer3Hand;

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

        // 创建电脑玩家
        computers = new ArrayList<>();
        computers.add(new Computer("Computer1", new ArrayList<MahjongTile>(), "东方"));
        computers.add(new Computer("Computer2", new ArrayList<MahjongTile>(), "西方"));
        computers.add(new Computer("Computer3", new ArrayList<MahjongTile>(), "北方"));

        // 初始化手牌列表
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

    private void selectDealer() {
        // 随机选择庄家
        Random random = new Random();
        int dealerIndex = random.nextInt(computers.size() + 1);
        if (dealerIndex == 0) {
            dealer = humanPlayer;
        } else {
            dealer = computers.get(dealerIndex - 1);
        }
        dealer.setTurn(true);

        // 输出庄家信息
        System.out.println("庄家是：" + dealer.getPosition());
    }

    private void dealTiles() {
        // 打乱牌堆中的牌的顺序
        deck.shuffle();
        remainingTiles.addAll(deck.getAllTiles());
        System.out.println("剩余牌堆里有： " + remainingTiles.size() + " 张牌");


        // 给每个玩家发放14张随机的牌
        for (int i = 0; i < 14; i++) {
            humanPlayerHand.add(remainingTiles.remove(0));
            computer1Hand.add(remainingTiles.remove(0));
            computer2Hand.add(remainingTiles.remove(0));
            computer3Hand.add(remainingTiles.remove(0));
        }
    }

    private void printPlayerHands() {
        // 打印每个玩家的手牌
        System.out.print(humanPlayer.getName() + " 的手牌: ");
        for (MahjongTile tile : humanPlayerHand) {
            System.out.print(tile.toString() + ", ");
        }
        System.out.println();

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

    public MahjongDeck getDeck() {
        return deck;
    }

    public String getDealerPosition() {
        return dealer.getPosition();
    }

    public ArrayList<MahjongTile> getRemainingTiles() {
        return remainingTiles;
    }

    public void dealNextRound() {
        // 从庄家开始发牌
        int dealerIndex = players.indexOf(dealer);
        int currentPlayerIndex = dealerIndex;
        int numPlayers = players.size();
        for (int i = 0; i < numPlayers; i++) {
            User currentPlayer = players.get(currentPlayerIndex);
            currentPlayer.getHand().add(remainingTiles.remove(0)); // 从剩余的牌中抽取一张牌发给当前玩家
            currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers; // 下一个玩家
        }
    }
}
