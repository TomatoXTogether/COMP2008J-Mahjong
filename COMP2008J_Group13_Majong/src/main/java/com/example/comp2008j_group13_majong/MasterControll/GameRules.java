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
    private Player player;
    private List<User> players;
    private User dealer;
    private MahjongDeck deck;

    public GameRules() {
        deck = new MahjongDeck();
        initializePlayers();
        dealTiles();
        printPlayerHands();
    }

    private void initializePlayers() {
        // 创建玩家
        player = new Player("Player", new ArrayList<MahjongTile>(), "南方");

        // 创建电脑玩家
        List<Computer> computers = new ArrayList<>();
        computers.add(new Computer("Computer1", new ArrayList<MahjongTile>(), "东方"));
        computers.add(new Computer("Computer2", new ArrayList<MahjongTile>(), "西方"));
        computers.add(new Computer("Computer3", new ArrayList<MahjongTile>(), "北方"));

        // 将所有玩家加入到一个列表中
        players = new ArrayList<>();
        players.add(player);
        players.addAll(computers);

        // 随机选择庄家
        selectDealer();
    }

    private void selectDealer() {
        // 随机选择庄家
        Random random = new Random();
        int dealerIndex = random.nextInt(players.size());
        dealer = players.get(dealerIndex);
        dealer.setTurn(true);

        // 输出庄家信息
        System.out.println( "庄家是：" + dealer.getPosition());
    }

    private void dealTiles() {
        // 打乱牌堆中的牌的顺序
        deck.shuffle();

        // 给每个玩家发放14张随机的牌
        for (User player : players) {
            List<MahjongTile> hand = player.getHand();
            for (int i = 0; i < 14; i++) {
                MahjongTile tile = deck.drawTile();
                hand.add(tile);
            }
        }
    }


    private void printPlayerHands() {
        // 打印每个玩家的手牌
        for (User player : players) {
            System.out.println(player.getName() + " (" + player.getPosition() + ") 的手牌: " + player.getHand());
        }
    }

    public String getDealerPosition() {
        return dealer.getPosition();
    }
}
